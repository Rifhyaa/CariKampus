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
import id.carikampus.model.Prodi;
import id.carikampus.viewmodel.ProdiListViewModel;

public class ProdiListFragment extends Fragment {

    private static final String ARG_KAMPUS_ID = "id_kampus";
    private static final String TAG = "ProdiListFragment";

    private ProdiListViewModel mProdiListViewModel;
    private RecyclerView mProdiRecyclerView;
    private ProdiListFragment.ProdiAdapter mAdapter;

    private int mKampusId;

    private void updateUI(List<Prodi> prodis) {
        mAdapter = new ProdiAdapter(prodis);
        mProdiRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, TAG + ".updateUI() Success");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKampusId = (int) getArguments().getSerializable(ARG_KAMPUS_ID);
        mProdiListViewModel = new ViewModelProvider(this).get(ProdiListViewModel.class);
        mAdapter = new ProdiAdapter(Collections.<Prodi>emptyList());

        Log.d(TAG, TAG + ".onCreate() Success");
    }

    // TODO: Rename and change types and number of parameters
    public static ProdiListFragment newInstance(int kampusId) {
        ProdiListFragment fragment = new ProdiListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_KAMPUS_ID, kampusId);
        fragment.setArguments(args);

        Log.d(TAG, TAG + ".newInstance() Success");
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mProdiRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mProdiRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProdiRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation));
        mProdiRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, TAG + ".onCreateView() Success");
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "ProdiListFragment.onViewCreated() Called!");
        mProdiListViewModel.getListProdi(mKampusId).observe(getViewLifecycleOwner(), new Observer<List<Prodi>>() {
            @Override
            public void onChanged(List<Prodi> prodis) {
                updateUI(prodis);
                Log.i(TAG, TAG + ".Got Prodi: " + prodis.size());
            }
        });
    }

    /**
     * Prodi Adapter
     */
    public class ProdiAdapter extends RecyclerView.Adapter<ProdiHolder> {

        private List<Prodi> mProdiList;

        private int lastPosition = -1;

        public ProdiAdapter(List<Prodi> prodiList) {
            mProdiList = prodiList;
        }

        @Override
        public ProdiListFragment.ProdiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ProdiHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProdiListFragment.ProdiHolder holder, int position) {
            Prodi prodi = mProdiList.get(position);
            holder.bind(prodi);

            setAnimation(holder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return mProdiList.size();
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
     * Prodi Holder
     */
    class ProdiHolder extends RecyclerView.ViewHolder {

        private Prodi mProdi;
        private TextView mNamaProdiTextView, mAkreditasiTextView;

        public ProdiHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_prodi, parent, false));
            mNamaProdiTextView = (TextView) itemView.findViewById(R.id.text_nama_prodi);
            mAkreditasiTextView = (TextView) itemView.findViewById(R.id.text_akreditasi_prodi);
        }

        public void bind(Prodi prodi) {
            mProdi = prodi;
            mNamaProdiTextView.setText(mProdi.getNama_prodi());
            mAkreditasiTextView.setText("Akreditasi " + mProdi.getAkreditasi());
        }
    }
}
