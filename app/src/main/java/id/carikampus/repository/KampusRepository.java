package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.data.model.Kampus;
import id.carikampus.helper.CariKampusMethods;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.KampusService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampusRepository {

    private static final String TAG = "KampusRepository";
    private static KampusRepository INSTANCE;

    private KampusService mKampusService;

    private KampusRepository(Context context) {
        mKampusService = ApiUtils.getKampusService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new KampusRepository(context);
        }
    }

    public static KampusRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<List<Kampus>> getListKampus() {
        MutableLiveData<List<Kampus>> kampusList = new MutableLiveData<>();

        Call<List<Kampus>> call = mKampusService.getListKampus();
        call.enqueue(new Callback<List<Kampus>>() {
            @Override
            public void onResponse(Call<List<Kampus>> call, Response<List<Kampus>> response) {
                if (response.isSuccessful()) {
                    kampusList.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getKampusById.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<List<Kampus>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return kampusList;
    }
}

