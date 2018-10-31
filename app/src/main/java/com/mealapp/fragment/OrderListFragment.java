package com.mealapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.adapter.AddMenuAdapter;
import com.mealapp.adapter.OrderListAdapter;
import com.mealapp.adapter.StatusAdapter;
import com.mealapp.databinding.FragmentOrderListBinding;

import butterknife.ButterKnife;

public class OrderListFragment extends Fragment implements OrderListAdapter.OrderListInterface {
    private FragmentOrderListBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private OrderListAdapter mAdapter;
    private StatusAdapter statusAdapter;
    private String[] statusData = new String[3];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Order List");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_list, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusData[0] = "Pending";
        statusData[1] = "In Progress";
        statusData[2] = "Dispatch";

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frOrderItemRv.setLayoutManager(linearLayoutManager);
        mAdapter = new OrderListAdapter(getActivity(), this);
        mBinding.frOrderItemRv.setAdapter(mAdapter);
    }

    @Override
    public void onClickOrderListOption(int pos) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        statusAdapter = new StatusAdapter(getActivity(), android.R.layout.simple_spinner_item, statusData);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setAdapter(statusAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                    }
                }).create();
        alertDialog.show();
    }
}
