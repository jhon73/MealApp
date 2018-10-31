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
import com.mealapp.adapter.MessageMainAdapter;
import com.mealapp.adapter.OrderDetailsAdapter;
import com.mealapp.databinding.FragmentMessageMainBinding;
import com.mealapp.databinding.FragmentOrderDetailsBinding;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageMainFragment extends Fragment {
    private FragmentMessageMainBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private MessageMainAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Messaging");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_main, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frMessageMainRv.setLayoutManager(linearLayoutManager);
        mAdapter = new MessageMainAdapter(getActivity(), this);
        mBinding.frMessageMainRv.setAdapter(mAdapter);
    }
}
