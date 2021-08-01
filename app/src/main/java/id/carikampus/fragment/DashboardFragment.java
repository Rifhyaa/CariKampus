package id.carikampus.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import id.carikampus.IntroductionActivity;
import id.carikampus.LoginActivity;
import id.carikampus.R;
import id.carikampus.helper.Preferences;
import id.carikampus.model.Kampus;

public class DashboardFragment extends Fragment {

    TextView mUsernameTextView, mPasswordTextView;
    Button mButtonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i(TAG, "onCreate() called");
//        mKampusId = (int) getArguments().getSerializable(ARG_KAMPUS_ID);
//        Log.i(TAG, "args bundle is : " + mKampusId);
//        mKampus = new Kampus();
//        mKampusDetailViewModel = getKampusDetailViewModel();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mUsernameTextView = v.findViewById(R.id.textView);
        mPasswordTextView = v.findViewById(R.id.textView2);
        mButtonLogout = v.findViewById(R.id.btn_logout);

        mUsernameTextView.setText(Preferences.getUsernameUser(v.getContext()));
        mPasswordTextView.setText(Preferences.getPasswordUser(v.getContext()));
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearPreferences(getActivity().getBaseContext());
                startActivity(new Intent(getActivity().getBaseContext(), IntroductionActivity.class));
                getActivity().finish();
            }
        });
        return v;
    }
}
