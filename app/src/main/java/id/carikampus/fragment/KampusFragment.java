package id.carikampus.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import id.carikampus.R;
import id.carikampus.helper.CariKampusConstants;
import id.carikampus.helper.CariKampusMethods;
import id.carikampus.helper.Preferences;
import id.carikampus.model.FotoKampus;
import id.carikampus.model.Kampus;
import id.carikampus.helper.CirclePageIndicator;
import id.carikampus.helper.ImagePagerAdapter;
import id.carikampus.model.KampusFavorit;
import id.carikampus.model.Komentar;
import id.carikampus.repository.FotoKampusRepository;
import id.carikampus.viewmodel.FotoKampusViewModel;
import id.carikampus.viewmodel.KampusDetailViewModel;
import id.carikampus.viewmodel.KampusFavoritViewModel;
import id.carikampus.viewmodel.KomentarViewModel;

public class KampusFragment extends Fragment {

    private static final String ARG_KAMPUS_ID = "id_kampus";
    private static final String TAG = "KampusFragment";

    private int mKampusId;
    private int mUserId;

    private Kampus mKampus;
    private List<KampusFavorit> mKampusFavorits;

    private RecyclerView mKomentarRecyclerView;
    private KomentarAdapter mAdapter;

    // View Model
    private KampusDetailViewModel mKampusDetailViewModel;
    private FotoKampusViewModel mFotoKampusViewModel;
    private KomentarViewModel mKomentarViewModel;

    // Fragment Component
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

    private TextView mTotalFavoritTextView;
    private ToggleButton mToggleButton;

    private TextInputLayout mTextLayoutTotalProdi;
    private TextInputLayout mTextLayoutAlamat;
    private TextInputLayout mTextLayoutTelepon;
    private TextInputLayout mTextLayoutWebsite;
    private TextInputLayout mTextLayoutEmail;

    // View Pager For Images
    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private Timer timer;

    public KampusFragment() {
        // Required empty public constructor
    }

    private Callbacks mCallbacks = null;

    public interface Callbacks {
        public void onProdiSelected(int idKampus);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach called");
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach called");
        mCallbacks = null;
    }

    // ViewModel Init
    public KampusDetailViewModel getKampusDetailViewModel() {
        if (mKampusDetailViewModel == null) {
            mKampusDetailViewModel = new ViewModelProvider(this)
                    .get(KampusDetailViewModel.class);
        }

        return mKampusDetailViewModel;
    }

    public FotoKampusViewModel getFotoKampusViewModel() {
        if (mFotoKampusViewModel == null) {
            mFotoKampusViewModel = new ViewModelProvider(this)
                    .get(FotoKampusViewModel.class);
        }

        return mFotoKampusViewModel;
    }

    public KomentarViewModel getKomentarViewModel() {
        if (mKomentarViewModel == null) {
            mKomentarViewModel = new ViewModelProvider(this)
                    .get(KomentarViewModel.class);
        }

        return mKomentarViewModel;
    }

    public static KampusFragment newInstance(int kampusId) {
        KampusFragment fragment = new KampusFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KAMPUS_ID, kampusId);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateUI() {
        Log.i(TAG, TAG + ".updateUI: called");
        mNamaKampusText.setText(mKampus.getNama_kampus());
        mSingkatanText.setText(mKampus.getSingkatan());
        mAkreditasiText.setText("Akreditasi " + mKampus.getAkreditasi());
        mTotalProdiText.setText(mKampus.getTotal_prodi() + " Prodi");
        mTotalDosenText.setText(mKampus.getTotal_dosen() + " Dosen");
        mBiayaSemesterMinimalText.setText(CariKampusMethods.getNumberFormat().format(mKampus.getBiaya_semester_minimal()));
        mBiayaSemesterMaksimalText.setText(CariKampusMethods.getNumberFormat().format(mKampus.getBiaya_semester_maksimal()));
        mAlamatText.setText(mKampus.getAlamat());
        mTeleponText.setText(mKampus.getTelepon());
        mWebsiteText.setText(mKampus.getWebsite());
        mEmailText.setText(mKampus.getEmail());

        mTotalFavoritTextView.setText(CariKampusMethods.getTotalFavoritByStatus(mKampusFavorits) + " Favorits");

        mToggleButton.setChecked(CariKampusMethods.isUserFavoriteIt(mKampusFavorits, mUserId));
        mToggleButton.setEnabled(true);

        getImagesString(mFotoKampusViewModel.getDetailKampusFavorit(mKampusId));

        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                KampusFavorit myKampusFavorit = new KampusFavorit();
                myKampusFavorit.setId_kampus(mKampusId);
                myKampusFavorit.setId_user_login(mUserId);
                myKampusFavorit.setStatus(isChecked ? 1 : 0);

                updateFavorit(myKampusFavorit);
            }
        });
    }

    private void updateKomentarUI(List<Komentar> komentars) {
        this.mAdapter = new KomentarAdapter(komentars);
        this.mKomentarRecyclerView.setAdapter(mAdapter);
    }

    private void updateFavorit(KampusFavorit kampusFavorit) {

        Log.d(TAG, "ID KAMPUS : " + mKampusId + " USER ID : " + mUserId);

        mKampusDetailViewModel.saveKampusFavorit(kampusFavorit).observe(
                getViewLifecycleOwner(),
                new Observer<KampusFavorit>() {
                    @Override
                    public void onChanged(KampusFavorit kampusFavorit) {
                        if (kampusFavorit.getStatus() == 1) {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Berhasil")
                                    .setContentText("Kampus difavoritkan")
                                    .show();
                        }
                        else {
                            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Berhasil")
                                    .setContentText("Kampus tidak difavoritkan")
                                    .show();
                        }

                    }
                });

        mKampusDetailViewModel.getDetailKampusFavorit(mKampusId).observe(
                getViewLifecycleOwner(),
                new Observer<List<KampusFavorit>>() {
                    @Override
                    public void onChanged(List<KampusFavorit> kampusFavorits) {
                        mKampusFavorits = kampusFavorits;
                        mTotalFavoritTextView.setText(CariKampusMethods.getTotalFavoritByStatus(mKampusFavorits) + " Favorits");
                    }
                }
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate() called");
        mKampusId = (int) getArguments().getSerializable(ARG_KAMPUS_ID);
        Preferences.setIdKampus(getContext(), (int) getArguments().getSerializable(ARG_KAMPUS_ID));
        Log.i(TAG, "args bundle is : " + mKampusId);
        mUserId = Preferences.getIdUser(getContext());
        mKampus = new Kampus();
        mKampusDetailViewModel = getKampusDetailViewModel();
        mFotoKampusViewModel = getFotoKampusViewModel();
        mKomentarViewModel = getKomentarViewModel();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "UserFragment.onViewCreated: called");
        mKampusDetailViewModel.getDetailKampusFavorit(mKampusId).observe(
                getViewLifecycleOwner(),
                new Observer<List<KampusFavorit>>() {
                    @Override
                    public void onChanged(List<KampusFavorit> kampusFavorits) {
                        mKampusFavorits = kampusFavorits;
                        Log.d(TAG, TAG + "Got Total Favorits: " + kampusFavorits.size());
                    }
                }
        );

        mKomentarViewModel.getListKomentar(mKampusId).observe(
                getViewLifecycleOwner(),
                new Observer<List<Komentar>>() {
                    @Override
                    public void onChanged(List<Komentar> komentars) {
                        Log.d(TAG, TAG + " Got Komentar: " + komentars.size());
                        updateKomentarUI(komentars);
                    }
                }
        );

        mKampusDetailViewModel.getKampusLiveData(mKampusId).observe(
                getViewLifecycleOwner(),
                new Observer<Kampus>() {
                    @Override
                    public void onChanged(Kampus kampus) {
                        mKampus = kampus;
                        updateUI();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kampus, container, false);

        mKomentarRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_komentar);
        mKomentarRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new KomentarAdapter();
        mKomentarRecyclerView.setAdapter(mAdapter);

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

        mTotalFavoritTextView = (TextView) v.findViewById(R.id.total_favorite);
        mToggleButton = (ToggleButton) v.findViewById(R.id.button_favorite);

        mTextLayoutTotalProdi = v.findViewById(R.id.til_total_prodi);
        mTextLayoutAlamat = v.findViewById(R.id.til_alamat);
        mTextLayoutTelepon = v.findViewById(R.id.til_telepon);
        mTextLayoutWebsite = v.findViewById(R.id.til_website);
        mTextLayoutEmail = v.findViewById(R.id.til_email);

        viewPager = (ViewPager) v.findViewById(R.id.pager);
        CirclePageIndicator pagerIndicator = (CirclePageIndicator) v.findViewById(R.id.indicator);
        imagePagerAdapter = new ImagePagerAdapter(v.getContext());

        viewPager.setAdapter(imagePagerAdapter);
        pagerIndicator.setViewPager(viewPager);

        setListenerComponent();

        timer = new Timer();
//        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 5000);
        return v;
    }

    private void setListenerComponent() {
        mTextLayoutTotalProdi.setEndIconOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                // Checking List Prodi Kampus
                mCallbacks.onProdiSelected(mKampus.getId());
            }
        });

        mTextLayoutAlamat.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go To Maps
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + mKampus.getNama_kampus()));
                startActivity(intent);
            }
        });

        mTextLayoutTelepon.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go To Telephone
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mKampus.getTelepon()));
                startActivity(intent);
            }
        });

        mTextLayoutWebsite.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go To Website
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mKampus.getWebsite()));
                startActivity(intent);
            }
        });

        mTextLayoutEmail.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go To Email
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{mKampus.getEmail()});
                email.putExtra(Intent.EXTRA_SUBJECT, "Saya tertarik dengan kampus ini");
                email.putExtra(Intent.EXTRA_TEXT, "");

                // This needed to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }

    private void getImagesString(MutableLiveData<List<FotoKampus>> list) {
        list.observe(getViewLifecycleOwner(), new Observer<List<FotoKampus>>() {
            @Override
            public void onChanged(List<FotoKampus> fotoKampuses) {
                // Set Image Adapter
                imagePagerAdapter.setImages(CariKampusMethods.getUriStringImages(fotoKampuses));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Komentar Adapter
     */
    public class KomentarAdapter extends RecyclerView.Adapter<KomentarHolder> {

        private List<Komentar> mKomentars;

        private int lastPosition = -1;

        public KomentarAdapter() {
            mKomentars = new ArrayList<>();;
        }

        public KomentarAdapter(List<Komentar> komentar) {
            mKomentars = komentar;
            notifyDataSetChanged();
        }

        @Override
        public KomentarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new KomentarHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(KomentarHolder holder, int position) {
            Komentar komentar = mKomentars.get(position);
            holder.bind(komentar);

            setAnimation(holder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return mKomentars.size();
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_animation_fall_down);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
    }

    /**
     * Komentar Holder
     */
    class KomentarHolder extends RecyclerView.ViewHolder {

        private Komentar mKomentar;
        private TextView mNamaUserTextView, mKomentarTextView;


        public KomentarHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_komentar, parent, false));

            mNamaUserTextView = (TextView) itemView.findViewById(R.id.text_nama_user);
            mKomentarTextView = (TextView) itemView.findViewById(R.id.text_komentar);
//            mLogoImageView = (ImageView) itemView.findViewById(R.id.foto_logo_kampus);
//            mNamaKampusTextView = (TextView) itemView.findViewById(R.id.text_nama_kampus);
//            mTotalProdiTextView = (TextView) itemView.findViewById(R.id.text_total_prodi);
//            mAkreditasiTextView = (TextView) itemView.findViewById(R.id.text_akreditasi);
//            mToggleButton = (ToggleButton) itemView.findViewById(R.id.button_favorite);
//
//            mNamaKampusTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), mKampus.getId() + " Clicked!", Toast.LENGTH_SHORT).show();
//                    mCallbacks.onKampusSelected(mKampus.getId());
//                }
//            });
//
//            mToggleButton.setEnabled(false);
        }

        public void bind(Komentar komentar) {

            mNamaUserTextView.setText(komentar.getUserLogin().getNama());
            mKomentarTextView.setText(komentar.getKomentar());
//            mKampus = kampus;
//
//            String uri = CariKampusConstants.URL_LOGO_KAMPUS + kampus.getFoto_logo();
//
//            Glide.with(mLogoImageView.getContext())
//                    .load(uri)
//                    .placeholder(R.drawable.undraw_void)
//                    .error(R.drawable.undraw_search)
//                    .into(mLogoImageView);
//
//
//            mNamaKampusTextView.setText(mKampus.getNama_kampus());
//            mTotalProdiTextView.setText(mKampus.getTotal_prodi() + " Prodi");
//            mAkreditasiTextView.setText("Akreditasi " + mKampus.getAkreditasi());
//            mToggleButton.setChecked((mKampus.getLiked() == 1));
        }
    }
}