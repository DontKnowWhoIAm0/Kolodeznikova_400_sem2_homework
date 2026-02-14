package ru.kpfu.itis.Kolodeznikova.listener;

import ru.kpfu.itis.Kolodeznikova.dao.core.UserDao;
import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutDao;
import ru.kpfu.itis.Kolodeznikova.dao.core.WorkoutRequestDao;
import ru.kpfu.itis.Kolodeznikova.dao.core.impl.UserDaoImpl;
import ru.kpfu.itis.Kolodeznikova.dao.core.impl.WorkoutDaoImpl;
import ru.kpfu.itis.Kolodeznikova.dao.core.impl.WorkoutRequestDaoImpl;
import ru.kpfu.itis.Kolodeznikova.dao.misc.NotificationDao;
import ru.kpfu.itis.Kolodeznikova.dao.misc.impl.NotificationDaoImpl;
import ru.kpfu.itis.Kolodeznikova.service.NotificationService;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutRequestService;
import ru.kpfu.itis.Kolodeznikova.service.WorkoutService;
import ru.kpfu.itis.Kolodeznikova.service.impl.NotificationServiceImpl;
import ru.kpfu.itis.Kolodeznikova.service.impl.UserServiceImpl;
import ru.kpfu.itis.Kolodeznikova.service.impl.WorkoutRequestServiceImpl;
import ru.kpfu.itis.Kolodeznikova.service.impl.WorkoutServiceImpl;
import ru.kpfu.itis.Kolodeznikova.util.CloudinaryUtil;
import ru.kpfu.itis.Kolodeznikova.util.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class InitListener implements ServletContextListener {

    private ConnectionPool pool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            if (dbUrl == null) dbUrl = "jdbc:postgresql://localhost:5432/sportbuddy";
            if (dbUser == null) dbUser = "postgres";
            if (dbPassword == null) dbPassword = "$Okol622";

            pool = new ConnectionPool(dbUrl, dbUser, dbPassword);

            String cloudName = System.getenv("CLOUD_NAME");
            String cloudApiKey = System.getenv("CLOUD_API_KEY");
            String cloudApiSecret = System.getenv("CLOUD_API_SECRET");

            CloudinaryUtil cloudUtil;
            if (cloudName != null && cloudApiKey != null && cloudApiSecret != null) {
                cloudUtil = new CloudinaryUtil(cloudName, cloudApiKey, cloudApiSecret);
            } else {
                cloudUtil = new CloudinaryUtil("", "", "");
            }

            UserDao userDao = new UserDaoImpl(pool);
            WorkoutRequestDao workoutRequestDao = new WorkoutRequestDaoImpl(pool);
            WorkoutDao workoutDao = new WorkoutDaoImpl(pool);
            NotificationDao notificationDao = new NotificationDaoImpl(pool);

            UserService userService = new UserServiceImpl(userDao);
            NotificationService notificationService = new NotificationServiceImpl(notificationDao, userService);
            WorkoutService workoutService = new WorkoutServiceImpl(workoutDao, userService, notificationService);
            WorkoutRequestService workoutRequestService = new WorkoutRequestServiceImpl(workoutRequestDao, userService, notificationService);

            sce.getServletContext().setAttribute("userDao", userDao);
            sce.getServletContext().setAttribute("userService", userService);
            sce.getServletContext().setAttribute("workoutRequestService", workoutRequestService);
            sce.getServletContext().setAttribute("workoutService", workoutService);
            sce.getServletContext().setAttribute("notificationService", notificationService);
            sce.getServletContext().setAttribute("cloudUtil", cloudUtil);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database connection", e);
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