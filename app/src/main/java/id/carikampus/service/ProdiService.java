package id.carikampus.service;

import java.util.List;

import id.carikampus.model.Prodi;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProdiService {

    @GET("prodi")
    Call<Prodi> getProdiById(@Query("id") int id);

    @GET("prodi-kampus")
    Call<List<Prodi>> getProdiByIdKampus(@Query("id") int id);
}
