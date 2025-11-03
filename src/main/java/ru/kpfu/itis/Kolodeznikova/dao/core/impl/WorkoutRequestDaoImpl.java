package ru.kpfu.itis.Kolodeznikova.dao.core.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutRequestDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.WorkoutRequest;
import ru.kpfu.itis.Kolodeznikova.entity.enums.Sports;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class that provides CRUD operations for WorkoutRequest entities.
 */
public class WorkoutRequestDaoImpl implements WorkoutRequestDao {

    /** SQL query to create the "workout_requests" table if it does not exist. */
    private static final String CREATE_REQUESTS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS sb_db.workout_requests (
                id BIGSERIAL PRIMARY KEY,
                creator_id BIGINT NOT NULL REFERENCES sb_db.users(id) ON DELETE CASCADE,
                sport VARCHAR(50) NOT NULL,
                description VARCHAR(512) NOT NULL,
                city VARCHAR(100) NOT NULL,
                start_date DATE NOT NULL,
                end_date DATE NOT NULL,
                is_time_relevant BOOLEAN NOT NULL,
                start_time TIME,
                end_time TIME
            );
            """;

    /** SQL query to create the "workout_request_respondents" table to link requests and respondents. */
    private static final String CREATE_RESPONDENTS_TABLE_QUERY = """
            CREATE TABLE IF NOT EXISTS sb_db.workout_request_respondents (
                request_id BIGINT REFERENCES sb_db.workout_requests(id) ON DELETE CASCADE,
                respondent_id BIGINT REFERENCES sb_db.users(id) ON DELETE CASCADE,
                PRIMARY KEY (request_id, respondent_id)
            );
            """;

    /** SQL query to add a new workout request and return its generated ID. */
    private static final String ADD_REQUEST_QUERY = """
            INSERT INTO sb_db.workout_requests (creator_id, sport, description, city, start_date, end_date, is_time_relevant, start_time, end_time)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    /** SQL query to add a respondent to a workout request. */
    private static final String ADD_RESPONDENT_QUERY = """
            INSERT INTO sb_db.workout_request_respondents (request_id, respondent_id) VALUES (?, ?);
            """;

    /** SQL query to get a workout request by ID. */
    private static final String GET_REQUEST_BY_ID_QUERY = """
            SELECT * FROM sb_db.workout_requests WHERE id = ?;
            """;

    /** SQL query to get all respondent IDs for a workout request by its ID. */
    private static final String GET_RESPONDENTS_BY_REQUEST_ID_QUERY = """
            SELECT respondent_id
            FROM sb_db.workout_request_respondents
            WHERE request_id = ?;
            """;

    /** SQL query to delete a workout request by ID. */
    private static final String DELETE_REQUEST_QUERY = """
            DELETE FROM sb_db.workout_requests WHERE id = ?;
            """;

    /** SQL query to delete a respondent from the workout request by their ID. */
    private static final String DELETE_RESPONDENT_BY_REQUEST_ID_QUERY = """
            DELETE FROM sb_db.workout_request_respondents WHERE request_id = ? AND respondent_id = ?;
            """;

    private static final String GET_ALL_NOT_USER_REQUESTS_QUERY = """
            SELECT * FROM sb_db.workout_requests WHERE creator_id <> ?;
            """;



    private final ConnectionPool connectionPool;

    /**
     * Constructor initializes the DAO and ensures that the requests and respondents tables exist.
     */
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

    /**
     * Adds a new workout request into the database with its respondents if any.
     */
    @Override
    public void create(WorkoutRequest workoutRequest) throws SQLException {
        Connection connection = connectionPool.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement(ADD_REQUEST_QUERY);) {
            statement.setInt(1, workoutRequest.getCreatorId());
            statement.setString(2, workoutRequest.getSport().toString());
            statement.setString(3, workoutRequest.getDescription());
            statement.setString(4, workoutRequest.getCity());
            statement.setDate(5, Date.valueOf(workoutRequest.getStartDate()));
            statement.setDate(6, Date.valueOf(workoutRequest.getEndDate()));
            statement.setBoolean(7, workoutRequest.isTimeRelevant());
            statement.setTime(8, workoutRequest.getStartTime() != null ? Time.valueOf(workoutRequest.getStartTime()) : null);
            statement.setTime(9, workoutRequest.getEndTime() != null ? Time.valueOf(workoutRequest.getEndTime()) : null);

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
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException(e);
        } finally {
            connection.setAutoCommit(true);
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Finds a workout request by its ID, including all respondents.
     */
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

    /**
     * Adds a respondent to an existing workout request.
     */
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

    /**
     * Deletes a workout request from the database by its ID.
     */
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

    /**
     * Deletes a respondent from the workout request.
     */
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

    /**
     * Returns a list of all respondent IDs associated with the workout request.
     */
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

    @Override
    public List<WorkoutRequest> getAllNotUserRequests(int userId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<WorkoutRequest> workoutRequests = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(GET_ALL_NOT_USER_REQUESTS_QUERY)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                workoutRequests.add(makeWorkoutRequest(resultSet));
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workoutRequests;
    }

    /**
     * Additional method to make a Workout Request object from ResultSet.
     */
    private WorkoutRequest makeWorkoutRequest(ResultSet resultSet) throws SQLException {
        return new WorkoutRequest(
                resultSet.getInt("id"),
                resultSet.getInt("creator_id"),
                getAllRespondentsOfRequest(resultSet.getInt("id")),
                Sports.valueOf(resultSet.getString("sport")),
                resultSet.getString("description"),
                resultSet.getString("city"),
                (resultSet.getDate("start_date") != null) ? resultSet.getDate("start_date").toLocalDate() : null,
                (resultSet.getDate("end_date") != null) ? resultSet.getDate("end_date").toLocalDate() : null,
                resultSet.getBoolean("is_time_relevant"),
                (resultSet.getTime("start_time") != null) ? resultSet.getTime("start_time").toLocalTime() : null,
                (resultSet.getTime("end_time") != null) ? resultSet.getTime("end_time").toLocalTime() : null
        );
    }
}
