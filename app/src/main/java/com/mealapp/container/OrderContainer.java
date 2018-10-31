package com.mealapp.container;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.R;
import com.mealapp.fragment.OrderDetailsFragment;
import com.mealapp.fragment.OrderListFragment;

public class OrderContainer extends BaseContainer {
    private OrderListFragment orderListFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (orderListFragment == null)
            orderListFragment = new OrderListFragment();
        replaceFragment(orderListFragment);
    }
}
