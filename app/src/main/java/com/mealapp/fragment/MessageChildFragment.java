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
import com.mealapp.adapter.MessageChildAdapter;
import com.mealapp.adapter.MessageMainAdapter;
import com.mealapp.databinding.FragmentMessageChildBinding;
import com.mealapp.databinding.FragmentMessageMainBinding;

import butterknife.ButterKnife;

public class MessageChildFragment extends Fragment {
    private FragmentMessageChildBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private MessageChildAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Messaging");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message_child, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frMsgChildRv.setLayoutManager(linearLayoutManager);
        mAdapter = new MessageChildAdapter(getActivity(), this);
        mBinding.frMsgChildRv.setAdapter(mAdapter);
    }
}
