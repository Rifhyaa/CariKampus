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
            if (validateUsername()) {
                mUser.setUsername(mUsername.getText().toString());
                mUser.setPassword(mPassword.getText().toString());
//                Toast.makeText(getApplicationContext(), mUser.getUsername() + mUser.getPassword(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), mUser.getUsername() + mUser.getPassword(), Toast.LENGTH_LONG).show();
                mUserLoginViewModel.loadUserLogin(mUser.getUsername(), mUser.getPassword()).observe(this, userLogin -> {
                    if (userLogin.getId() == -1) {
//                        SweetAlertDialog pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE);
//                        pDialog.setTitleText("Username atau password salah");
//                        pDialog.setCancelable(true);
//                        pDialog.show();
                        Toast.makeText(getApplicationContext(), "salah", Toast.LENGTH_LONG).show();
                    } else {
//                        SweetAlertDialog pDialog = new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.SUCCESS_TYPE);
//                        pDialog.setTitleText("Uyey");
//                        pDialog.setCancelable(true);
//                        pDialog.show();
//                        Toast.makeText(getApplicationContext(), mUser.getUsername() + mUser.getPassword(), Toast.LENGTH_LONG).show();
                        Preferences.setUsernameUser(getApplicationContext(), userLogin.getUsername());
                        Preferences.setPasswordUser(getApplicationContext(), userLogin.getPassword());
                        Preferences.setLoggedInStatus(getApplicationContext(), true);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });

            }
        });
    }

    private boolean validateUsername() {
        if (mUsername.getText().toString().isEmpty()) {
            setErrorLayout(R.id.til_username, "Username harus diisi.");
            return false;
        } else {
            setErrorLayout(R.id.til_username, null);
        }

        return true;
    }

    private void setErrorLayout(int id_layout, String message) {
        TextInputLayout mTextLayout = (TextInputLayout) findViewById(id_layout);
        mTextLayout.setError(message);
    }
}
