package id.carikampus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import id.carikampus.R;
import id.carikampus.model.Kampus;
import id.carikampus.viewmodel.KampusListViewModel;

public class KampusListFragment extends Fragment {

    private static final String TAG = "KampusListFragment";

    private KampusListViewModel mKampusListViewModel;
    private RecyclerView mKampusRecyclerView;
    private KampusAdapter mAdapter;

    public interface Callbacks {
        public void onKampusSelected(int idKampus);
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

    private Callbacks mCallbacks = null;

    private void updateUI(List<Kampus> kampus) {
        mAdapter = new KampusAdapter(kampus);
        mKampusRecyclerView.setAdapter(mAdapter);

        Log.i(TAG, TAG + ".updateUI() Success");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKampusListViewModel = new ViewModelProvider(this).get(KampusListViewModel.class);
        mAdapter = new KampusAdapter(Collections.<Kampus>emptyList());

        Log.i(TAG, TAG + ".onCreate() Success");
    }

    public static KampusListFragment newInstance() {
        return new KampusListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mKampusRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mKampusRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mKampusRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation));
        mKampusRecyclerView.setAdapter(mAdapter);

        Log.i(TAG, TAG + ".onCreateView() Success");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "KampusListFragment.onViewCreated() Called!");
        mKampusListViewModel.getListKampus().observe(
                getViewLifecycleOwner(),
                new Observer<List<Kampus>>() {
                    @Override
                    public void onChanged(List<Kampus> kampus) {
                        // Update the cached copy of
                        updateUI(kampus);
                        Log.i(TAG, TAG + ".Got Kampus: " + kampus.size());
                    }
                }
        );
    }

    /**
     * Kampus Adapter
     */
    public class KampusAdapter extends RecyclerView.Adapter<KampusHolder> {

        private List<Kampus> mKampusList;

        private int lastPosition = -1;

        public KampusAdapter(List<Kampus> kampus) {
            mKampusList = kampus;
        }

        @Override
        public KampusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new KampusHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(KampusHolder holder, int position) {
            Kampus Kampus = mKampusList.get(position);
            holder.bind(Kampus);

            setAnimation(holder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return mKampusList.size();
        }

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                // android.R.anim.slide_in_left
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.item_animation_fall_down);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }
    }

    /**
     * Kampus Holder
     */
    class KampusHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Kampus mKampus;
        private ImageView mLogoImageView;
        private TextView mNamaKampusTextView, mTotalProdiTextView, mAkreditasiTextView;

        String imageUri = "http://192.168.100.140:8080/uploads/logo_kampus/";

        public KampusHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_kampus, parent, false));
            mLogoImageView = (ImageView) itemView.findViewById(R.id.foto_logo_kampus);
            mNamaKampusTextView = (TextView) itemView.findViewById(R.id.text_nama_kampus);
            mTotalProdiTextView = (TextView) itemView.findViewById(R.id.text_total_prodi);
            mAkreditasiTextView = (TextView) itemView.findViewById(R.id.text_akreditasi);

            mNamaKampusTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), mKampus.getId() + " Clicked!", Toast.LENGTH_SHORT).show();
                    mCallbacks.onKampusSelected(mKampus.getId());
                }
            });
        }

        public void bind(Kampus kampus) {
            mKampus = kampus;

            String uri = imageUri + kampus.getFoto_logo();
//            Picasso.get().load(uri).placeholder(R.drawable.undraw_void).into(mLogoImageView);
//            Picasso.get().setLoggingEnabled(true);
            Log.d(TAG, uri);

            Glide.with(mLogoImageView.getContext())
                    .load(uri)
//                    .centerCrop()
                    //.transform(new RoundedCorners(5))
                    .placeholder(R.drawable.undraw_void)
                    .error(R.drawable.undraw_search)
                    .into(mLogoImageView);


            mNamaKampusTextView.setText(mKampus.getNama_kampus());
            mTotalProdiTextView.setText(mKampus.getTotal_prodi() + " Prodi");
            mAkreditasiTextView.setText("Akreditasi " + mKampus.getAkreditasi());
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onKampusSelected(mKampus.getId());
            Toast.makeText(getActivity(), mKampus.getId() + " Clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
