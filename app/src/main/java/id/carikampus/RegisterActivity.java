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
import androidx.lifecycle.ViewModelProvider;

import id.carikampus.model.UserLogin;
import id.carikampus.viewmodel.UserLoginViewModel;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private UserLogin mPengguna;
    ImageView mBackToLogin;
    Button mButtonSave;

    UserLogin mUser;
    UserLoginViewModel mUserLoginViewModel;

    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();

        mUser = new UserLogin();
        mUserLoginViewModel = new ViewModelProvider(this).get(UserLoginViewModel.class);

        mBackToLogin = (ImageView) findViewById(R.id.back_to_login);
        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
//        btnDisplay = (Button) findViewById(R.id.btnDisplay);
//
//        btnDisplay.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // get selected radio button from radioGroup
//                int selectedId = radioGroup.getCheckedRadioButtonId();
//
//                // find the radiobutton by returned id
//                radioButton = (RadioButton) findViewById(selectedId);
//
//                Toast.makeText(MyAndroidAppActivity.this,
//                        radioButton.getText(), Toast.LENGTH_SHORT).show();
//
//            }

//        });

        mButtonSave = (Button) findViewById(R.id.register);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int selectedId = mRadioGroup.getCheckedRadioButtonId();
//
//                // find the radiobutton by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                mUser.setJenis_kelamin(radioButton.getText() == "Laki-Laki" ? 1 : 0);
                mUser.setNama(((EditText) findViewById(R.id.nama)).getText().toString());
                mUser.setEmail(((EditText) findViewById(R.id.email)).getText().toString());
                mUser.setUsername(((EditText) findViewById(R.id.username)).getText().toString());
                mUser.setPassword(((EditText) findViewById(R.id.password)).getText().toString());
                mUserLoginViewModel.saveUserLogin(mUser);
            }
        });

//
//        mPengguna = new User();

//        EditText mUsername = (EditText) findViewById(R.id.username);
//        EditText mPassword = (EditText) findViewById(R.id.password);
//        Button mButton = (Button) findViewById(R.id.login);
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPengguna.setUsername(mUsername.getText().toString());
//                mPengguna.setPassword(mPassword.getText().toString());
//                Toast.makeText(getApplicationContext(), mPengguna.getUsername() + mPengguna.getPassword(), Toast.LENGTH_LONG).show();
//
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        });
    }
}
