package ru.kpfu.itis.Kolodeznikova.dao.impl;

import ru.kpfu.itis.Kolodeznikova.dao.WorkoutRequestDao;
import ru.kpfu.itis.Kolodeznikova.entity.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.entity.enums.Sports;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutRequestDaoImpl implements WorkoutRequestDao {

    private static final String CREATE_REQUESTS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS workout_requests (
                id BIGSERIAL PRIMARY KEY,
                creatorId BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                sport VARCHAR(50) NOT NULL,
                city VARCHAR(100) NOT NULL,
                startDate DATE NOT NULL,
                endDate DATE NOT NULL,
                isTimeRelevant BOOLEAN NOT NULL,
                startTime TIME,
                endTime TIME
            );
            """;

    private static final String CREATE_RESPONDENTS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS workout_request_respondents (
                request_id BIGINT REFERENCES workout_requests(id) ON DELETE CASCADE,
                respondent_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                PRIMARY KEY (request_id, respondent_id)
            );
            """;

    private static final String ADD_REQUEST_QUERY = """
            INSERT INTO workout_requests (creatorId, sport, city, startDate, endDate, isTimeRelevant, startTime, endTime)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    private static final String ADD_RESPONDENT_QUERY = """
            INSERT INTO workout_request_respondents (request_id, respondent_id) VALUES (?, ?);
            """;

    private static final String GET_REQUEST_BY_ID_QUERY = """
            SELECT * FROM workout_requests WHERE id = ?;
            """;

    private static final String GET_RESPONDENTS_BY_REQUEST_ID_QUERY = """
            SELECT respondent_id
            FROM workout_request_respondents
            WHERE request_id = ?;
            """;

    private static final String DELETE_REQUEST_QUERY = """
            DELETE FROM workout_requests WHERE id = ?;
            """;

    private static final String DELETE_RESPONDENT_BY_REQUEST_ID_QUERY = """
            DELETE FROM workout_request_respondents WHERE request_id = ? AND respondent_id = ?;
            """;

    private final ConnectionPool connectionPool;

    public WorkoutRequestDaoImpl(ConnectionPool connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_REQUESTS_TABLE_QUERY);
            statement.execute(CREATE_RESPONDENTS_TABLE_QUERY);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void create(WorkoutRequest workoutRequest) throws SQLException {
        Connection connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(ADD_REQUEST_QUERY);) {
            statement.setInt(1, workoutRequest.getCreatorId());
            statement.setString(2, workoutRequest.getSport().toString());
            statement.setString(3, workoutRequest.getCity());
            statement.setDate(4, Date.valueOf(workoutRequest.getStartDate()));
            statement.setDate(5, Date.valueOf(workoutRequest.getEndDate()));
            statement.setBoolean(6, workoutRequest.isTimeRelevant());
            statement.setTime(7, workoutRequest.getStartTime() != null ? Time.valueOf(workoutRequest.getStartTime()) : null);
            statement.setTime(8, workoutRequest.getEndTime() != null ? Time.valueOf(workoutRequest.getEndTime()) : null);

            ResultSet resultSet = statement.executeQuery();
            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            if (workoutRequest.getRespondentsId() != null && !workoutRequest.getRespondentsId().isEmpty()) {
                try (PreparedStatement respondentsStatement = connection.prepareStatement(ADD_RESPONDENT_QUERY)) {
                    for (Integer respondentId : workoutRequest.getRespondentsId()) {
                        respondentsStatement.setInt(1, id);
                        respondentsStatement.setInt(2, respondentId);
                        respondentsStatement.executeUpdate();
                    }
                }
            }
            connection.commit();
        }catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
        } finally {
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public WorkoutRequest findById(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        WorkoutRequest workoutRequest = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_REQUEST_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                workoutRequest = makeWorkoutRequest(resultSet);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workoutRequest;
    }

    @Override
    public void addRespondentToRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ADD_RESPONDENT_QUERY)) {
            statement.setInt(1, workoutRequest.getId());
            statement.setInt(2, respondentId);
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void deleteRespondentFromRequest(WorkoutRequest workoutRequest, int respondentId) throws SQLException {
        Connection connection = connectionPool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_RESPONDENT_BY_REQUEST_ID_QUERY)) {
            statement.setInt(1, workoutRequest.getId());
            statement.setInt(2, respondentId);
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);

        }
    }

    private List<Integer> getAllRespondentsOfRequest(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Integer> respondentIds = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_RESPONDENTS_BY_REQUEST_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                respondentIds.add(resultSet.getInt("respondent_id"));
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return respondentIds;
    }

    private WorkoutRequest makeWorkoutRequest(ResultSet resultSet) throws SQLException {
        return new WorkoutRequest(
                resultSet.getInt("id"),
                resultSet.getInt("creatorId"),
                getAllRespondentsOfRequest(resultSet.getInt("id")),
                Sports.valueOf(resultSet.getString("sport")),
                resultSet.getString("city"),
                (resultSet.getDate("startDate") != null) ? resultSet.getDate("startDate").toLocalDate() : null,
                (resultSet.getDate("endDate") != null) ? resultSet.getDate("endDate").toLocalDate() : null,
                resultSet.getBoolean("isTimeRelevant"),
                (resultSet.getTime("startTime") != null) ? resultSet.getTime("startTime").toLocalTime() : null,
                (resultSet.getTime("endTime") != null) ? resultSet.getTime("endTime").toLocalTime() : null
        );
    }
}
