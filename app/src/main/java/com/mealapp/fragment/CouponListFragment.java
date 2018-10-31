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
import com.mealapp.adapter.CouponListAdapter;
import com.mealapp.databinding.FragmentCouponListBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponListFragment extends Fragment {
    private FragmentCouponListBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private CouponListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Coupon");
        ((MainActivity)getActivity()).setIconBack();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coupon_list, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frCouponListRv.setLayoutManager(linearLayoutManager);
        mAdapter = new CouponListAdapter(getActivity(), this);
        mBinding.frCouponListRv.setAdapter(mAdapter);
    }

    @OnClick(R.id.fr_coupon_add_btn)
    public void onClickAddCoupon(){
        CouponAddFragment nextFrag= new CouponAddFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fr_coupon_list_ll_frame, nextFrag,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Dashboard");
        ((MainActivity)getActivity()).setIconNavi();
    }
}
