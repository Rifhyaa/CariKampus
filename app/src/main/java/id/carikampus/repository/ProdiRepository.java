package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.helper.CariKampusMethods;
import id.carikampus.model.Prodi;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.ProdiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdiRepository {

    private static final String TAG = "ProdiRepository";
    private static ProdiRepository INSTANCE;

    private ProdiService mProdiService;

    private ProdiRepository(Context context) {
        mProdiService = ApiUtils.getProdiService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ProdiRepository(context);
        }
    }

    public static ProdiRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<Prodi> getProdiById(int id) {
        MutableLiveData<Prodi> prodi = new MutableLiveData<>();

        Call<Prodi> call = mProdiService.getProdiById(id);
        call.enqueue(new Callback<Prodi>() {
            @Override
            public void onResponse(Call<Prodi> call, Response<Prodi> response) {
                if (response.isSuccessful()) {
                    prodi.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getProdiById.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<Prodi> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return prodi;
    }

    public LiveData<List<Prodi>> getProdiByIdKampus(int id) {
        MutableLiveData<List<Prodi>> prodis = new MutableLiveData<>();

        Call<List<Prodi>> call = mProdiService.getProdiByIdKampus(id);
        call.enqueue(new Callback<List<Prodi>>() {
            @Override
            public void onResponse(Call<List<Prodi>> call, Response<List<Prodi>> response) {
                if (response.isSuccessful()) {
                    prodis.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getProdiByIdKampus.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<List<Prodi>> call, Throwable t) {
                Log.e("Error API Call : ",  t.getMessage());
            }
        });

        return prodis;
    }
}

