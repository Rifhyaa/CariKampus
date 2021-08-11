package id.carikampus.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.Kampus;
import id.carikampus.model.Komentar;
import id.carikampus.repository.KampusRepository;
import id.carikampus.repository.KomentarRepository;

public class KomentarViewModel extends ViewModel {

    private static final String TAG = "KomentarViewModel";

    private MutableLiveData<List<Komentar>> mKomentarListMutableLiveData;
    private KomentarRepository mKomentarRepository;

    public KomentarViewModel() {
        mKomentarRepository = KomentarRepository.get();
    }

    public LiveData<List<Komentar>> getListKomentar(int id) {
        return mKomentarRepository.getListKomentarByIdKampus(id);
    }
}
