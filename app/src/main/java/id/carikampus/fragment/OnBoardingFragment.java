package id.carikampus.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.carikampus.R;
import id.carikampus.model.OnBoarding;

public class OnBoardingFragment extends Fragment {

    private static final String TAG = "OnBoardingFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.item_introduction, container, false);

        Log.d(TAG, TAG + ".onCreateView() Success");
        return root;
    }

    public static class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

        private List<OnBoarding> OnBoardingItems;

        public OnBoardingAdapter(List<OnBoarding> OnBoardingItems) {
            this.OnBoardingItems = OnBoardingItems;
        }

        @NonNull
        @Override
        public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new OnBoardingViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.item_introduction, parent, false
                    )
            );
        }

        @Override
        public void onBindViewHolder(@NonNull OnBoardingViewHolder holder, int position) {
            holder.setOnBoardingData(OnBoardingItems.get(position));
        }

        @Override
        public int getItemCount() {
            return OnBoardingItems.size();
        }

        static class OnBoardingViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;
            private TextView mHeaderText;
            private TextView mDescriptionText;

            public OnBoardingViewHolder(@NonNull View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.image_onboarding);
                mHeaderText = itemView.findViewById(R.id.header_onboarding);
                mDescriptionText = itemView.findViewById(R.id.desc_onboarding);
            }

            private void setOnBoardingData(OnBoarding OnBoardingItem) {
                mImageView.setImageResource(OnBoardingItem.getImage());
                mHeaderText.setText(OnBoardingItem.getTitle());
                mDescriptionText.setText(OnBoardingItem.getDescription());
            }
        }
    }
}
