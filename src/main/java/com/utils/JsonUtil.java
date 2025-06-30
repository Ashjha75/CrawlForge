package com.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonUtil {

    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class.getName());
    private static final Gson gson;

    static {
        // Configure Gson with your project's requirements
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    /**
     * Convert Java object to JSON string
     */
    public static String toJson(Object object) {
        try {
            if (object == null) {
                return "null";
            }
            return gson.toJson(object);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error converting object to JSON: " + object, e);
            return "{\"error\":\"Serialization failed\"}";
        }
    }

    /**
     * Convert JSON string to Java object
     */
    public static <T> T fromJson(String json, Class<T> classType) {
        try {
            if (json == null || json.trim().isEmpty()) {
                return null;
            }
            return gson.fromJson(json, classType);
        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.SEVERE, "Invalid JSON syntax: " + json, e);
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON to object: " + json, e);
            return null;
        }
    }

    /**
     * Convert JSON string to Java object with Type (for generics)
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            if (json == null || json.trim().isEmpty()) {
                return null;
            }
            return gson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            LOGGER.log(Level.SEVERE, "Invalid JSON syntax: " + json, e);
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON to object: " + json, e);
            return null;
        }
    }

    /**
     * Check if string is valid JSON
     */
    public static boolean isValidJson(String json) {
        try {
            gson.fromJson(json, Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }

    /**
     * Create success response JSON
     */
    public static String createSuccessResponse(Object data) {
        try {
            return String.format(
                    "{\"success\":true,\"data\":%s,\"timestamp\":\"%s\"}",
                    toJson(data),
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating success response", e);
            return "{\"success\":false,\"error\":\"Response creation failed\"}";
        }
    }

    /**
     * Create error response JSON
     */
    public static String createErrorResponse(String message, int code) {
        try {
            return String.format(
                    "{\"success\":false,\"error\":\"%s\",\"code\":%d,\"timestamp\":\"%s\"}",
                    message,
                    code,
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating error response", e);
            return "{\"success\":false,\"error\":\"Unknown error occurred\",\"code\":500}";
        }
    }

    /**
     * Create paginated response JSON
     */
    public static String createPaginatedResponse(Object data, int page, int size, long total) {
        try {
            return String.format(
                    "{\"success\":true,\"data\":%s,\"pagination\":{\"page\":%d,\"size\":%d,\"total\":%d},\"timestamp\":\"%s\"}",
                    toJson(data),
                    page,
                    size,
                    total,
                    LocalDateTime.now()
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating paginated response", e);
            return "{\"success\":false,\"error\":\"Response creation failed\"}";
        }
    }

    /**
     * Sanitize JSON string to prevent XSS
     */
    public static String sanitizeJson(String json) {
        if (json == null) return null;

        return json.replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;")
                .replace("/", "&#x2F;");
    }

    /**
     * Get Gson instance for advanced usage
     */
    public static Gson getGson() {
        return gson;
    }
}
