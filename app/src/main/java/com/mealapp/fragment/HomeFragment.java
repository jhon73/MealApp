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
import com.mealapp.adapter.HomeAdapter;
import com.mealapp.databinding.FragmentHomeBinding;

import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private HomeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Dashboard");
        ((MainActivity)getActivity()).setIconNavi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frHomeRv.setLayoutManager(linearLayoutManager);
        mAdapter = new HomeAdapter(getActivity(), this);
        mBinding.frHomeRv.setAdapter(mAdapter);


    }
}
