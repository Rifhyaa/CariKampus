package id.carikampus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.Kampus;
import id.carikampus.repository.KampusRepository;

public class KampusListViewModel extends ViewModel {

    private static final String TAG = "KampusDetailViewModel";

    private MutableLiveData<List<Kampus>> mKampusListMutableLiveData;
    private KampusRepository mKampusRepository;

    public KampusListViewModel() {
        mKampusRepository = KampusRepository.get();
        mKampusListMutableLiveData = mKampusRepository.getListKampus();
    }

    public MutableLiveData<List<Kampus>> getListKampus() {
        return mKampusListMutableLiveData;
    }
}
