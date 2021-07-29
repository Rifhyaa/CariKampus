package id.carikampus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.carikampus.model.User;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private User mPengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().hide();

        mPengguna = new User();

        EditText mUsername = (EditText) findViewById(R.id.username);
        EditText mPassword = (EditText) findViewById(R.id.password);
        Button mButton = (Button) findViewById(R.id.login);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPengguna.setUsername(mUsername.getText().toString());
                mPengguna.setPassword(mPassword.getText().toString());
                Toast.makeText(getApplicationContext(), mPengguna.getUsername() + mPengguna.getPassword(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
