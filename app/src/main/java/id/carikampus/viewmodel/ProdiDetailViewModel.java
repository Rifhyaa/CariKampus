package id.carikampus.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import id.carikampus.data.model.Prodi;
import id.carikampus.repository.ProdiRepository;

public class ProdiDetailViewModel extends ViewModel {

    private static final String TAG = "ProdiDetailViewModel";

    private LiveData<Prodi> mProdiLiveData;
    private ProdiRepository mProdiRepository;
    private MutableLiveData<Integer> mIdMutableLiveData;

    public ProdiDetailViewModel() {
        mProdiRepository = ProdiRepository.get();
        mIdMutableLiveData = new MutableLiveData<>();
        mProdiLiveData = Transformations.switchMap(mIdMutableLiveData, idProdi -> mProdiRepository.getProdiById(idProdi));
    }

    public void loadProdi(int idProdi) {
        Log.i(TAG, "loadProdi: called");
        mIdMutableLiveData.setValue(idProdi);
    }

    public LiveData<Prodi> getProdiLiveData() {
        Log.i(TAG, "getProdiLiveData: called");
        return mProdiLiveData;
    }

//    public void saveUser(User user) {
//        mUserRepository.updateUser(user);
//    }
}
