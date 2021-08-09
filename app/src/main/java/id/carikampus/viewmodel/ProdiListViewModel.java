package id.carikampus.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.carikampus.model.Prodi;
import id.carikampus.repository.ProdiRepository;

public class ProdiListViewModel extends ViewModel {

    private static final String TAG = "ProdiListViewModel";

    private LiveData<List<Prodi>> mProdiListMutableLiveData;
    private ProdiRepository mProdiRepository;

    public ProdiListViewModel() {
        mProdiRepository = ProdiRepository.get();
    }

    public LiveData<List<Prodi>> getListProdi(int id) {
        mProdiListMutableLiveData = mProdiRepository.getProdiByIdKampus(id);
        return mProdiListMutableLiveData;
    }
}
