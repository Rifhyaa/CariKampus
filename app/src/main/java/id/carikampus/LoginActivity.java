package id.carikampus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.helper.Preferences;
import id.carikampus.model.UserLogin;
import id.carikampus.viewmodel.UserLoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private UserLogin mUser;
    EditText mUsername, mPassword;
    TextView mSignUp;
    Button mButton;

    UserLoginViewModel mUserLoginViewModel;

    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Preferences.getLoggedInStatus(this)) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();

        mUser = new UserLogin();
        mUserLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);
        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mSignUp = (TextView) findViewById(R.id.signup);
        mButton = (Button) findViewById(R.id.login);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

        mButton.setOnClickListener(v -> {
            if (validateField()) {
                mUser.setUsername(mUsername.getText().toString().trim());
                mUser.setPassword(mPassword.getText().toString().trim());

                mUserLoginViewModel.loadUserLogin(mUser.getUsername(), mUser.getPassword()).observe(this, userLogin -> {
                    if (userLogin.getId() == -1) {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Login gagal")
                                .setContentText("Username atau password salah")
                                .show();

                        mPassword.setText("");
                    }
                    else {
                        Preferences.setIdUser(getApplicationContext(), userLogin.getId());
                        Preferences.setNameUser(getApplicationContext(), userLogin.getNama());
                        Preferences.setUsernameUser(getApplicationContext(), userLogin.getUsername());
                        Preferences.setPasswordUser(getApplicationContext(), userLogin.getPassword());
                        Preferences.setEmailUser(getApplicationContext(), userLogin.getEmail());
                        Preferences.setLoggedInStatus(getApplicationContext(), true);

                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("Login berhasil!")
                                .setContentText("Selamat datang " + userLogin.getNama())
                                .setConfirmText("Ok!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                })
                                .show();

                    }
                });

            }
        });
    }

    private boolean validateField() {
        boolean resultUsername = false;
        boolean resultPassword = false;

        resultUsername = validateUsername();
        resultPassword = validatePassword();

        if (!resultUsername && !resultPassword) {
            return false;
        }

        return resultUsername == resultPassword;
    }

    private boolean validateUsername() {
        if (mUsername.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_username, "Username tidak boleh kosong.");
            return false;
        } else {
            setErrorLayout(R.id.til_username, null);
        }

        return true;
    }

    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_password, "Password tidak boleh kosong.");
            return false;
        } else {
            setErrorLayout(R.id.til_password, null);
        }

        return true;
    }

    private void setErrorLayout(int id_layout, String message) {
        TextInputLayout mTextLayout = (TextInputLayout) findViewById(id_layout);
        mTextLayout.setError(message);
    }
}
