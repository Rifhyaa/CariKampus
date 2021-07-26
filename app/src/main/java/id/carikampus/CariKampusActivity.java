package id.carikampus;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import id.carikampus.fragment.KampusFragment;
import id.carikampus.fragment.KampusListFragment;
import id.carikampus.fragment.ProdiFragment;

public class CariKampusActivity extends AppCompatActivity implements KampusListFragment.Callbacks { // implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "CariKampusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kampus);
        this.getSupportActionBar().hide();

        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation_view);
//        mBottomNav.setOnNavigationItemSelectedListener(this);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = KampusListFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Fragment fragment = null;
//        switch (item.getItemId()) {
//            case R.id.home:
//                fragment = new Fragment();
//                break;
////            default:
////            case R.id.search_menu:
////                fragment = new SearchFragment();
////                break;
////            case R.id.favorite_menu:
////                fragment = new FavoriteFragment();
////                break;
////            case R.id.account_menu:
////                fragment = new AccountFragment();
////                break;
////        }
////        return loadFragment(fragment);
//            default:
//
//        }
//        return false;
//    }

        private boolean loadFragment(Fragment fragment) {
            if (fragment != null) {
                fragment = new ProdiFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        }

    @Override
    public void onKampusSelected(int idKampus) {

        Log.i(TAG, "MainActivity.onUserSelected : " + idKampus);
        //Fragment fragment = new UserFragment();
        Fragment fragment = KampusFragment.newInstance(idKampus);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }
}
