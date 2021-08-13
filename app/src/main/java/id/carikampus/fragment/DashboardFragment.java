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

    private static final String TAG = "DashboardFragment";

    // Fragment Component
    private TextView mNamaUserTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mNamaUserTextView = v.findViewById(R.id.text_nama_user);
        mNamaUserTextView.setText("Halo " + Preferences.getNameUser(getContext()) + "!");
        return v;
    }
}
