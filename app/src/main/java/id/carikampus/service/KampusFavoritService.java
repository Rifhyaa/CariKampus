package id.carikampus.service;

import java.util.List;

import id.carikampus.model.FotoKampus;
import id.carikampus.model.KampusFavorit;
import id.carikampus.model.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KampusFavoritService {

    @GET("kampus-favorit")
    Call<List<KampusFavorit>> getKampusFavoritByIdUser(@Query("id") int id);

    @GET("kampus-favorit/detail")
    Call<List<KampusFavorit>> getKampusFavoritByIdKampus(@Query("id") int id);

    @POST("kampus-favorit")
    Call<KampusFavorit> saveKampusFavorit(@Body KampusFavorit kampusFavorit);
}
