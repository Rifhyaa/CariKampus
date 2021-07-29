package id.carikampus.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import id.carikampus.R;
import id.carikampus.model.FotoKampus;
import id.carikampus.model.Kampus;
import id.carikampus.helper.CirclePageIndicator;
import id.carikampus.helper.ImagePagerAdapter;
import id.carikampus.repository.FotoKampusRepository;
import id.carikampus.viewmodel.KampusDetailViewModel;

public class KampusFragment extends Fragment {

    private static final String ARG_KAMPUS_ID = "id_kampus";
    private static final String TAG = "KampusFragment";

    private Kampus mKampus;
    private KampusDetailViewModel mKampusDetailViewModel;
    private int mKampusId;

    // Fragment
    private TextInputEditText mNamaKampusText;
    private TextInputEditText mSingkatanText;
    private TextInputEditText mAkreditasiText;
    private TextInputEditText mTotalProdiText;
    private TextInputEditText mTotalDosenText;
    private TextInputEditText mBiayaSemesterMinimalText;
    private TextInputEditText mBiayaSemesterMaksimalText;
    private TextInputEditText mAlamatText;
    private TextInputEditText mTeleponText;
    private TextInputEditText mWebsiteText;
    private TextInputEditText mEmailText;

    // View Pager For Images
    ViewPager viewPager;
    ImagePagerAdapter imagePagerAdapter;
    Timer timer;

    public KampusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach called");
        mContext =  context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach called");
        mContext = null;
    }

    private Context mContext = null;

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
        Locale myIndonesianLocale = new Locale("in", "ID");
        NumberFormat formater = NumberFormat.getCurrencyInstance(myIndonesianLocale);

        Log.i(TAG, TAG + ".updateUI: called");
        mNamaKampusText.setText(mKampus.getNama_kampus());
        mSingkatanText.setText(mKampus.getSingkatan());
        mAkreditasiText.setText("Akreditasi " + mKampus.getAkreditasi());
        mTotalProdiText.setText(mKampus.getTotal_prodi() + " Prodi");
        mTotalDosenText.setText(mKampus.getTotal_dosen() + " Dosen");
        mBiayaSemesterMinimalText.setText(formater.format(mKampus.getBiaya_semester_minimal()));
        mBiayaSemesterMaksimalText.setText(formater.format(mKampus.getBiaya_semester_maksimal()));
        mAlamatText.setText(mKampus.getAlamat());
        mTeleponText.setText(mKampus.getTelepon());
        mWebsiteText.setText(mKampus.getWebsite());
        mEmailText.setText(mKampus.getEmail());

        ArrayList<String> imagesString = new ArrayList<>();
        FotoKampusRepository mFotoKampusRepository = FotoKampusRepository.get();


        getImagesString(mFotoKampusRepository.getListFotoKampus(mKampus.getId()));

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
        View v = inflater.inflate(R.layout.fragment_kampus, container, false);

        mNamaKampusText = (TextInputEditText) v.findViewById(R.id.nama_kampus);
        mSingkatanText = (TextInputEditText) v.findViewById(R.id.singkatan);
        mAkreditasiText = (TextInputEditText) v.findViewById(R.id.akreditasi);
        mTotalProdiText = (TextInputEditText) v.findViewById(R.id.total_prodi);
        mTotalDosenText = (TextInputEditText) v.findViewById(R.id.total_dosen);
        mBiayaSemesterMinimalText = (TextInputEditText) v.findViewById(R.id.biaya_semester_minimal);
        mBiayaSemesterMaksimalText = (TextInputEditText) v.findViewById(R.id.biaya_semester_maksimal);
        mAlamatText = (TextInputEditText) v.findViewById(R.id.alamat);
        mTeleponText = (TextInputEditText) v.findViewById(R.id.telepon);
        mWebsiteText = (TextInputEditText) v.findViewById(R.id.website);
        mEmailText = (TextInputEditText) v.findViewById(R.id.email);

        viewPager = (ViewPager) v.findViewById(R.id.pager);
        CirclePageIndicator pagerIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);

        imagePagerAdapter = new ImagePagerAdapter(v.getContext());

        // Hide
        View view = inflater.inflate(R.layout.fragment_list_kampus, container, false);
        view.findViewById(R.id.cari_kampus).setVisibility(View.GONE);

        viewPager.setAdapter(imagePagerAdapter);
        pagerIndicator.setViewPager(viewPager);


//        ArrayList<String> images = new ArrayList<>();
//        images.add("https://ie.binus.ac.id/files/2015/04/DSC06004UPDATE.jpg");
//        images.add("https://lh3.googleusercontent.com/p/AF1QipMyvbMxpApPuPJ88Y-cH-N_N2z1a83f3-OfbVfg=w600-h0");
//        images.add("https://asset.kompas.com/crops/wT4FMTcjLi6fY9jnR4z_VcEVYkA=/0x0:915x610/750x500/data/photo/2019/11/03/5dbe42c2a8fc8.jpg");
//
//

        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 5000);
        return v;
    }

    private void getImagesString(MutableLiveData<List<FotoKampus>> list) {
        String imageUri = "http://192.168.100.140:8080/uploads/img_kampus/";
        ArrayList<String> myString = new ArrayList<>();
        List<FotoKampus> temp;
        Log.d(TAG, "Cek list " + list.toString());

        list.observe(getViewLifecycleOwner(), new Observer<List<FotoKampus>>() {
            @Override
            public void onChanged(List<FotoKampus> fotoKampuses) {

                for (FotoKampus image : fotoKampuses) {
                    myString.add(imageUri + image.getFoto());
                    Log.d(TAG, "Cek Val " + imageUri + image.getFoto());
                }

                if (myString.isEmpty()) {
                    myString.add("https://ie.binus.ac.id/files/2015/04/DSC06004UPDATE.jpg");
                    myString.add("https://lh3.googleusercontent.com/p/AF1QipMyvbMxpApPuPJ88Y-cH-N_N2z1a83f3-OfbVfg=w600-h0");
                    myString.add("https://asset.kompas.com/crops/wT4FMTcjLi6fY9jnR4z_VcEVYkA=/0x0:915x610/750x500/data/photo/2019/11/03/5dbe42c2a8fc8.jpg");
                    myString.add("https://www.teahub.io/photos/full/303-3034192_default-banner-banner-jpg.jpg");
                }

                imagePagerAdapter.setImages(myString);
            }
        });
    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < imagePagerAdapter.getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}