package id.carikampus.fragment;

import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

//    interface Callbacks {
//        public void onUserSelected(String userId);
//    }
//
//    private Callbacks mCallbacks = null;

    private void updateUI(List<Kampus> kampus) {
        //List<Kampus> Kampus = mKampusListViewModel.getKampus();
        Log.i(TAG, "updateUI called");
        mAdapter = new KampusAdapter(kampus);
        mKampusRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "KampusListFragment.onCreate() called");
        //setHasOptionsMenu(true);
        mKampusListViewModel = new ViewModelProvider(this).get(KampusListViewModel.class);
        mAdapter = new KampusAdapter(Collections.<Kampus>emptyList());
        Log.d(TAG, "Total Kampus: 10");
    }

    public static KampusListFragment newInstance() {
        return new KampusListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "KampusListFragment.onCreateView() Called");
        View view = inflater.inflate(R.layout.fragment_kampus_list, container, false);
        mKampusRecyclerView = (RecyclerView) view.findViewById(R.id.kampus_recycler_view);
        mKampusRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mKampusRecyclerView.setAdapter(mAdapter);
//                bottomNavigationView.setOnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.home->setCurrentFragment(firstFragment)
//                R.id.person->setCurrentFragment(secondFragment)
//                R.id.settings->setCurrentFragment(thirdFragment)
//
//            }
//            true
//        }
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
                        Log.i(TAG, "Got Kampus: " + kampus.size());
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
//            View view = layoutInflater.inflate(R.layout.list_item_kampus, parent, false);
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
    static class KampusHolder extends RecyclerView.ViewHolder {

        private Kampus mKampus;
        private TextView mNamaKampusTextView, mTotalProdiTextView, mAkreditasiTextView;

        public KampusHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_kampus, parent, false));
//            itemView.setOnClickListener(this);

            mNamaKampusTextView = (TextView) itemView.findViewById(R.id.text_nama_kampus);
            mTotalProdiTextView = (TextView) itemView.findViewById(R.id.text_total_prodi);
            mAkreditasiTextView = (TextView) itemView.findViewById(R.id.text_akreditasi);

        }

        public void bind(Kampus kampus) {
            mKampus = kampus;
            mNamaKampusTextView.setText(mKampus.getNama_kampus());
            mTotalProdiTextView.setText(mKampus.getTotal_prodi() + " Prodi");
            mAkreditasiTextView.setText(mKampus.getAkreditasi());
        }
//
//        @Override
//        public void onClick(View v) {
//            mCallbacks.onKampusSelected(mKampus.getId());
//            //Toast.makeText(getActivity(), mKampus.getKampusname() + " Clicked!", Toast.LENGTH_SHORT).show();
//        }
    }
}
