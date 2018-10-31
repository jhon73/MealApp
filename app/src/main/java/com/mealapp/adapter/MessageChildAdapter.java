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
import com.mealapp.databinding.RowMessageChildBinding;
import com.mealapp.databinding.RowMessageMainBinding;
import com.mealapp.fragment.MessageChildFragment;

public class MessageChildAdapter extends RecyclerView.Adapter<MessageChildAdapter.ViewHolder> {
    private RowMessageChildBinding mBinding;
    private Context context;

    public MessageChildAdapter(FragmentActivity activity, MessageChildFragment messageChildFragment) {
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_message_child, parent, false);
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
        private RowMessageChildBinding mBinding;
        public ViewHolder(RowMessageChildBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
