package id.carikampus.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import id.carikampus.data.model.Kampus;
import id.carikampus.repository.KampusRepository;

public class KampusDetailViewModel extends ViewModel {

    private static final String TAG = "KampusDetailViewModel";

    private LiveData<Kampus> mKampusLiveData;
    private KampusRepository mKampusRepository;
    private MutableLiveData<Integer> mIdMutableLiveData;

    public KampusDetailViewModel() {
        mKampusRepository = KampusRepository.get();
        mIdMutableLiveData = new MutableLiveData<>();
        mKampusLiveData = Transformations.switchMap(mIdMutableLiveData, idKampus -> mKampusRepository.getKampusById(idKampus));
    }

    public void loadKampus(int idKampus) {
        mIdMutableLiveData.setValue(idKampus);

        Log.i(TAG, TAG + ".loadKampus() Success");
    }

    public LiveData<Kampus> getKampusLiveData() {
        Log.i(TAG, TAG + ".getKampusLiveData() Success");

        return mKampusLiveData;
    }
}
