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
import com.mealapp.databinding.RowHomeBinding;
import com.mealapp.fragment.HomeFragment;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private RowHomeBinding mBinding;

    public HomeAdapter(FragmentActivity activity, HomeFragment homeFragment) {
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_home, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RowHomeBinding mBinding;

        public ViewHolder(RowHomeBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
