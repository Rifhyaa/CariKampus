package id.carikampus.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import id.carikampus.model.UserLogin;
import id.carikampus.repository.UserLoginRepository;

public class UserLoginViewModel extends ViewModel {

    private static final String TAG = "UserLoginViewModel";

    private LiveData<UserLogin> mUserLoginLiveData;
    private UserLoginRepository mUserLoginRepository;
    private MutableLiveData<Integer> mIdMutableLiveData;

    public UserLoginViewModel() {
        mUserLoginRepository = UserLoginRepository.get();
        mIdMutableLiveData = new MutableLiveData<>();
//        mUserLoginLiveData = Transformations.switchMap(mIdMutableLiveData, idUserLogin -> mUserLoginRepository.getUserLoginById(idUserLogin));
    }

    public LiveData<UserLogin> loadUserLogin(String username, String password) {
//        mIdMutableLiveData.setValue(idUserLogin);
        Log.i(TAG, TAG + ".loadUserLogin() Success");

        return mUserLoginRepository.getUserLoginByUsernamePassword(username, password);
    }

    public LiveData<UserLogin> saveUserLogin(UserLogin myUser) {
        return mUserLoginRepository.saveUser(myUser);
    }

    public LiveData<UserLogin> getUserLoginLiveData() {
        Log.i(TAG, TAG + ".getUserLoginLiveData() Success");

        return mUserLoginLiveData;
    }
}
