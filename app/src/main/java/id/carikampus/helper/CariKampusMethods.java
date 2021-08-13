package id.carikampus.helper;

import android.util.Log;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import id.carikampus.R;
import id.carikampus.model.FotoKampus;
import id.carikampus.model.Kampus;
import id.carikampus.model.KampusFavorit;
import id.carikampus.model.OnBoarding;

public class CariKampusMethods {

    private static final String TAG = "CariKampusMethods";

    public static List<OnBoarding> getListItemBoarding() {
        List<OnBoarding> models = new ArrayList<>();

        OnBoarding model_one = new OnBoarding();
        model_one.setTitle(R.string.onboarding_heading_one);
        model_one.setDescription(R.string.onboaring_desc_one);
        model_one.setImage(R.drawable.undraw_search);

        OnBoarding model_two = new OnBoarding();
        model_two.setTitle(R.string.onboarding_heading_two);
        model_two.setDescription(R.string.onboaring_desc_two);
        model_two.setImage(R.drawable.undraw_select);

        OnBoarding model_three = new OnBoarding();
        model_three.setTitle(R.string.onboarding_heading_three);
        model_three.setDescription(R.string.onboaring_desc_three);
        model_three.setImage(R.drawable.undraw_define);

        models.add(model_one);
        models.add(model_two);
        models.add(model_three);

        return models;
    }

    public static List<Kampus> getListKampusByFavorite(List<KampusFavorit> kampusFavorits, int idUser) {
        List<Kampus> list = new ArrayList<>();

        // Check if value is null
        if (kampusFavorits == null) {
            Log.d(TAG, ".getListKampusByFavorite() Message : ListKampusFavorit is null");
            return list;
        }

        for (KampusFavorit data : kampusFavorits) {
            if (data.getId_user_login() == idUser && data.getStatus() == 1) {
                list.add(data.getKampus());
            }
        }

        return list;
    }

    public static int getTotalFavoritByStatus(List<KampusFavorit> kampusFavorits) {
        int iter = 0;

        // Check if value is null
        if (kampusFavorits == null) {
            Log.d(TAG, ".getTotalFavoritByStatus() Message : ListKampusFavorit is null");
            return iter;
        }

        for (KampusFavorit data : kampusFavorits) {
            if (data.getStatus() == 1) {
                iter++;
            }
        }

        return iter;
    }

    public static boolean isUserFavoriteIt(List<KampusFavorit> kampusFavorits, int idUser) {

        // Check if value is null
        if (kampusFavorits == null) {
            Log.d(TAG, ".isUserFavoriteIt() Message : ListKampusFavorit is null");
            return false;
        }

        for (KampusFavorit data : kampusFavorits) {
            if (data.getId_user_login() == idUser && data.getStatus() == 1) {
                return true;
            }
        }

        return false;
    }

    public static Kampus isUserFavoriteKampus(Kampus kampus, List<KampusFavorit> kampusFavorits) {

        // Set default value first
        kampus.setLiked(0);

        // Check if value is null
        if (kampusFavorits == null) {
            Log.d(TAG, ".isUserFavoriteKampus() Message : ListKampusFavorit is null");
            return kampus;
        }

        for (KampusFavorit data : kampusFavorits) {
            if (data.getKampus().getId().equals(kampus.getId())) {
                kampus.setLiked(data.getStatus());
            }
        }

        return kampus;
    }

    public static ArrayList<String> getUriStringImages(List<FotoKampus> fotoKampuses) {
        ArrayList<String> myString = new ArrayList<>();

        for (FotoKampus image : fotoKampuses) {
            myString.add(CariKampusConstants.URL_FOTO_KAMPUS + image.getFoto());
        }

        if (myString.isEmpty()) {
            myString.add("https://www.teahub.io/photos/full/303-3034192_default-banner-banner-jpg.jpg");
        }

        return myString;
    }

    public static NumberFormat getNumberFormat() {
        Locale myIndonesianLocale = new Locale("in", "ID");

        return NumberFormat.getCurrencyInstance(myIndonesianLocale);
    }
}
