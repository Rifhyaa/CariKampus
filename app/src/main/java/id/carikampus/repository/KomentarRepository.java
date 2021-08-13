package id.carikampus.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.carikampus.helper.CariKampusMethods;
import id.carikampus.model.Komentar;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.KomentarService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KomentarRepository {

    private static final String TAG = "KomentarRepository";
    private static KomentarRepository INSTANCE;

    private KomentarService mKomentarService;

    private KomentarRepository(Context context) {
        mKomentarService = ApiUtils.getKomentarService();
    }

    public static void initialize(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new KomentarRepository(context);
        }
    }

    public static KomentarRepository get() {
        return INSTANCE;
    }

    public LiveData<List<Komentar>> getListKomentarByIdKampus(int id) {
        MutableLiveData<List<Komentar>> lists = new MutableLiveData<>();

        Call<List<Komentar>> call = mKomentarService.getKomentarByIdKampus(id);
        call.enqueue(new Callback<List<Komentar>>() {
            @Override
            public void onResponse(Call<List<Komentar>> call, Response<List<Komentar>> response) {
                lists.setValue(response.body());
                Log.i(TAG, TAG + ".getListKomentarByIdKampus() Success");
            }

            @Override
            public void onFailure(Call<List<Komentar>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return lists;
    }

    public LiveData<Komentar> saveKomentar(Komentar komentar) {
        MutableLiveData<Komentar> myKomentar = new MutableLiveData<>();

        Call<Komentar> call = mKomentarService.saveKomentar(komentar);
        call.enqueue(new Callback<Komentar>() {
            @Override
            public void onResponse(Call<Komentar> call, Response<Komentar> response) {
                myKomentar.setValue(response.body());
                Log.i(TAG, TAG + ".saveKomentar() Success");
            }

            @Override
            public void onFailure(Call<Komentar> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

        return myKomentar;
    }
}
