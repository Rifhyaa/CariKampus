package id.carikampus.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import id.carikampus.R;
import id.carikampus.helper.Preferences;
import id.carikampus.model.Kampus;

public class DashboardFragment extends Fragment {

    TextView mUsernameTextView, mPasswordTextView;

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

        mUsernameTextView.setText(Preferences.getUsernameUser(v.getContext()));
        mPasswordTextView.setText(Preferences.getPasswordUser(v.getContext()));
        return v;
    }
}
