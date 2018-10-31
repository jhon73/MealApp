package com.mealapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.databinding.FragmentReviewChildBinding;

import butterknife.ButterKnife;

public class ReviewChildFragment extends Fragment {
    private FragmentReviewChildBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Reviews");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_child, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }
}
