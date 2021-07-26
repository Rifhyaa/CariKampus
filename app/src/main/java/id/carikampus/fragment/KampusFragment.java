package id.carikampus.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import id.carikampus.R;
import id.carikampus.data.model.Kampus;
import id.carikampus.viewmodel.KampusDetailViewModel;

public class KampusFragment extends Fragment {

    private static final String ARG_KAMPUS_ID = "id_kampus";
    private static final String TAG = "KampusFragment";

    private Kampus mKampus;
    private KampusDetailViewModel mKampusDetailViewModel;
    private int mKampusId;

    private EditText mUsernameField;

    public KampusFragment() {
        // Required empty public constructor
    }

    public KampusDetailViewModel getKampusDetailViewModel() {
        if (mKampusDetailViewModel == null) {
            mKampusDetailViewModel = new ViewModelProvider(this)
                    .get(KampusDetailViewModel.class);
        }

        return mKampusDetailViewModel;
    }

    // TODO: Rename and change types and number of parameters
    public static KampusFragment newInstance(int kampusId) {
        KampusFragment fragment = new KampusFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_KAMPUS_ID, kampusId);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI() {
        Log.i(TAG, "updateUI: called");
        mUsernameField.setText(mKampus.getNama_kampus());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called");
        mKampusId = (int) getArguments().getSerializable(ARG_KAMPUS_ID);
        Log.i(TAG, "args bundle is : " + mKampusId);
        mKampus = new Kampus();
        mKampusDetailViewModel = getKampusDetailViewModel();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "UserFragment.onViewCreated: called");
        mKampusDetailViewModel.getKampusLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Kampus>() {
                    @Override
                    public void onChanged(Kampus kampus) {
                        mKampus = kampus;
                        updateUI();
                    }
                }
        );
        mKampusDetailViewModel.loadKampus(mKampusId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prodi, container, false);

        mUsernameField = (EditText) v.findViewById(R.id.username);
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mKampus.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });
        return v;
    }
}