package kr.co.mash_up.crema.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kr.co.mash_up.crema.util.GsonLoader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public class CremaClient {

    private volatile static CremaClient instance;

    /**
     * Get cached api service.
     *
     * @param service is class type of api service
     * @return API service.
     */
    public static <T> T getService(Class<T> service) {
        if (instance == null) {
            synchronized (CremaClient.class) {
                if (instance == null) {
                    instance = new CremaClient();
                }
            }
        }

        return instance.getOrCreate(service);
    }

    private static final String DEV_URL = "http://52.78.83.164:8080/";

    private Retrofit retrofit;
    private Map<Object, Object> serviceHashMap = new HashMap<>();


    protected CremaClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CremaHttpInterceptor())
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(DEV_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonLoader.getInstance().getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    // get api service from hashmap.
    // if not exist, create from retrofit object and cache to hashmap
    <T> T getOrCreate(Class<T> service) {
        T apiService = (T) serviceHashMap.get(service);
        if (apiService != null) {
            return apiService;
        }

        apiService = retrofit.create(service);
        serviceHashMap.put(service, apiService);
        return apiService;
    }

}
