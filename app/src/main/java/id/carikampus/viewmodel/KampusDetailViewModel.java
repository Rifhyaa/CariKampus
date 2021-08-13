package id.carikampus.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.Kampus;
import id.carikampus.model.KampusFavorit;
import id.carikampus.repository.KampusFavoritRepository;
import id.carikampus.repository.KampusRepository;

public class KampusDetailViewModel extends ViewModel {

    private static final String TAG = "KampusDetailViewModel";

    private LiveData<Kampus> mKampusLiveData;
    private LiveData<List<KampusFavorit>> mListKampusFavoritLiveData;

    private KampusRepository mKampusRepository;
    private KampusFavoritRepository mKampusFavoritRepository;

    private MutableLiveData<Integer> mIdMutableLiveData;

    public KampusDetailViewModel() {
        mKampusRepository = KampusRepository.get();
        mKampusFavoritRepository = KampusFavoritRepository.get();
        mIdMutableLiveData = new MutableLiveData<>();
    }

    public void loadKampus(int idKampus) {
        mIdMutableLiveData.setValue(idKampus);
        Log.i(TAG, TAG + ".loadKampus() Success");
    }

    public LiveData<Kampus> getKampusLiveData(int idKampus) {
        mKampusLiveData = mKampusRepository.getKampusById(idKampus);
        return mKampusLiveData;
    }

    public LiveData<List<KampusFavorit>> getDetailKampusFavorit(int idKampus) {
        mListKampusFavoritLiveData = mKampusFavoritRepository.getListKampusFavoritByIdKampus(idKampus);
        return mListKampusFavoritLiveData;
    }

    public LiveData<KampusFavorit> saveKampusFavorit(KampusFavorit mKampusFavorit) {
        return mKampusFavoritRepository.saveKampusFavorit(mKampusFavorit);
    }
}
