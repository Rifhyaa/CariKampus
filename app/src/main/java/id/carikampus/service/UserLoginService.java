package id.carikampus.service;

import java.util.List;

import id.carikampus.model.Prodi;
import id.carikampus.model.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserLoginService {

    @GET("login")
    Call<UserLogin> getUserByUsernamePassword(@Query("username") String username, @Query("password") String password);

    @POST("user/save")
    Call<UserLogin> registerUser(@Body UserLogin userLogin);

    @PUT("/user/edit")
    Call<UserLogin> updateUser(@Body UserLogin userLogin);

    @GET("user")
    Call<UserLogin> getUserById(@Query("id") Integer id);
}
