package com.mealapp.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.adapter.OrderListAdapter;
import com.mealapp.adapter.PopularMealAdapter;
import com.mealapp.adapter.ReviewShowAdapter;
import com.mealapp.databinding.FragmentDeliveryRateBinding;
import com.mealapp.databinding.FragmentReviewMainBinding;

import butterknife.ButterKnife;

public class ReviewMainFragment extends Fragment {
    private FragmentReviewMainBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private PopularMealAdapter popularMealAdapter;
    private ReviewShowAdapter reviewShowAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Reviews");
        ((MainActivity) getActivity()).setMenuActionBar("remove");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_main, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ipokArrayList = new ArrayList<>();
//
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mBinding.frReviewMainRv.setLayoutManager(linearLayoutManager);
        popularMealAdapter = new PopularMealAdapter(getActivity(), this);
        mBinding.frReviewMainRv.setAdapter(popularMealAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frRvShowReview.setLayoutManager(linearLayoutManager);
        reviewShowAdapter = new ReviewShowAdapter(getActivity(), this);
        mBinding.frRvShowReview.setAdapter(reviewShowAdapter);
    }
}
