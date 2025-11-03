package ru.kpfu.itis.Kolodeznikova.listener;

import ru.kpfu.itis.Kolodeznikova.dao.core.UserDao;
import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutRequestDao;
import ru.kpfu.itis.Kolodeznikova.dao.core.impl.UserDaoImpl;
import ru.kpfu.itis.Kolodeznikova.dao.core.impl.WorkoutRequestDaoImpl;
import ru.kpfu.itis.Kolodeznikova.dto.workouts.WorkoutRequestDto;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;
import ru.kpfu.itis.Kolodeznikova.service.impl.UserServiceImpl;
import ru.kpfu.itis.Kolodeznikova.service.impl.WorkoutRequestServiceImpl;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class InitListener implements ServletContextListener {

    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getResourceAsStream("/db.properties");

            properties.load(inputStream);
            pool = new ConnectionPool(
                    properties.getProperty("url"),
                    properties.getProperty("user"),
                    properties.getProperty("password")
            );

            UserDao userDao = new UserDaoImpl(pool);
            WorkoutRequestDao workoutRequestDao = new WorkoutRequestDaoImpl(pool);
            UserService userService = new UserServiceImpl(userDao);
            WorkoutRequestService workoutRequestService = new WorkoutRequestServiceImpl(workoutRequestDao);

            sce.getServletContext().setAttribute("userDao", userDao);
            sce.getServletContext().setAttribute("userService", userService);
            sce.getServletContext().setAttribute("workoutRequestService", workoutRequestService);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (pool != null) {
            try {
                pool.closePool();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
