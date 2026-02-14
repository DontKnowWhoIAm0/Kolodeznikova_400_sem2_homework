package ru.kpfu.itis.Kolodeznikova.util;

import com.cloudinary.Cloudinary;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {

    private Cloudinary cloudinary;
    private String cloud_name;
    private String api_key;
    private String api_secret;

    public CloudinaryUtil(String cloud_name, String api_key, String api_secret) throws SQLException {
        this.cloud_name = cloud_name;
        this.api_key = api_key;
        this.api_secret = api_secret;
        initializeCloudinary();
    }

    private void initializeCloudinary() throws SQLException {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        cloudinary = new Cloudinary(config);
    }

    public Cloudinary getInstance() {
        return cloudinary;
    }

}
