package com.mealapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mealapp.R;
import com.mealapp.databinding.RowOrderListBinding;
import com.mealapp.fragment.OrderListFragment;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private RowOrderListBinding mBinding;
    private Context context;
    private OrderListInterface orderListInterface;

    public OrderListAdapter(FragmentActivity activity, OrderListInterface orderListInterface) {
        this.context = activity;
        this.orderListInterface = orderListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_order_list, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.llStatus.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowOrderListBinding mBinding;

        public ViewHolder(RowOrderListBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.llStatus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.llStatus) {
                if (orderListInterface != null){
                    int pos = (int) v.getTag();
                    orderListInterface.onClickOrderListOption(pos);
                }
            }
        }
    }
    public interface OrderListInterface{
        void onClickOrderListOption(int pos);
    }
}
