package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.helper.CariKampusMethods;
import id.carikampus.model.UserLogin;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.UserLoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginRepository {

    private static final String TAG = "UserLoginRepository";
    private static UserLoginRepository INSTANCE;

    private UserLoginService mUserLoginService;

    private UserLoginRepository(Context context) {
        mUserLoginService = ApiUtils.getUserLoginService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserLoginRepository(context);
        }
    }

    public static UserLoginRepository get() {
        return INSTANCE;
    }

    public LiveData<UserLogin> getUserLoginByUsernamePassword(String username, String password) {
        MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();

        Call<UserLogin> call = mUserLoginService.getUserByUsernamePassword(username, password);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccessful()) {
                    userLogin.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getUserLoginById.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return userLogin;
    }

    public LiveData<UserLogin> saveUser(UserLogin myUser) {
        MutableLiveData<UserLogin> userLogin = new MutableLiveData<>();

        Call<UserLogin> call = mUserLoginService.registerUser(myUser);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccessful()) {
                    userLogin.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getUserLoginById.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return userLogin;
    }
}
