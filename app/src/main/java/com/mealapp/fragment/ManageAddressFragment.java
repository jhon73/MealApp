package com.mealapp.fragment;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mealapp.LoginActivity;
import com.mealapp.MainActivity;
import com.mealapp.R;
import com.mealapp.databinding.FragmentManageAddressBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.LocationResp;
import com.mealapp.retrofit.model.TokenResp;
import com.mealapp.utility.Utility;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ManageAddressFragment extends Fragment {
    private FragmentManageAddressBinding mBinding;
    private int id;
    private String city, postalCode, stateProvinace, streetAddress;

    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).setActionBarTitle("My Address");
        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id = getArguments().getInt("id");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_address, container, false);
        ButterKnife.bind(this, mBinding.getRoot());
        getLocation();
        return mBinding.getRoot();
    }

    private void getLocation() {
        mBinding.rotateloading.start();
        if (sharedPreferences.getInt(ConstantData.LOCATION_ID, 0) != 0 && sharedPreferences.getInt(ConstantData.LOCATION_ID, 0) > 0) {
            apiService.getLocation(sharedPreferences.getString(ConstantData.TOKEN, ""), sharedPreferences.getInt(ConstantData.LOCATION_ID, 0))
                    .enqueue(new Callback<LocationResp>() {
                        @Override
                        public void onResponse(Call<LocationResp> call, Response<LocationResp> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    if (response.body().getId() != null) {
                                        mBinding.rotateloading.stop();
                                        mBinding.edtHouseNo.setText(response.body().getStreetAddress());
                                        mBinding.edtLandmark.setText(response.body().getCity());
                                        mBinding.placesAutocomplete.setText(response.body().getStreetAddress() + ", " +
                                                response.body().getCity() + ", " +
                                                response.body().getStateProvince() + ", " +
                                                response.body().getPostalCode());
                                        mBinding.btnSaveAddress.setText("UPDATE");
                                        mBinding.btnDeleteAddress.setVisibility(View.VISIBLE);
                                    }
                                } else if (response.code() == 401) {
                                    mBinding.rotateloading.stop();
                                    Utility.toast(getActivity(), "Unauthorized");
                                } else if (response.code() == 403) {
                                    mBinding.rotateloading.stop();
                                    Utility.toast(getActivity(), "Forbidden");
                                } else if (response.code() == 404) {
                                    mBinding.rotateloading.stop();
                                    Utility.toast(getActivity(), "Not Found");
                                }
                            } else {
                                mBinding.rotateloading.stop();
                            }
                        }

                        @Override
                        public void onFailure(Call<LocationResp> call, Throwable t) {
                            mBinding.rotateloading.stop();
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {

        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.placesAutocomplete.setHistoryManager(null);
        mBinding.placesAutocomplete.setOnPlaceSelectedListener(new OnPlaceSelectedListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mBinding.placesAutocomplete.getDetailsFor(place, new DetailsCallback() {
                    @Override
                    public void onSuccess(PlaceDetails details) {
                        streetAddress = details.name;
                        mBinding.edtLandmark.setText(details.name);
                        for (AddressComponent component : details.address_components) {
                            for (AddressComponentType type : component.types) {
                                switch (type) {
                                    case STREET_NUMBER:
                                        mBinding.edtHouseNo.setText(component.short_name);
                                        break;
                                    case ROUTE:
                                        break;
                                    case NEIGHBORHOOD:

                                        break;
                                    case SUBLOCALITY_LEVEL_1:
                                        mBinding.edtHouseNo.setText(component.short_name);
                                        break;
                                    case SUBLOCALITY:
                                        mBinding.edtHouseNo.setText(component.short_name);
                                        break;
                                    case LOCALITY:
                                        city = component.long_name;
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_1:
                                        stateProvinace = component.short_name;
                                        break;
                                    case ADMINISTRATIVE_AREA_LEVEL_2:
                                        break;
                                    case COUNTRY:
                                        break;
                                    case POSTAL_CODE:
                                        postalCode = component.long_name;
                                        break;
                                    case POLITICAL:
                                        break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("TAG", "onFailure: " + throwable.getMessage());
                    }
                });
            }
        });
    }

    @OnClick(R.id.btn_save_address)
    public void onClickSaveAddress() {
        if (mBinding.placesAutocomplete.getText().length() == 0) {
            mBinding.placesAutocomplete.setError("Enter location");
        }else if (mBinding.edtHouseNo.getText().length() == 0){
            mBinding.edtHouseNo.setError("Enter House Number");
        }else if (mBinding.edtLandmark.getText().length() == 0){
            mBinding.edtLandmark.setError("Enter Landmark");
        }else {
            if (mBinding.btnSaveAddress.getText().equals("SAVE")) {
                final Map<String, Object> requestBodyMap = new HashMap<>();
                requestBodyMap.put("resturentid", id);
                requestBodyMap.put("city", city);
                requestBodyMap.put("postalCode", postalCode);
                requestBodyMap.put("stateProvince", stateProvinace);
                requestBodyMap.put("streetAddress", streetAddress);
                mBinding.rotateloading.start();
                apiService.createLocations(sharedPreferences.getString(ConstantData.TOKEN, ""), requestBodyMap)
                        .enqueue(new Callback<LocationResp>() {
                            @Override
                            public void onResponse(Call<LocationResp> call, Response<LocationResp> response) {
//                            progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    if (response.code() == 201) {
                                        mBinding.rotateloading.stop();
                                        editor.putInt(ConstantData.LOCATION_ID, response.body().getId());
                                        editor.commit();
                                        mBinding.btnSaveAddress.setText("UPDATE");
                                        mBinding.btnDeleteAddress.setVisibility(View.VISIBLE);
                                        Toast.makeText(getActivity(), "Location created", Toast.LENGTH_SHORT).show();
                                    } else if (response.code() == 401) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Unauthorized");
                                    } else if (response.code() == 403) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Forbidden");
                                    } else if (response.code() == 404) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Not Found");
                                    }
                                } else {
                                    mBinding.rotateloading.stop();
                                }
                            }

                            @Override
                            public void onFailure(Call<LocationResp> call, Throwable t) {
                                mBinding.rotateloading.stop();
                                Toast.makeText(getActivity(), "Fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else if (mBinding.btnSaveAddress.getText().equals("UPDATE")) {
                final Map<String, Object> requestBodyMap = new HashMap<>();
                requestBodyMap.put("id", sharedPreferences.getInt(ConstantData.LOCATION_ID, 0));
                requestBodyMap.put("city", city);
                requestBodyMap.put("postalCode", postalCode);
                requestBodyMap.put("stateProvince", stateProvinace);
                requestBodyMap.put("streetAddress", streetAddress);
                mBinding.rotateloading.start();
                apiService.updateLocations(sharedPreferences.getString(ConstantData.TOKEN, ""), requestBodyMap)
                        .enqueue(new Callback<LocationResp>() {
                            @Override
                            public void onResponse(Call<LocationResp> call, Response<LocationResp> response) {
                                if (response.isSuccessful()) {
                                    if (response.code() == 200) {
                                        mBinding.rotateloading.stop();
                                        editor.putInt(ConstantData.LOCATION_ID, response.body().getId());
                                        editor.commit();
                                        Toast.makeText(getActivity(), "Location Updated", Toast.LENGTH_SHORT).show();
                                    } else if (response.code() == 400) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Bad Request");
                                    } else if (response.code() == 401) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Unauthorized");
                                    } else if (response.code() == 403) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Forbidden");
                                    } else if (response.code() == 404) {
                                        mBinding.rotateloading.stop();
                                        Utility.toast(getActivity(), "Not Found");
                                    }
                                } else {
                                    mBinding.rotateloading.stop();
                                }
                            }

                            @Override
                            public void onFailure(Call<LocationResp> call, Throwable t) {
                                mBinding.rotateloading.stop();
                                Toast.makeText(getActivity(), "Fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }


    @OnClick(R.id.btn_delete_address)
    public void onClickDeleteAddress() {
        mBinding.rotateloading.start();
        apiService.deleteLocation(sharedPreferences.getString(ConstantData.TOKEN, ""), sharedPreferences.getInt(ConstantData.LOCATION_ID, 0))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                mBinding.rotateloading.stop();
                                mBinding.edtHouseNo.setText("");
                                mBinding.edtLandmark.setText("");
                                mBinding.placesAutocomplete.setText("");
                                mBinding.btnSaveAddress.setText("SAVE");
                                mBinding.btnDeleteAddress.setVisibility(View.GONE);
                            }
                        } else if (response.code() == 401) {
                            mBinding.rotateloading.stop();
                            Utility.toast(getActivity(), "Unauthorized");
                        } else if (response.code() == 403) {
                            mBinding.rotateloading.stop();
                            Utility.toast(getActivity(), "Forbidden");
                        } else if (response.code() == 404) {
                            mBinding.rotateloading.stop();
                            Utility.toast(getActivity(), "Not Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        mBinding.rotateloading.stop();
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utility.hideKeyboared(getActivity());
    }
}
