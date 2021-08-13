package id.carikampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.model.UserLogin;
import id.carikampus.viewmodel.UserLoginViewModel;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    ImageView mBackToLogin;
    Button mButtonSave;

    UserLogin mUser;
    UserLoginViewModel mUserLoginViewModel;

    RadioGroup mRadioGroup;

    EditText mNamaUser, mEmaillUser, mUsername, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();

        mUser = new UserLogin();
        mUserLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mBackToLogin = (ImageView) findViewById(R.id.back_to_login);
        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        mNamaUser = findViewById(R.id.nama);
        mEmaillUser = findViewById(R.id.email);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);

        mButtonSave = (Button) findViewById(R.id.register);
        mButtonSave.setOnClickListener((View.OnClickListener) v -> {

            if (validateField()) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                mUser.setJenis_kelamin(radioButton.getText() == "Laki-Laki" ? 1 : 0);
                mUser.setNama(((EditText) findViewById(R.id.nama)).getText().toString());
                mUser.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
                mUser.setUsername(((EditText) findViewById(R.id.username)).getText().toString());
                mUser.setPassword(((EditText) findViewById(R.id.password)).getText().toString());

                mUserLoginViewModel.saveUserLogin(mUser).observe(this, userLogin -> {
                    if (userLogin.getId() == 0) {
                        if (userLogin.getEmail() != null) {
                            setErrorLayout(R.id.til_email, userLogin.getEmail());
                        }
                        if (userLogin.getUsername() != null) {
                            setErrorLayout(R.id.til_username, userLogin.getUsername());
                        }
                    }
                    else {
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Daftar berhasil!")
                                .setContentText("Silahkan login untuk melanjutkan")
                                .setConfirmText("Ok!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
        boolean result = true;

        if (!validateNama()) {
            result = false;
        }

        if (!validateEmail()) {
            result = false;
        }

        if (!validateUsername()) {
            result = false;
        }

        if (!validatePassword()) {
            result = false;
        }

        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            ((RadioButton)mRadioGroup.getChildAt(mRadioGroup.getChildCount()-1)).setError("Your error");
            result = false;
        }
        else {
            ((RadioButton)mRadioGroup.getChildAt(mRadioGroup.getChildCount()-1)).setError(null);
        }

        return result;
    }

    private boolean validateNama() {
        if (mNamaUser.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_nama_lengkap, "Nama pengguna tidak boleh kosong.");
            return false;
        }
        else {
            setErrorLayout(R.id.til_nama_lengkap, null);
        }

        return true;
    }

    private boolean validateEmail() {
        if (mEmaillUser.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_email, "Email tidak boleh kosong.");
            return false;
        }
        else {
            setErrorLayout(R.id.til_email, null);
        }

        return true;
    }

    private boolean validateUsername() {
        if (mUsername.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_username, "Username tidak boleh kosong.");
            return false;
        }
        else {
            setErrorLayout(R.id.til_username, null);
        }

        return true;
    }

    private boolean validatePassword() {
        if (mPassword.getText().toString().trim().isEmpty()) {
            setErrorLayout(R.id.til_password, "Password tidak boleh kosong.");
            return false;
        }
        else {
            setErrorLayout(R.id.til_password, null);
        }

        return true;
    }

    private void setErrorLayout(int id_layout, String message) {
        TextInputLayout mTextLayout = (TextInputLayout) findViewById(id_layout);
        mTextLayout.setError(message);
    }
}
