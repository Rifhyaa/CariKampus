package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.helper.CariKampusMethods;
import id.carikampus.model.Kampus;
import id.carikampus.model.KampusFavorit;
import id.carikampus.model.KampusFavorit;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.KampusFavoritService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampusFavoritRepository {

    private static final String TAG = "KampusFavoritRepository";
    private static KampusFavoritRepository INSTANCE;

    private KampusFavoritService mKampusFavoritService;

    private KampusFavoritRepository(Context context) {
        mKampusFavoritService = ApiUtils.getKampusFavoritService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new KampusFavoritRepository(context);
        }
    }

    public static KampusFavoritRepository get() {
        return INSTANCE;
    }

    public  LiveData<List<KampusFavorit>> getListKampusFavoritById(int id) {
        MutableLiveData<List<KampusFavorit>> lists = new MutableLiveData<>();

        Call<List<KampusFavorit>> call = mKampusFavoritService.getKampusFavoritByIdUser(id);
        call.enqueue(new Callback<List<KampusFavorit>>() {
            @Override
            public void onResponse(Call<List<KampusFavorit>> call, Response<List<KampusFavorit>> response) {
                lists.setValue(response.body());
                Log.i(TAG, TAG + ".getListKampusFavoritById() Success");
            }

            @Override
            public void onFailure(Call<List<KampusFavorit>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return lists;
    }

    public LiveData<List<KampusFavorit>> getListKampusFavoritByIdKampus(int id) {
        MutableLiveData<List<KampusFavorit>> lists = new MutableLiveData<>();

        Call<List<KampusFavorit>> call = mKampusFavoritService.getKampusFavoritByIdKampus(id);
        call.enqueue(new Callback<List<KampusFavorit>>() {
            @Override
            public void onResponse(Call<List<KampusFavorit>> call, Response<List<KampusFavorit>> response) {
                lists.setValue(response.body());
                Log.i(TAG, TAG + ".getListKampusFavoritByIdKampus() Success");
            }

            @Override
            public void onFailure(Call<List<KampusFavorit>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return lists;
    }

    public LiveData<KampusFavorit> saveKampusFavorit(KampusFavorit myKampus) {
        MutableLiveData<KampusFavorit> kampusFavorit = new MutableLiveData<>();

        Call<KampusFavorit> call = mKampusFavoritService.saveKampusFavorit(myKampus);
        call.enqueue(new Callback<KampusFavorit>() {
            @Override
            public void onResponse(Call<KampusFavorit> call, Response<KampusFavorit> response) {
                if (response.isSuccessful()) {
                    kampusFavorit.setValue(response.body());
                    Log.i(TAG, TAG + ".saveKampusFavorit() Success");
                }
            }

            @Override
            public void onFailure(Call<KampusFavorit> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return kampusFavorit;
    }
}
