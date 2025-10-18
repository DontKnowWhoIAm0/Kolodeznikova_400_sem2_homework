package ru.kpfu.itis.Kolodeznikova.dao.impl;

import ru.kpfu.itis.Kolodeznikova.dao.WorkoutDao;
import ru.kpfu.itis.Kolodeznikova.entity.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.enums.*;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDaoImpl implements WorkoutDao {

    private static final String WORKOUT_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS workouts (
                id BIGSERIAL PRIMARY KEY,
                creatorId BIGINT NOT NULL REFERENCES users(id),
                participantId BIGINT NOT NULL REFERENCES users(id),
                sport VARCHAR(50) NOT NULL,
                city VARCHAR(100) NOT NULL,
                status VARCHAR(50) NOT NULL,
                completedDate DATE
            );
            """;

    private static final String ADD_WORKOUT_QUERY = """
            INSERT INTO workouts (creatorId, participantId, sport, city, status)
            VALUES (?, ?, ?, ?, ?);
            """;

    private static final String GET_WORKOUT_BY_ID_QUERY = """
            SELECT * FROM workouts WHERE id = ?;
            """;

    private static final String UPDATE_WORKOUT_INFORMATION_QUERY = """
            UPDATE workouts
            SET status = ?, completedDate = ?
            WHERE id = ?;
            """;

    private static final String GET_ALL_USER_WORKOUTS_QUERY = """
            SELECT * FROM workouts
            WHERE creatorId = ? OR participantId = ?;
            """;

    private static final String GET_ALL_USER_AS_CREATOR_PENDING_WORKOUTS_QUERY = """
            SELECT * FROM workouts
            WHERE creatorId = ? AND status = 'pending';
            """;

    private static final String GET_ALL_USER_AS_PARTICIPANT_PENDING_WORKOUTS_QUERY = """
            SELECT * FROM workouts
            WHERE participantId = ? AND status = 'pending';
            """;

    private static final String DELETE_WORKOUT_QUERY = """
            DELETE FROM workouts WHERE id = ?;
            """;

    private final ConnectionPool connectionPool;

    public WorkoutDaoImpl(ConnectionPool connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(WORKOUT_TABLE_CREATE_QUERY);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void create(Workout workout) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_WORKOUT_QUERY)) {
            statement.setInt(1, workout.getCreatorId());
            statement.setInt(2, workout.getParticipantId());
            statement.setString(3, workout.getSport().toString());
            statement.setString(4, workout.getCity());
            statement.setString(5, workout.getStatus().toString());
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Workout findById(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        Workout workout = null;
        try (PreparedStatement statement = connection.prepareStatement(GET_WORKOUT_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                workout = makeWorkout(resultSet);
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workout;
    }

    @Override
    public void update(Workout workout) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_WORKOUT_INFORMATION_QUERY)) {
            statement.setString(1, workout.getStatus().toString());
            if (workout.getStatus() == WorkoutStatus.COMPLETED) {
                statement.setDate(2, Date.valueOf(LocalDate.now()));
            } else {
                statement.setNull(2, Types.DATE);
            }
            statement.setInt(3, workout.getId());
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_WORKOUT_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Workout> getAllUserWorkouts(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Workout> workouts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USER_WORKOUTS_QUERY)) {
            statement.setInt(1, id);
            statement.setInt(2, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                workouts.add(makeWorkout(resultSet));
            }
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workouts;
    }

    @Override
    public List<Workout> getAllUserAsCreatorPendingWorkouts(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Workout> workouts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USER_AS_CREATOR_PENDING_WORKOUTS_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                workouts.add(makeWorkout(resultSet));
            }

        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workouts;
    }

    @Override
    public List<Workout> getAllUserAsParticipantPendingWorkouts(int id) throws SQLException {
        Connection connection = connectionPool.getConnection();
        List<Workout> workouts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USER_AS_PARTICIPANT_PENDING_WORKOUTS_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                workouts.add(makeWorkout(resultSet));
            }

        } finally {
            connectionPool.releaseConnection(connection);
        }
        return workouts;
    }

    private Workout makeWorkout(ResultSet resultSet) throws SQLException {
        return new Workout(
                resultSet.getInt("id"),
                resultSet.getInt("creatorId"),
                resultSet.getInt("participantId"),
                Sports.valueOf(resultSet.getString("sport")),
                resultSet.getString("city"),
                WorkoutStatus.valueOf(resultSet.getString("status")),
                (resultSet.getDate("completedDate") != null) ? resultSet.getDate("completedDate").toLocalDate() : null
        );
    }
}
