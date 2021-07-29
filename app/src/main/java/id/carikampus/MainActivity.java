package id.carikampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.carikampus.fragment.KampusFragment;
import id.carikampus.fragment.KampusListFragment;
import id.carikampus.fragment.ProdiFragment;

public class MainActivity extends AppCompatActivity implements KampusListFragment.Callbacks, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_kampus);
        this.getSupportActionBar().hide();

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation_view);
        mBottomNav.setOnNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = KampusListFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home_menu:
                Log.d(TAG,  "Home Item");
                fragment = KampusListFragment.newInstance();
                break;
            case R.id.school_menu:
                Log.d(TAG,  "School Item");
                findViewById(R.id.cari_kampus).setVisibility(View.VISIBLE);
                fragment = KampusListFragment.newInstance();
                break;
            case R.id.favorite_menu:
                Log.d(TAG,  "Favorite Item");
                fragment = new ProdiFragment();
                break;
            case R.id.person_menu:
                Log.d(TAG,  "Person Item");
                fragment = KampusFragment.newInstance(1);
                break;
            default:
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
//                fragment = new ProdiFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onKampusSelected(int idKampus) {
        Log.i(TAG, "MainActivity.onKampusSelected : " + idKampus);
        Fragment fragment = KampusFragment.newInstance(idKampus);
        findViewById(R.id.cari_kampus).setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }
}