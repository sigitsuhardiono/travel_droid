package service;

import java.util.HashMap;

import model.kota_asal.KotaAsal;
import model.user.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by www.scclaptop.com on 11/19/2016.
 */
public interface ApiInterface {
    @FormUrlEncoded
    @POST("travel/login")
    Call<User> postLogin(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("travel/list_kota_asal")
    Call<KotaAsal> postTravelAsal(@Field("auth") String auth);

    @FormUrlEncoded
    @POST("travel/list_kota_tujuan")
    Call<KotaAsal> postTravelTujuan(@Field("auth") String auth,@Field("id_kota_asal") String id_kota_asal);
}
