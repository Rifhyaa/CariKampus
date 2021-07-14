package id.carikampus.helper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import id.carikampus.R;
import id.carikampus.data.model.OnBoarding;

public class CariKampusMethods {

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

    public static void printLog(String TAG, String key) {
        Log.d(TAG, key);
    }
}
