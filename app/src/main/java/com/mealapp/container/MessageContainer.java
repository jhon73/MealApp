package com.mealapp.container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.R;
import com.mealapp.fragment.CouponListFragment;
import com.mealapp.fragment.MessageMainFragment;

public class MessageContainer extends BaseContainer{
    private MessageMainFragment messageMainFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (messageMainFragment == null)
            messageMainFragment = new MessageMainFragment();
        replaceFragment(messageMainFragment);
    }
}
