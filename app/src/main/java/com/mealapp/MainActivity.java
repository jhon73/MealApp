package com.mealapp;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mealapp.baseclass.BaseAppCompactActivity;
import com.mealapp.container.BaseContainer;
import com.mealapp.container.CouponContainer;
import com.mealapp.container.HomeContainer;
import com.mealapp.container.MenuItemContainer;
import com.mealapp.container.MessageContainer;
import com.mealapp.container.OrderContainer;
import com.mealapp.databinding.ActivityMainBinding;
import com.mealapp.fragment.CouponListFragment;
import com.mealapp.fragment.HomeFragment;
import com.mealapp.fragment.MenuItemAddFragment;
import com.mealapp.fragment.DeliveryRateFragment;
import com.mealapp.fragment.MenuListFragment;
import com.mealapp.fragment.MessageMainFragment;
import com.mealapp.fragment.OrderDetailsFragment;
import com.mealapp.fragment.OrderListFragment;
import com.mealapp.fragment.ProfileFragment;
import com.mealapp.fragment.ReviewMainFragment;
import com.mealapp.helper.APIService;
import com.mealapp.helper.APIUtils;
import com.mealapp.helper.ConstantData;
import com.mealapp.helper.SharedPref;
import com.mealapp.retrofit.model.UserDetail;
import com.mealapp.utility.Utility;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseAppCompactActivity implements View.OnClickListener {

    private ActivityMainBinding mBinding;
    private ActionBarDrawerToggle drawerToggle;
    private String screen;
    private static String currentFragment;
    private ImageView profileImg;
    private TextView txtUserName;
    private SharedPref session;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);

        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();


        session = new SharedPref(MainActivity.this);
//        session.checkLogin();

        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_);

        View view = mBinding.navView.getHeaderView(0);
        mBinding.navView.getMenu().getItem(0).setChecked(false);
        profileImg = (ImageView) view.findViewById(R.id.imageView_user);
        txtUserName = (TextView) view.findViewById(R.id.txt_user_name);

        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });
        drawerToggle = setupDrawerToggle();
        mBinding.drawerLayout.addDrawerListener(drawerToggle);
        mBinding.navView.setItemIconTintList(null);
        getUserData();
        profileImg.setOnClickListener(this);

        HomeFragment homeFragment = new HomeFragment();
        currentFragment = HomeContainer.class.getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.addToBackStack(homeFragment.getClass().getSimpleName());
        ft.replace(R.id.ll_frame, new HomeContainer(), HomeContainer.class.getSimpleName());
        ft.commit();
    }

    private void getUserData() {
        apiService.getUserDetail(sharedPreferences.getString(ConstantData.TOKEN, ""))
                .enqueue(new Callback<UserDetail>() {
                    @Override
                    public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    Utility.loadImage(response.body().getImageUrl(), profileImg, R.drawable.placeholder);
                                    txtUserName.setText(response.body().getFirstName());
                                    id = response.body().getId();
                                }
                            } else if (response.code() == 401) {
                                Utility.toast(MainActivity.this, "Unauthorized");
                            } else if (response.code() == 403) {
                                Utility.toast(MainActivity.this, "Forbidden");
                            } else if (response.code() == 404) {
                                Utility.toast(MainActivity.this, "Not Found");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetail> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            boolean isPop = false;
//            if (currentFragment.equals(HomeContainer.class.getSimpleName())) {
//                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(HomeContainer.class.getSimpleName())).popFragment();
//            }else if (currentFragment.equals(MenuItemContainer.class.getSimpleName())) {
//                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(MenuItemContainer.class.getSimpleName())).popFragment();
//            }else if (currentFragment.equals(OrderContainer.class.getSimpleName())){
//                isPop = ((BaseContainer)getSupportFragmentManager().findFragmentByTag(OrderContainer.class.getSimpleName())).popFragment();
//            }else if (currentFragment.equals(CouponContainer.class.getSimpleName())){
//                isPop = ((BaseContainer)getSupportFragmentManager().findFragmentByTag(CouponContainer.class.getSimpleName())).popFragment();
//            }else if (currentFragment.equals(MessageContainer.class.getSimpleName())){
//                isPop = ((BaseContainer)getSupportFragmentManager().findFragmentByTag(MessageContainer.class.getSimpleName())).popFragment();
//            }


//            } else if (currentFragment.equals(OrderHistoryContainer.class.getSimpleName())) {
//                isPop = ((BaseContainer) getSupportFragmentManager().findFragmentByTag(OrderHistoryContainer.class.getSimpleName())).popFragment();
//            }
            if (!isPop) {
            super.onBackPressed();
            }
        }
    }

    public void setActionBarTitle(String title) {
        mBinding.txtToolbarTitle.setText(title);
    }

    public void setIconNavi() {
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_menu_);
        invalidateOptionsMenu();
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    public void setIconBack() {
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setMenuActionBar(String screen) {
        this.screen = screen;
        invalidateOptionsMenu();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mBinding.drawerLayout, mBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment = null;

        if (id == R.id.dashboard) {
//            currentFragment = HomeContainer.class.getSimpleName();
//            fragment = new HomeContainer();
            HomeFragment nextFrag = new HomeFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
//            Toast.makeText(this, "dashboard", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.products) {

//            currentFragment = MenuItemContainer.class.getSimpleName();
//            fragment = new MenuItemContainer();
            MenuListFragment nextFrag = new MenuListFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "products", Toast.LENGTH_SHORT).show();

        }
//        else if (id == R.id.favourite) {
//            ReviewMainFragment nextFrag = new ReviewMainFragment();
//            this.getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
//                    .addToBackStack(null)
//                    .commit();
//            Toast.makeText(this, "favourite", Toast.LENGTH_SHORT).show();
//        }
        else if (id == R.id.order_detail) {
//            currentFragment = OrderContainer.class.getSimpleName();
//            fragment = new OrderContainer();

            OrderDetailsFragment nextFrag = new OrderDetailsFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "order details", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.delivery_status) {
            DeliveryRateFragment nextFrag = new DeliveryRateFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "delivery status", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.messages) {

//            currentFragment = MessageContainer.class.getSimpleName();
//            fragment = new MessageContainer();
            MessageMainFragment nextFrag= new MessageMainFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag,"findThisFragment")
                    .addToBackStack(null)
                    .commit();
            Toast.makeText(this, "messages", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.coupon) {
//            currentFragment = CouponContainer.class.getSimpleName();
//            fragment = new CouponContainer();
            CouponListFragment nextFrag = new CouponListFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
//            Toast.makeText(this, "coupon", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.sign_out) {
            session.logoutUser();
            finish();
            Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ll_frame, fragment, fragment.getClass().getSimpleName());
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_menu_item:
                MenuItemAddFragment nextFrag = new MenuItemAddFragment();
                this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_frame, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (screen != null && screen.equals("Add_Menu")){
            menu.findItem(R.id.action_add_menu_item).setVisible(true);
        }else {
            menu.findItem(R.id.action_add_menu_item).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v == profileImg){

            ProfileFragment searchFragment = new ProfileFragment();
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ll_frame, searchFragment, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }
}
