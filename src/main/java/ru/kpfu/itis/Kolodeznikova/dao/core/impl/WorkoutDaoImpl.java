package ru.kpfu.itis.Kolodeznikova.dao.core.impl;

import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutDao;
import ru.kpfu.itis.Kolodeznikova.entity.core.Workout;
import ru.kpfu.itis.Kolodeznikova.entity.enums.*;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class that provides CRUD operations for Workout entities.
 */
public class WorkoutDaoImpl implements WorkoutDao {

    /** SQL query to create the "workouts" table if it does not exist. */
    private static final String WORKOUT_TABLE_CREATE_QUERY = """
            CREATE TABLE IF NOT EXISTS sb_db.workouts (
                id BIGSERIAL PRIMARY KEY,
                creator_id BIGINT NOT NULL REFERENCES sb_db.users(id),
                participant_id BIGINT NOT NULL REFERENCES sb_db.users(id),
                sport VARCHAR(50) NOT NULL,
                city VARCHAR(100) NOT NULL,
                status VARCHAR(50) NOT NULL,
                completed_date DATE
            );
            """;

    /** SQL query to add a new workout. */
    private static final String ADD_WORKOUT_QUERY = """
            INSERT INTO sb_db.workouts (creator_id, participant_id, sport, city, status)
            VALUES (?, ?, ?, ?, ?);
            """;

    /** SQL query to get a workout by ID. */
    private static final String GET_WORKOUT_BY_ID_QUERY = """
            SELECT * FROM sb_db.workouts WHERE id = ?;
            """;

    /** SQL query to update a workout's status and date of complete. */
    private static final String UPDATE_WORKOUT_INFORMATION_QUERY = """
            UPDATE sb_db.workouts
            SET status = ?, completed_date = ?
            WHERE id = ?;
            """;

    /** SQL query to get all workouts related to a user (as creator or participant). */
    private static final String GET_ALL_USER_WORKOUTS_QUERY = """
            SELECT * FROM sb_db.workouts
            WHERE creator_id = ? OR participant_id = ?;
            """;

    /** SQL query to get all pending workouts where the user is the creator. */
    private static final String GET_ALL_USER_AS_CREATOR_PENDING_WORKOUTS_QUERY = """
            SELECT * FROM sb_db.workouts
            WHERE creator_id = ? AND status = 'pending';
            """;

    /** SQL query to get all pending workouts where the user is the participant. */
    private static final String GET_ALL_USER_AS_PARTICIPANT_PENDING_WORKOUTS_QUERY = """
            SELECT * FROM sb_db.workouts
            WHERE participant_id = ? AND status = 'pending';
            """;

    /** SQL query to delete a workout by ID. */
    private static final String DELETE_WORKOUT_QUERY = """
            DELETE FROM sb_db.workouts WHERE id = ?;
            """;

    private final ConnectionPool connectionPool;

    /**
     * Constructor initializes the DAO and ensures that the workouts table exists.
     */
    public WorkoutDaoImpl(ConnectionPool connectionPool) throws SQLException {
        this.connectionPool = connectionPool;
        Connection connection = connectionPool.getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(WORKOUT_TABLE_CREATE_QUERY);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Adds a new workout into the database.
     */
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

    /**
     * Finds a workout by its ID.
     */
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

    /**
     * Updates a workout's status and sets the completed date if the workout is completed.
     */
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

    /**
     * Deletes a workout from the database by ID.
     */
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

    /**
     * Returns all workouts where the user is either the creator or participant.
     */
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

    /**
     * Returns all pending workouts where the user is the creator.
     */
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

    /**
     * Returns all pending workouts where the user is the participant.
     */
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

    /**
     * Additional method to make a Workout object from ResultSet.
     */
    private Workout makeWorkout(ResultSet resultSet) throws SQLException {
        return new Workout(
                resultSet.getInt("id"),
                resultSet.getInt("creator_id"),
                resultSet.getInt("participant_id"),
                Sports.valueOf(resultSet.getString("sport")),
                resultSet.getString("city"),
                WorkoutStatus.valueOf(resultSet.getString("status")),
                (resultSet.getDate("completed_date") != null) ? resultSet.getDate("completed_date").toLocalDate() : null
        );
    }
}
