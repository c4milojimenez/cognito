package co.com.example.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private final Gson gson;

    private GsonUtils() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public static GsonUtils getInstance() {
        return GsonUtilsHolder.INSTANCE;
    }

    private static class GsonUtilsHolder {
        private static final GsonUtils INSTANCE = new GsonUtils();
    }

    /**
     * @param data  JSON string
     * @param model Entity to convert
     * @param <T>   object
     * @return T object
     */
    public <T> T objectToModel(Object data, Class<T> model) {
        return gson.fromJson(gson.toJson(data), model);
    }

    /**
     * @param data  Entity to convert
     * @return String JSON string
     */
    public String objectToString(Object data) {
        return gson.toJson(data);
    }
}