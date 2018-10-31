package com.mealapp;

import android.content.Intent;
        import android.databinding.DataBindingUtil;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Toast;

        import com.mealapp.databinding.ActivityRegisterBinding;
        import com.mealapp.helper.APIService;
        import com.mealapp.helper.APIUtils;
        import com.mealapp.retrofit.model.TokenResp;
        import com.mealapp.utility.Utility;

        import java.util.Arrays;
        import java.util.HashMap;
        import java.util.Map;

        import butterknife.ButterKnife;
        import butterknife.OnClick;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding mBinding;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        ButterKnife.bind(this);
        apiService = APIUtils.getAPIService();
    }

    @OnClick(R.id.btn_register)
    public void onClickRegister(){

//        String authotities = "ROLE_VENDOR";
        if (mBinding.txtActRegisterEmail.getText().length() ==0 ){
            mBinding.txtActRegisterEmail.setError("Enter Email");
        }else if (mBinding.txtActRegisterAddress.getText().length() ==0 ){
            mBinding.txtActRegisterAddress.setError("Enter Address");
        }else if (mBinding.txtActRegisterEmail.getText().length() == 0 ){
            mBinding.txtActRegisterEmail.setError("Enter Email");
        }else if (mBinding.txtActRegisterPhone.getText().length() ==0 ){
            mBinding.txtActRegisterPhone.setError("Enter Phone number");
        }else if (mBinding.txtActRegisterPassword.getText().length() ==0 ){
            mBinding.txtActRegisterPassword.setError("Enter password");
        }else if (mBinding.txtActRegisterPassword.getText().length() < 6){
            mBinding.txtActRegisterPassword.setError("Password length minimum six character");
        }else {
            final Map<String, Object> requestBodyMap = new HashMap<>();
            requestBodyMap.put("activated",true);
            requestBodyMap.put("authorities", new String[]{"ROLE_VENDOR"});
            requestBodyMap.put("email", mBinding.txtActRegisterEmail.getText().toString().trim());
            requestBodyMap.put("firstName", mBinding.txtActRegisterFullname.getText().toString().trim());
            requestBodyMap.put("login", mBinding.txtActRegisterEmail.getText().toString().trim());
            requestBodyMap.put("password", mBinding.txtActRegisterPassword.getText().toString().trim());
            apiService.getSignUpData(requestBodyMap)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 201) {
                                    Utility.navigationIntent(RegisterActivity.this, LoginActivity.class);
                                } else if (response.code() == 401) {
                                    Utility.toast(RegisterActivity.this, "Unauthorized");
                                } else if (response.code() == 403) {
                                    Utility.toast(RegisterActivity.this, "Forbidden");
                                } else if (response.code() == 404) {
                                    Utility.toast(RegisterActivity.this, "Not Found");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @OnClick(R.id.txt_already_login)
    public void onClickAlreadyLogin() {
        Utility.navigationIntent(RegisterActivity.this,LoginActivity.class);
        finish();
    }
}
