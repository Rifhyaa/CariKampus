package id.carikampus.service;

import java.util.List;

import id.carikampus.model.FotoKampus;
import id.carikampus.model.ProdiFavorit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProdiFavoritService {

    @GET("prodi-favorit")
    Call<List<ProdiFavorit>> getProdiFavoritByIdUser(@Query("id") int id);
}
