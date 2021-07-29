package id.carikampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import id.carikampus.fragment.OnBoardingFragment;
import id.carikampus.helper.CariKampusMethods;

public class IntroductionActivity extends AppCompatActivity {

    private static final String TAG = "IntroductionActivity";
    private static final int NUM_PAGES = 3;

    private OnBoardingFragment.OnBoardingAdapter mOnBoardingAdapter;
    private LinearLayout mLayoutOnBoarding;
    private MaterialButton mButtonOnBoarding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();

        setContentView(R.layout.activity_introduction);

        mLayoutOnBoarding = findViewById(R.id.layoutOnboardingIndicators);
        mButtonOnBoarding = findViewById(R.id.buttonOnBoardingAction);
        mOnBoardingAdapter = new OnBoardingFragment.OnBoardingAdapter(CariKampusMethods.getListItemBoarding());

        ViewPager2 mOnBoardingViewPager = findViewById(R.id.onboardingViewPager);
        mOnBoardingViewPager.setAdapter(mOnBoardingAdapter);
        setOnboadingIndicator();
        setCurrentOnboardingIndicators(0);
        mOnBoardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicators(position);
            }
        });
        mButtonOnBoarding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnBoardingViewPager.getCurrentItem() + 1 < mOnBoardingAdapter.getItemCount()) {
                    mOnBoardingViewPager.setCurrentItem(mOnBoardingViewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void setOnboadingIndicator() {
        ImageView[] indicators = new ImageView[mOnBoardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(), R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            mLayoutOnBoarding.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicators(int index) {
        int childCount = mLayoutOnBoarding.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) mLayoutOnBoarding.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive));
            }
        }
        if (index == mOnBoardingAdapter.getItemCount() - 1){
            mButtonOnBoarding.setText("Start");
        }else {
            mButtonOnBoarding.setText("Next");
        }
    }
}