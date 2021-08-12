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

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.IntroductionActivity;
import id.carikampus.LoginActivity;
import id.carikampus.R;
import id.carikampus.helper.Preferences;
import id.carikampus.model.Prodi;
import id.carikampus.model.UserLogin;
import id.carikampus.rest.ApiUtils;
import id.carikampus.service.UserLoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private UserLoginService mUserService;
    private final static String APP_NAME = "CariKampus";
    private final static String UNAME = "username";
    private final static String NAMA = "nama";
    private final static String ID = "id";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";

    private TextView midUser;
    private TextView musernameTextView;
    private TextView mUname;
    private EditText mNama;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPassword2;
    Button mButtonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mButtonLogout = v.findViewById(R.id.button_logout);
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
//                        dialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setBackgroundColor(getResources().getColor(R.color.btn_danger));
            }
        });

        sharedPreferences = this.getActivity().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        midUser = (TextView) v.findViewById(R.id.idUser);
        mUname = (TextView) v.findViewById(R.id.uName);
        musernameTextView = (TextView) v.findViewById(R.id.usernameTextView);
        mNama = (EditText) v.findViewById(R.id.updatenama);
        mEmail = (EditText) v.findViewById(R.id.updateemail);
        mPassword = (EditText) v.findViewById(R.id.updatepassword);
        mPassword2 = (EditText) v.findViewById(R.id.updatepassword2);


        //
        Integer id = Preferences.getIdUser(getContext());
        midUser.setText("" + Preferences.getIdUser(getContext()));
        mUname.setText("" + Preferences.getUsernameUser(getContext()));
        musernameTextView.setText(Preferences.getNameUser(getContext()));
        mNama.setText(Preferences.getNameUser(getContext()));
        mEmail.setText("" + Preferences.getEmailUser(getContext()));
        mPassword.setText(Preferences.getPasswordUser(getContext()));
        mPassword2.setText(Preferences.getPasswordUser(getContext()));

        v.findViewById(R.id.btnUbah).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Clicked userbutton");
                save(id);
            }
        });

        return v;
    }

    private void save(Integer id) {
        String name = String.valueOf(mNama.getText());
        String email = String.valueOf(mEmail.getText());
        String uname = String.valueOf(mUname.getText());
        String pass = String.valueOf(mPassword.getText());
        String pass2 = String.valueOf(mPassword2.getText());

        if (name.isEmpty()) {
            mNama.setError("Harap isi Field Name!");
            mNama.requestFocus();
            return;
        } else if (email.isEmpty()) {
            mEmail.setError("Harap isi Field Email!");
            mEmail.requestFocus();
            return;
        } else if (pass.isEmpty()) {
            mEmail.setError("Harap isi Field Password!");
            mEmail.requestFocus();
            return;
        } else if (pass2.isEmpty()) {
            mEmail.setError("Harap isi Field Konfirmasi Password!");
            mEmail.requestFocus();
            return;
        }


        if (!pass.equals(pass2)) {
            mPassword2.setError("Password Tidak Cocok!");
            mPassword2.requestFocus();
            return;
        } else {
            mUserService = ApiUtils.getUserLoginService();
            Call<UserLogin> call = mUserService.updateUser(new UserLogin(id, name, email, uname, pass));
            call.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    if (response.body() != null) {
                        Toast.makeText(getActivity(), "Data saved successfully", Toast.LENGTH_LONG).show();
                        refresh(id);
                        startActivity(new Intent(getActivity().getBaseContext(), LoginActivity.class));

                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    Log.e("Update Error : ", t.getMessage());
                    Toast.makeText(getActivity(), "Data gagal tersimpan!2", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void refresh(Integer id) {
        mUserService = ApiUtils.getUserLoginService();
        Call<UserLogin> call = mUserService.getUserById(id);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.body() != null) {
                    UserLogin user = response.body();
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString(EMAIL, user.getEmail());
                    edit.putString(PASSWORD, user.getPassword());
                    edit.putString(NAMA, user.getNama());
                    edit.putInt(ID, user.getId());
                    edit.apply();
                } else {
                    Toast.makeText(getActivity(), "Data gagal tersimpan !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.e("Log In Error : ", t.getMessage());
                Toast.makeText(getActivity(), "Gagal Login !", Toast.LENGTH_LONG).show();
            }
        });
    }
}