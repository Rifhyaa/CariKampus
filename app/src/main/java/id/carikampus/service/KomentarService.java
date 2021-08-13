package id.carikampus.service;

import java.util.List;

import id.carikampus.model.Komentar;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KomentarService {

    @GET("komentar-kampus")
    Call<List<Komentar>> getKomentarByIdKampus(@Query("id") int id);

    @POST("komentar-kampus")
    Call<Komentar> saveKomentar(@Body Komentar komentar);
}
