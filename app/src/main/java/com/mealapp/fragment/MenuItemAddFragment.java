package com.mealapp.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.adapter.CouponListAdapter;
import com.mealapp.databinding.FragmentAddMenuItemBinding;
import com.mealapp.databinding.FragmentCouponListBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.MenuItemResp;
import com.mealapp.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class MenuItemAddFragment extends Fragment {
    private FragmentAddMenuItemBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private MenuItemResp menuItemResp;

    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public MenuItemAddFragment(){}

    public MenuItemAddFragment(MenuItemResp menuItemResp, String update) {
        this.menuItemResp = menuItemResp;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("Add Menu Item");
        ((MainActivity) getActivity()).setIconBack();
        ((MainActivity) getActivity()).setMenuActionBar("remove");
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
//        arrayListMenuItem = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_menu_item, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (menuItemResp != null) {
            mBinding.btnAddItem.setText("Update Item");
//            Utility.loadImage(menuItemResp.getImageurl(),mBinding.);
            mBinding.edtMenuItemName.setText(menuItemResp.getItemname());
            mBinding.edtMenuItemDescription.setText(menuItemResp.getDescription());
            mBinding.edtMenuItemPrice.setText(""+menuItemResp.getPrice());
        }
    }

    @OnClick(R.id.btn_add_item)
    public void onClickMenuItemAdd() {
        if (mBinding.edtMenuItemName.getText().length() == 0) {
            mBinding.edtMenuItemName.setError("Enter Item name");
        } else if (mBinding.edtMenuItemDescription.getText().length() == 0) {
            mBinding.edtMenuItemDescription.setError("Enter Item Descriptin");
        } else if (mBinding.edtMenuItemPrice.getText().length() == 0) {
            mBinding.edtMenuItemPrice.setError("Enter Item Price");
        } else {
            final Map<String, String> requestBodyMap = new HashMap<>();
            requestBodyMap.put("itemname", mBinding.edtMenuItemName.getText().toString().trim());
            requestBodyMap.put("price", mBinding.edtMenuItemPrice.getText().toString().trim());
            requestBodyMap.put("description", mBinding.edtMenuItemDescription.getText().toString().trim());

            if (menuItemResp != null) {    //  UPDATE MENU ITEM
                requestBodyMap.put("id",""+menuItemResp.getId());
                apiService.UpdateMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""),requestBodyMap)
                        .enqueue(new Callback<MenuItemResp>() {
                            @Override
                            public void onResponse(Call<MenuItemResp> call, Response<MenuItemResp> response) {
                                if (response.isSuccessful()) {
                                    if (response.code() == 200) {
                                        if (response.body() != null) {
                                            Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                            getActivity().onBackPressed();
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
            } else {
                //ADD MENU ITEM
                apiService.AddMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""),requestBodyMap)
                        .enqueue(new Callback<MenuItemResp>() {
                            @Override
                            public void onResponse(Call<MenuItemResp> call, Response<MenuItemResp> response) {
                                if (response.isSuccessful()) {
                                    if (response.code() == 201) {
                                        if (response.body() != null && response.body().getId() != null) {
                                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                            getActivity().onBackPressed();
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Add Menu Item");
//        ((MainActivity) getActivity()).setMenuActionBar("Add_Menu");
        ((MainActivity)getActivity()).setIconNavi();
    }
}
