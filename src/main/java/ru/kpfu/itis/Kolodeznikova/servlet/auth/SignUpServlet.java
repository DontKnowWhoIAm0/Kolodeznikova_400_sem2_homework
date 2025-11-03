package ru.kpfu.itis.Kolodeznikova.servlet.auth;

import com.cloudinary.utils.ObjectUtils;
import ru.kpfu.itis.Kolodeznikova.entity.core.User;
import ru.kpfu.itis.Kolodeznikova.entity.enums.Gender;
import ru.kpfu.itis.Kolodeznikova.entity.enums.WayOfCommunication;
import ru.kpfu.itis.Kolodeznikova.service.UserService;
import ru.kpfu.itis.Kolodeznikova.util.CloudinaryUtil;
import ru.kpfu.itis.Kolodeznikova.util.PasswordUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Map;

/**
 * Servlet that handles user registration functionality.
 * Supports uploading a profile image and adds user into the database using UserService.
 */
@MultipartConfig
@WebServlet(name="Sign Up", urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {

    private UserService userService;
    private CloudinaryUtil cloudUtil;

    /** Number of directories for storing profile images to avoid file system overload. */
    private static final int DIRECTORIES_COUNT = 100;

    /**
     * Initializes the servlet and gets the UserService instance from the servlet context.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = (UserService) config.getServletContext().getAttribute("userService");
        this.cloudUtil = (CloudinaryUtil) config.getServletContext().getAttribute("cloudUtil");
    }

    /**
     * Handles GET requests by forwarding the user to the signup page.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("contextPath", req.getContextPath());
        req.setAttribute("title", "Регистрация");
        req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
    }

    /**
     * Handles POST requests for user registration.
     * Validates input fields, checks login uniqueness, encrypts password, uploads profile image and registers the user.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Retrieve user input from the signup form
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String nickname = req.getParameter("nickname");
        String genderStr = req.getParameter("gender");
        String wayOfCommunicationStr = req.getParameter("wayOfCommunication");
        String contactValue = req.getParameter("contactValue");

        // Validate required fields
        if (login == null || login.isBlank() ||
                password == null || password.isBlank() ||
                name == null || name.isBlank() ||
                lastname == null || lastname.isBlank() ||
                nickname == null || nickname.isBlank() ||
                genderStr == null || genderStr.isBlank() ||
                wayOfCommunicationStr == null || wayOfCommunicationStr.isBlank() ||
                contactValue == null || contactValue.isBlank()) {
            req.setAttribute("error", "Все обязательные поля должны быть заполнены");
            req.setAttribute("contextPath", req.getContextPath());
            req.setAttribute("title", "Регистрация");
            req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
            return;
        }

        // Check if login already exists
        try {
            if (userService.loginExists(login)) {
                req.setAttribute("error", "Пользователь с таким логином уже существует");
                req.setAttribute("contextPath", req.getContextPath());
                req.setAttribute("title", "Регистрация");
                req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
                return;
            } else if (userService.nicknameExists(nickname)) {
                req.setAttribute("error", "Пользователь с таким никнеймом уже существует");
                req.setAttribute("contextPath", req.getContextPath());
                req.setAttribute("title", "Регистрация");
                req.getRequestDispatcher("/WEB-INF/templates/auth/auth_page.ftl").forward(req, resp);
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Convert string values to enums and hash the password
        Gender gender = Gender.valueOf(genderStr);
        WayOfCommunication wayOfCommunication = WayOfCommunication.valueOf(wayOfCommunicationStr.toUpperCase());
        String passwordHash = PasswordUtil.encrypt(password);
        String profileImageUrl = null;

        Part part = req.getPart("profile_image");
        try (InputStream is = part.getInputStream()) {
            byte[] imageBytes = new byte[is.available()];
            int bytesRead = is.read(imageBytes);
            Map uploadResult = cloudUtil.getInstance().uploader().upload(imageBytes, ObjectUtils.emptyMap());
            profileImageUrl = (String) uploadResult.get("secure_url");
        }


        // Create new User object and add into the database
        User user = new User(login, passwordHash, name, lastname, nickname, gender,
                wayOfCommunication, contactValue, profileImageUrl);
        try {
            userService.registerUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Redirect to login page after successful registration
        resp.sendRedirect(req.getContextPath() + "/login");

    }

    /**
     * Uploads the user's profile image to the server filesystem.
     * Files are distributed across multiple directories using a hash of the filename to avoid overloading a directory.
     * Returns the path to the uploaded image or null if no file was uploaded.
     */
    private String uploadProfileImage(Part part) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null || part.getSubmittedFileName().isEmpty()) {
            return null;
        }

        // Retrieve filename and prepare path
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads" + File.separator + "profile_images";
        File file = new File(uploadPath + File.separator +
                filename.hashCode() % DIRECTORIES_COUNT + File.separator +
                filename);

        // Create directories and file if necessary
        file.getParentFile().mkdirs();
        file.createNewFile();

        // Write uploaded file content
        try (InputStream is = part.getInputStream();
        FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = is.readAllBytes();
            fos.write(buffer);
        }

        return "uploads" + File.separator + "profile_images" + File.separator + filename.hashCode() % DIRECTORIES_COUNT + File.separator + filename;
    }
}
