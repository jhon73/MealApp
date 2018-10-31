package com.mealapp.fragment;

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
import com.mealapp.adapter.AddMenuAdapter;
import com.mealapp.container.BaseContainer;
import com.mealapp.databinding.FragmentAddMenuListBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.MenuItemResp;
import com.mealapp.utility.Utility;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MenuListFragment extends Fragment implements AddMenuAdapter.AddMenuListInterface {
    private FragmentAddMenuListBinding mBinding;
    private LinearLayoutManager linearLayoutManager;
    private AddMenuAdapter mAdapter;

    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<MenuItemResp> arrayListMenuItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Add Menu Item");
//        ((MainActivity)getActivity()).setIconBack();
        ((MainActivity)getActivity()).setMenuActionBar("Add_Menu");
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        arrayListMenuItem = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_menu_list, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ipokArrayList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.frRecycler.setLayoutManager(linearLayoutManager);
        mAdapter = new AddMenuAdapter(getActivity(), arrayListMenuItem,this);
        mBinding.frRecycler.setAdapter(mAdapter);
        getMenuListApi();
    }

    private void getMenuListApi() {
        apiService.getAllMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<List<MenuItemResp>>() {
                    @Override
                    public void onResponse(Call<List<MenuItemResp>> call, Response<List<MenuItemResp>> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    if (arrayListMenuItem != null)
                                        arrayListMenuItem.clear();
                                    arrayListMenuItem.addAll(response.body());
                                    mAdapter.notifyDataSetChanged();
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
                    public void onFailure(Call<List<MenuItemResp>> call, Throwable t) {
                        if (getActivity() != null)
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).setActionBarTitle("Home");
        ((MainActivity)getActivity()).setIconNavi();
    }

    @Override
    public void onAddMenuListClick(int pos) {
        ((BaseContainer)getParentFragment()).addFragment(new MenuItemDetailFragment(arrayListMenuItem.get(pos)));
//        MenuItemDetailFragment nextFrag= new MenuItemDetailFragment();
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.ll_frame, nextFrag,"findThisFragment")
//                .addToBackStack(null)
//                .commit();
    }

    @Override
    public void onDeleteMEnuItem(int pos) {
        apiService.DeleteMenuItem(sharedPreferences.getString(ConstantData.TOKEN, ""),arrayListMenuItem.get(pos).getId())
                .enqueue(new Callback<MenuItemResp>() {
                    @Override
                    public void onResponse(Call<MenuItemResp> call, Response<MenuItemResp> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    getMenuListApi();
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
