package id.carikampus.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.IntroductionActivity;
import id.carikampus.R;
import id.carikampus.helper.Preferences;
import id.carikampus.model.Prodi;

public class SettingsFragment extends Fragment {

    Button mButtonLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mButtonLogout = v.findViewById(R.id.button_logout);
        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Anda yakin ingin keluar?")
                        .setConfirmText("Keluar")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                Preferences.clearPreferences(getContext());
                                startActivity(new Intent(getActivity().getBaseContext(), IntroductionActivity.class));
                                getActivity().finish();
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
//                        dialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setBackgroundColor(getResources().getColor(R.color.btn_danger));
            }
        });
        return v;
    }
}