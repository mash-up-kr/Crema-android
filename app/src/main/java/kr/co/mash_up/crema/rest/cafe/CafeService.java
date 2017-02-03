package kr.co.mash_up.crema.rest.cafe;

import kr.co.mash_up.crema.model.BaseListModel;
import kr.co.mash_up.crema.model.cafe.CafeModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public interface CafeService {

    @GET("cafes")
    Call<BaseListModel<CafeModel>> getCafes (
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("cursor") String cursor
    );
}
