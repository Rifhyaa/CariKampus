package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.model.FotoKampus;
import id.carikampus.helper.CariKampusMethods;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.FotoKampusService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoKampusRepository {

    private static final String TAG = "FotoKampusRepository";
    private static FotoKampusRepository INSTANCE;

    private FotoKampusService mFotoKampusService;

    private FotoKampusRepository(Context context) {
        mFotoKampusService = ApiUtils.getFotoKampusService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FotoKampusRepository(context);
        }
    }

    public static FotoKampusRepository get() {
        return INSTANCE;
    }

    public MutableLiveData<List<FotoKampus>> getListFotoKampus(int id) {
        MutableLiveData<List<FotoKampus>> fotoKampusList = new MutableLiveData<>();

        Call<List<FotoKampus>> call = mFotoKampusService.getFotoKampusByIdKampus(id);
        call.enqueue(new Callback<List<FotoKampus>>() {
            @Override
            public void onResponse(Call<List<FotoKampus>> call, Response<List<FotoKampus>> response) {
                if (response.isSuccessful()) {
                    fotoKampusList.setValue(response.body());
                    CariKampusMethods.printLog(TAG, "getKampusById.onResponse() Called!");
                }
            }

            @Override
            public void onFailure(Call<List<FotoKampus>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
//        call.enqueue(new Callback<List<FotoKampus>>() {
//            @Override
//            public void onResponse(Call<List<FotoKampus>> call, Response<List<FotoKampus>> response) {
//                if (response.isSuccessful()) {
////                    fotoKampusList.addAll(response.body().);
//                    // Add address to list
//                    fotoKampusList.addAll(response.body());
////                    fotoKampusList.setValue(response.body());
//                    CariKampusMethods.printLog(TAG, "getKampusById.onResponse() Called!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FotoKampus>> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//        call.enqueue(new Callback<List<FotoKampus>>() {
//            @Override
//            public void onResponse(Call<List<FotoKampus>> call, Response<List<FotoKampus>> response) {
//                if (response.isSuccessful()) {
//                    fotoKampusList.setValue(response.body());
//                    CariKampusMethods.printLog(TAG, "getKampusById.onResponse() Called!");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FotoKampus>> call, Throwable t) {
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });

        return fotoKampusList;
    }
}
