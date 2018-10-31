package com.mealapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mealapp.databinding.ActivityLoginBinding;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.retrofit.model.TokenResp;
import com.mealapp.utility.Utility;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding mBinding;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        if (mBinding.txtActLoginEmail.getText().length() == 0) {
            mBinding.txtActLoginEmail.setError("Enter Emial");
        } else if (mBinding.txtActLoginPassword.getText().length() == 0) {
            mBinding.txtActLoginPassword.setError("Enter Password");
        } else {
            final Map<String, String> requestBodyMap = new HashMap<>();
            requestBodyMap.put("username", mBinding.txtActLoginEmail.getText().toString().trim());
            requestBodyMap.put("password", mBinding.txtActLoginPassword.getText().toString().trim());
            apiService.getLoginData(requestBodyMap)
                    .enqueue(new Callback<TokenResp>() {
                        @Override
                        public void onResponse(Call<TokenResp> call, Response<TokenResp> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    if (response.body().getIdToken() != null) {
                                        Utility.navigationIntent(LoginActivity.this, MainActivity.class);
                                        editor.putString(ConstantData.TOKEN, "Bearer "+response.body().getIdToken());
                                        editor.commit();
                                  String token  = sharedPreferences.getString(ConstantData.TOKEN, "");  //get Token data
                                    }
                                } else if (response.code() == 401) {
                                    Utility.toast(LoginActivity.this, "Unauthorized");
                                } else if (response.code() == 403) {
                                    Utility.toast(LoginActivity.this, "Forbidden");
                                } else if (response.code() == 404) {
                                    Utility.toast(LoginActivity.this, "Not Found");
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<TokenResp> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @OnClick(R.id.btn_new_user_sign_up)
    public void onClickNewUser(){
        Utility.navigationIntent(this,RegisterActivity.class);
        finish();
    }
}
