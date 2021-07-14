package id.carikampus.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.carikampus.R;
import id.carikampus.data.model.OnBoarding;


public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {

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

    class OnBoardingViewHolder extends RecyclerView.ViewHolder {

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
