package id.carikampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.carikampus.fragment.DashboardFragment;
import id.carikampus.fragment.KampusFavoritFragment;
import id.carikampus.fragment.KampusFragment;
import id.carikampus.fragment.KampusListFragment;
import id.carikampus.fragment.ProdiFragment;
import id.carikampus.fragment.ProdiListFragment;
import id.carikampus.fragment.SettingsFragment;
import id.carikampus.helper.Preferences;
import id.carikampus.model.KampusFavorit;

public class MainActivity extends AppCompatActivity implements KampusListFragment.Callbacks, BottomNavigationView.OnNavigationItemSelectedListener, KampusFragment.Callbacks, KampusFavoritFragment.Callbacks {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Color.parseColor("#FB770D"))));
        this.getSupportActionBar().setIcon(R.drawable.icon_logo);
        this.getSupportActionBar().setDisplayUseLogoEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation_view);
        mBottomNav.setOnNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new DashboardFragment();
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
                fragment = new DashboardFragment();
                break;
            case R.id.school_menu:
                Log.d(TAG,  "School Item");
                fragment = KampusListFragment.newInstance(Preferences.getIdUser(getApplicationContext()));
                break;
            case R.id.favorite_menu:
                Log.d(TAG,  "Favorite Item");
                fragment = KampusFavoritFragment.newInstance(Preferences.getIdUser(getApplicationContext()));
                break;
            case R.id.person_menu:
                Log.d(TAG,  "Person Item");
                fragment = new SettingsFragment();
                break;
            default:
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onProdiSelected(int idKampus) {
        Log.i(TAG, "MainActivity.onProdiSelected : " + idKampus);
        Fragment fragment = ProdiListFragment.newInstance(idKampus);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }
}