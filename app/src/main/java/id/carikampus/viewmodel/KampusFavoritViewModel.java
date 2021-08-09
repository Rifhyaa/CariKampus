package id.carikampus.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.KampusFavorit;
import id.carikampus.model.UserLogin;
import id.carikampus.repository.KampusFavoritRepository;

public class KampusFavoritViewModel extends ViewModel {

    private static final String TAG = "KampusDetailViewModel";

    private LiveData<List<KampusFavorit>> mKampusFavoritListMutableLiveData;
    private KampusFavoritRepository mKampusFavoritRepository;

    public KampusFavoritViewModel() {
        mKampusFavoritRepository = KampusFavoritRepository.get();
    }

    public LiveData<List<KampusFavorit>> getListKampusFavorit(int idUser) {
        Log.d(TAG, TAG + ".getListKampusFavorit(" + idUser + ")");
        mKampusFavoritListMutableLiveData = mKampusFavoritRepository.getListKampusFavoritById(idUser);
        return mKampusFavoritListMutableLiveData;
    }

    public LiveData<List<KampusFavorit>> getDetailKampusFavorit(int idKampus) {
        mKampusFavoritListMutableLiveData = mKampusFavoritRepository.getListKampusFavoritByIdKampus(idKampus);
        return mKampusFavoritListMutableLiveData;
    }

    public LiveData<KampusFavorit> saveKampusFavorit(KampusFavorit kampusFavorit) {
        return mKampusFavoritRepository.saveKampusFavorit(kampusFavorit);
    }
}
