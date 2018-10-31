package com.mealapp.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.container.BaseContainer;
import com.mealapp.databinding.FragmentAddMenuItemDetailBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.MenuItemResp;
import com.mealapp.utility.Utility;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class MenuItemDetailFragment extends Fragment {
    private FragmentAddMenuItemDetailBinding mBinding;
    private MenuItemResp menuItemResp;

    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String update;

    public MenuItemDetailFragment(MenuItemResp menuItemResp , String update){
        this.menuItemResp = menuItemResp;
        this.update = update;
    }

    public MenuItemDetailFragment(MenuItemResp menuItemResp) {
        this.menuItemResp = menuItemResp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (menuItemResp != null) {
            ((MainActivity) getActivity()).setActionBarTitle(""+menuItemResp.getItemname());
        }
        ((MainActivity) getActivity()).setIconBack();
        ((MainActivity) getActivity()).setMenuActionBar("remove");
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_menu_item_detail, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utility.loadImage(menuItemResp.getImageurl(),mBinding.imgItemDetail,R.drawable.placeholder);
        mBinding.txtItemNameDetail.setText(menuItemResp.getItemname());
        mBinding.txtDescriptionItemDetail.setText(menuItemResp.getDescription());
        mBinding.txtItemPriceDetail.setText(""+menuItemResp.getPrice());
    }

    @OnClick(R.id.btn_delete_menu_item)
    public void onClickDeleteMenuItem(){
        Toast.makeText(getActivity(), "Delete", Toast.LENGTH_SHORT).show();

        apiService.DeleteMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""),menuItemResp.getId())
                .enqueue(new Callback<MenuItemResp>() {
                    @Override
                    public void onResponse(Call<MenuItemResp> call, Response<MenuItemResp> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                }
                            } else if (response.code() == 401) {
                                Utility.toast(getActivity(), "Unauthorized");
                            } else if (response.code() == 403) {
                                Utility.toast(getActivity(), "Forbidden");
                            } else if (response.code() == 404) {
                                Utility.toast(getActivity(), "Not Found");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MenuItemResp> call, Throwable t) {
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.btn_edit_menu_item)
    public void onClickEditMenuItem(){
        Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
        ((BaseContainer)getParentFragment()).addFragment(new MenuItemAddFragment(menuItemResp,"Update"));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Add Menu Item");
        ((MainActivity) getActivity()).setMenuActionBar("Add_Menu");
        ((MainActivity)getActivity()).setIconNavi();
    }
}
