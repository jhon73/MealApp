package com.mealapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.R;
import com.mealapp.databinding.RowReviewPopularBinding;
import com.mealapp.fragment.ReviewMainFragment;

public class PopularMealAdapter extends RecyclerView.Adapter<PopularMealAdapter.ViewHolder> {

    private RowReviewPopularBinding mBinding;
    private Context context;

    public PopularMealAdapter(FragmentActivity activity, ReviewMainFragment reviewMainFragment) {
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_review_popular, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowReviewPopularBinding mBinding;
        public ViewHolder(RowReviewPopularBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
