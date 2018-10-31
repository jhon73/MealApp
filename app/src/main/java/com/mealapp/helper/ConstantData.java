package com.mealapp.helper;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by ADMIN on 28-Nov-17.
 */

public class ConstantData {

//    private SharedPreferences sharedPreferences;
//    private SharedPreferences.Editor editor;
//
//    public ConstantData(Context context) {
//        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//    }
//
//    public void setData(Keys keys, String value) {
//        editor.putString(keys.name(), value);
//        editor.commit();
//    }
//
//    public void setData(Keys keys, boolean value) {
//        editor.putBoolean(keys.name(), value);
//        editor.commit();
//    }
//
//    public String getData(Keys keys) {
//        return sharedPreferences.getString(keys.name(), "");
//    }
//
//    public boolean getData(Keys keys, boolean flag) {
//        return sharedPreferences.getBoolean(keys.name(), flag);
//    }
//
//    public enum Keys {
//        ISLOGIN, IMAGEURL, USERDATA, TOKEN, PROFILE, NOTIFICATIONDATA
//    }

    public static final String PREF_NAME = "user_preference";
    public static final String GUEST_PREFERENCE_NAME = "guest_preference";

    public static final String TOKEN = "token_vendor";
    public static final String LOCATION_ID = "id";


    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String U_ROLEID = "user_rollid";
    public static final String USER_IMAGE = "profile_image";
    public static final String USER_PHONE= "user_phone";

    public static final String U_FBID = "user_fbid";
    public static final String U_GPID = "user_gpid";

    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String LANGUAGE_SELECTION = "lang_selection";
    public static final String USER_TYPE = "user_type";

    public static final String GUEST_USER = "0";
    public static final String GUEST_NAME = "guest_name";
    public static final String GUEST_PASSWORD = "guest_password";

    public static final String FIRST_NAME = "firstname";
    public static final String LAST_NAME = "lastname";
    public static final String ADDRESS_LINE1 = "address1";
    public static final String ADDRESS_LINE2 = "address2";
    public static final String BUILDING_NUMBER = "building_flatno";
    public static final String CITY = "community";
    public static final String PINCODE = "city";
    public static final String PHONE_NUMBER = "phone";
    public static final String DELIEVARY_TIME = "delivery_time";
    public static final String DATE = "date";
    public static final String COUPON_CODE = "code";
    public static final String ADDRESS_ID = "address_id";
    public static final String LANGUAGE = "language";

    public static final String PAYMENT_TYPE = "payment_type";
    public static final String CARD_TITLE = "card_title";
    public static final String CARD_NUMBER = "card_number";
    public static final String CARD_PLACE_HOLDER_NAME = "card_place_holder_name";
    public static final String CARD_EXP_YEAR = "card_exp_year";
    public static final String CARD_EXP_MONTH = "card_exp_month";
    public static final String CARD_CVV = "card_cvv";

    public static final String CART_TOTAL  = "total";

    public static final boolean ONCE_SELECTION = false;

    public static final String STRIPE_TOKEN = "stripeToken";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String PRODUCT_DETAIL = "productDetail";
    public static final String AMOUNT = "amount";
    public static final String ORDER_ID = "order_id";

    public static final String CARD_TYPE = "card_type";
}
//