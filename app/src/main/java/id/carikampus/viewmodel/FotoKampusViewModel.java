package id.carikampus.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.FotoKampus;
import id.carikampus.repository.FotoKampusRepository;

public class FotoKampusViewModel extends ViewModel {

    private static final String TAG = "FotoKampusViewModel";

    private FotoKampusRepository mFotoKampusRepository;
    private MutableLiveData<Integer> mIdMutableLiveData;

    public FotoKampusViewModel() {
        mFotoKampusRepository = FotoKampusRepository.get();
        mIdMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<FotoKampus>> getDetailKampusFavorit(int idKampus) {
        return mFotoKampusRepository.getListFotoKampus(idKampus);
    }
}
