package id.carikampus.service;

import java.util.List;

import id.carikampus.model.FotoKampus;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FotoKampusService {

    @GET("foto-kampus")
    Call <List<FotoKampus>> getFotoKampusByIdKampus(@Query("id") int id);
}
