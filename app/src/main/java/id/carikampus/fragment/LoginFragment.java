package id.carikampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import id.carikampus.MainActivity;
import id.carikampus.R;
import id.carikampus.model.User;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private User mUser;
    EditText mUsername;
    EditText mPassword;
    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mUsername = (EditText) v.findViewById(R.id.username);
        mPassword = (EditText) v.findViewById(R.id.password);
        mButton = (Button) v.findViewById(R.id.login);
        mUser = new User();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername()) {
                    startActivity(new Intent(v.getContext(), MainActivity.class));
                    getActivity().finish();
                }
            }
        });
        return v;
    }

    private boolean validateUsername() {
        String sUsername = mUsername.getText().toString();

        if (sUsername.isEmpty()) {
            setErrorLayout(R.id.til_username, "Username harus diisi.");
            return false;
        }
        
        return true;
    }

    private void setErrorLayout(int id_layout, String message) {
        TextInputLayout mTextLayout = (TextInputLayout) getView().findViewById(id_layout);
        mTextLayout.setError(message);
    }

}
