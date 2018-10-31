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
import com.mealapp.databinding.RowAddMenuListBinding;
import com.mealapp.retrofit.model.MenuItemResp;
import com.mealapp.utility.Utility;

import java.util.ArrayList;

public class AddMenuAdapter extends RecyclerView.Adapter<AddMenuAdapter.ViewHolder> {
    private Context context;
    private RowAddMenuListBinding mBinding;
    private ArrayList<MenuItemResp> arrayListMenuItem;
    private AddMenuListInterface addMenuListInterface;

    public AddMenuAdapter(FragmentActivity activity, ArrayList<MenuItemResp> arrayListMenuItem, AddMenuListInterface addMenuListInterface) {
        this.context = activity;
        this.arrayListMenuItem = arrayListMenuItem;
        this.addMenuListInterface = addMenuListInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_add_menu_list, parent, false);
        return new ViewHolder(mBinding, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Utility.loadImage(arrayListMenuItem.get(position).getImageurl(),holder.mBinding.imgFrgMenuList,R.drawable.placeholder);
        holder.mBinding.txtFoodNameMenuList.setText(arrayListMenuItem.get(position).getItemname());
        holder.mBinding.txtFoodPriceMenuList.setText(""+arrayListMenuItem.get(position).getPrice());
        holder.mBinding.llMenuItemList.setTag(position);
        holder.mBinding.btnDeleteMenuList.setTag(position);
    }

    @Override
    public int getItemCount() {
        return arrayListMenuItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RowAddMenuListBinding mBinding;

        public ViewHolder(RowAddMenuListBinding mBinding, View itemView) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
            this.mBinding.llMenuItemList.setOnClickListener(this);
            this.mBinding.btnDeleteMenuList.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mBinding.btnDeleteMenuList) {
                if (addMenuListInterface != null) {
                    int pos = (int) v.getTag();
                    addMenuListInterface.onDeleteMEnuItem(pos);
                }
            }else if (v == mBinding.llMenuItemList) {
                if (addMenuListInterface != null) {
                    int pos = (int) v.getTag();
                    addMenuListInterface.onAddMenuListClick(pos);
                }

            }
        }
    }
    public interface AddMenuListInterface{
        void onAddMenuListClick(int pos);
        void onDeleteMEnuItem(int pos);
    }
}
