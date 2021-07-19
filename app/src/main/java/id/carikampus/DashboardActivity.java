package id.carikampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import id.carikampus.data.model.Kampus;
import id.carikampus.fragment.KampusListFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.recycler_view);
//        if (fragment == null) {
//            //fragment = new UserFragment();
//            fragment = KampusListFragment.newInstance();
//            fm.beginTransaction().add(R.id.recycler_view, fragment)
//                    .commit();
//        }
    }
}