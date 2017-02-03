package kr.co.mash_up.crema.rest;

import java.io.IOException;

import kr.co.mash_up.crema.UserManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public class CremaHttpInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .header("Accept", "application/json")
                .header("Authorization",
                        String.format("Bearer %s", UserManager.getInstance().getAccessToken()))
                .method(chain.request().method(), chain.request().body())
                .build();

        return chain.proceed(request);
    }
}
