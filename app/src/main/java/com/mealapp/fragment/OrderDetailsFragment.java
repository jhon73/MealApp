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
import com.mealapp.adapter.AddMenuAdapter;
import com.mealapp.adapter.OrderDetailsAdapter;
import com.mealapp.databinding.FragmentOrderDetailsBinding;

import butterknife.ButterKnife;

public class OrderDetailsFragment extends Fragment {

    private FragmentOrderDetailsBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private OrderDetailsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Order Details");
        ((MainActivity)getActivity()).setIconBack();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frOrderDetailRv.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderDetailsAdapter(getActivity(), this);
        mBinding.frOrderDetailRv.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Dashboard");
        ((MainActivity)getActivity()).setIconNavi();
    }
}

