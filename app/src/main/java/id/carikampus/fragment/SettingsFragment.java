package id.carikampus.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.IntroductionActivity;
import id.carikampus.LoginActivity;
import id.carikampus.R;
import id.carikampus.helper.Preferences;
import id.carikampus.model.Prodi;
import id.carikampus.model.UserLogin;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.UserLoginService;
import id.carikampus.viewmodel.ProdiDetailViewModel;
import id.carikampus.viewmodel.UserLoginViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    private TextView midUser;

    private TextView mUname;
    private EditText mNama;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPassword2;

    private TextView mUserTextView;
    private TextView mEmailTextView;

    private UserLoginViewModel mUserLoginViewModel;

    Button mButtonLogout;
    Button mButtonUpdate;

    public UserLoginViewModel getUserLoginDetailViewModel() {
        if (mUserLoginViewModel == null) {
            mUserLoginViewModel = new ViewModelProvider(this)
                    .get(UserLoginViewModel.class);
        }

        return mUserLoginViewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLoginViewModel = getUserLoginDetailViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mButtonLogout = v.findViewById(R.id.button_logout);
        mButtonUpdate = v.findViewById(R.id.btnUbah);

        midUser = (TextView) v.findViewById(R.id.idUser);
        mUname = (TextView) v.findViewById(R.id.uName);

        mNama = (EditText) v.findViewById(R.id.updatenama);
        mEmail = (EditText) v.findViewById(R.id.updateemail);
        mPassword = (EditText) v.findViewById(R.id.updatepassword);
        mPassword2 = (EditText) v.findViewById(R.id.updatepassword2);

        mUserTextView = (TextView) v.findViewById(R.id.usernameTextView);
        mEmailTextView = (TextView) v.findViewById(R.id.username_user);

        updateUI();
        setListenerComponent();

        return v;
    }

    private void setListenerComponent() {
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Anda yakin ingin keluar?")
                        .setConfirmText("Keluar")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Preferences.clearPreferences(getContext());
                                startActivity(new Intent(getActivity().getBaseContext(), IntroductionActivity.class));
                                getActivity().finish();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save(Preferences.getIdUser(getContext()))) {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Berhasil")
                            .setContentText("Data user berhasil diubah")
                            .show();
                }
            }
        });
    }

    private boolean save(Integer id) {
        String name = String.valueOf(mNama.getText());
        String email = String.valueOf(mEmail.getText());
        String uname = String.valueOf(mUname.getText());
        String pass = String.valueOf(mPassword.getText());
        String pass2 = String.valueOf(mPassword2.getText());

        if (name.isEmpty()) {
            mNama.setError("Harap isi Field Name!");
            mNama.requestFocus();
            return false;
        }
        else if (email.isEmpty()) {
            mEmail.setError("Harap isi Field Email!");
            mEmail.requestFocus();
            return false;
        }
        else if (pass.isEmpty()) {
            mEmail.setError("Harap isi Field Password!");
            mEmail.requestFocus();
            return false;
        }
        else if (pass2.isEmpty()) {
            mEmail.setError("Harap isi Field Konfirmasi Password!");
            mEmail.requestFocus();
            return false;
        }


        if (!pass.equals(pass2)) {
            mPassword2.setError("Password Tidak Cocok!");
            mPassword2.requestFocus();
            return false;
        }
        else {
            mUserLoginViewModel.updateUserLogin(new UserLogin(id, name, email, uname, pass)).observe(
                    getViewLifecycleOwner(),
                    new Observer<UserLogin>() {
                        @Override
                        public void onChanged(UserLogin userLogin) {
                            Preferences.setIdUser(getContext(), userLogin.getId());
                            Preferences.setEmailUser(getContext(), userLogin.getEmail());
                            Preferences.setNameUser(getContext(), userLogin.getNama());
                            Preferences.setPasswordUser(getContext(), userLogin.getPassword());

                            Log.d(TAG, ".updateUserLogin() Success");
                            updateUI();
                        }
                    }
            );

            return true;
        }
    }

    private void updateUI() {
//        midUser.setText("" + Preferences.getIdUser(getContext()));
//        mUname.setText("" + Preferences.getUsernameUser(getContext()));

        mNama.setText(Preferences.getNameUser(getContext()));
        mEmail.setText(Preferences.getEmailUser(getContext()));
        mPassword.setText(Preferences.getPasswordUser(getContext()));
        mPassword2.setText(Preferences.getPasswordUser(getContext()));

        mUserTextView.setText(Preferences.getNameUser(getContext()));
        mEmailTextView.setText("Username : " + Preferences.getUsernameUser(getContext()));

        Log.d(TAG, ".updateUI() Success");
    }
}