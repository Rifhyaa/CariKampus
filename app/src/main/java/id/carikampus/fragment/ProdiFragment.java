package id.carikampus.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import id.carikampus.R;
import id.carikampus.model.Prodi;
import id.carikampus.viewmodel.ProdiDetailViewModel;

public class ProdiFragment extends Fragment {

    private static final String TAG = "ProdiFragment";

    private int mProdiId;
    private Prodi mProdi;
    private EditText mEditText;
    private ProdiDetailViewModel mProdiDetailViewModel;

    public ProdiDetailViewModel getProdiDetailViewModel() {
        if (mProdiDetailViewModel == null) {
            mProdiDetailViewModel = new ViewModelProvider(this)
                    .get(ProdiDetailViewModel.class);
        }

        return mProdiDetailViewModel;
    }

    private void updateUI() {
        Log.i(TAG, TAG + ".updateUI");
        mEditText.setText(mProdi.getNama_prodi());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProdi = new Prodi();

        mProdiId = 1; // (Integer) getArguments().getSerializable("1");
//        mUserId = (String) getArguments().getSerializable(ARG_USER_ID);
        mProdiDetailViewModel = getProdiDetailViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prodi, container, false);
        mEditText = (EditText) v.findViewById(R.id.username);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mProdi.setNama_prodi(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Log.i(TAG, TAG + ".onCreateView() Success");

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProdiDetailViewModel.getProdiLiveData().observe(
                getViewLifecycleOwner(),
                new Observer<Prodi>() {
                    @Override
                    public void onChanged(Prodi prodi) {
                        mProdi = prodi;
                        updateUI();
                    }
                }
        );
        mProdiDetailViewModel.loadProdi(mProdiId);

        Log.i(TAG, TAG + ".onViewCreated() Success");
    }
}
