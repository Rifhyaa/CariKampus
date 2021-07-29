package id.carikampus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import id.carikampus.R;
import id.carikampus.data.model.Kampus;
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
        }

        @Override
        public int getItemCount() {
            return mKampusList.size();
        }
    }

    /**
     * Kampus Holder
     */
    class KampusHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Kampus mKampus;
        private TextView mNamaKampusTextView, mTotalProdiTextView, mAkreditasiTextView;

        public KampusHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_kampus, parent, false));
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
            mNamaKampusTextView.setText(mKampus.getNama_kampus());
            mTotalProdiTextView.setText(mKampus.getTotal_prodi() + " Prodi");
            mAkreditasiTextView.setText(mKampus.getAkreditasi());
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onKampusSelected(mKampus.getId());
            Toast.makeText(getActivity(), mKampus.getId() + " Clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
