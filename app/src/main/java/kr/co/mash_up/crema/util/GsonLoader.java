package kr.co.mash_up.crema.util;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by bigstark on 2016. 8. 3..
 */

public class GsonLoader {

    private volatile static GsonLoader instance;

    public static GsonLoader getInstance() {
        if (instance == null) {
            synchronized (GsonLoader.class) {
                if (instance == null) {
                    instance = new GsonLoader();
                }
            }
        }

        return instance;
    }


    public static String toJson(Object src) {
        return getInstance().getGson().toJson(src);
    }


    public static <T> T fromJson(String json, Class<T> classType) {
        return getInstance().getGson().fromJson(json, classType);
    }

    private Gson gson;


    public GsonLoader() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
                .create();
    }

    public Gson getGson() {
        return gson;
    }


    // Using Android's base64 libraries. This can be replaced with any base64 library.
    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString(), Base64.NO_WRAP);
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
        }
    }
}
