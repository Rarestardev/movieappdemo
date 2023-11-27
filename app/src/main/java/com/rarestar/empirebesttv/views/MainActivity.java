package com.rarestar.empirebesttv.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.adapter.VPAdapter;
import com.rarestar.empirebesttv.app.ScreenUtility;
import com.rarestar.empirebesttv.fragments.Category_fragment;
import com.rarestar.empirebesttv.fragments.Home_fragment;
import com.rarestar.empirebesttv.fragments.Profile_Fragment;
import com.rarestar.empirebesttv.fragments.Saved_fragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView search,imageView_menu,close_dialog,close_contactUs_dialog;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CardView telegramID,instagramID;
    public static boolean largerScreen = false;
    TabLayout tabs;
    ViewPager viewPager;
    Dialog ContactUsDialog,AboutDialog;
    TextView appName;
    String link;
    private final int[] tabIcons = {
            R.drawable.baseline_home_24,
            R.drawable.baseline_category_24,
            R.drawable.baseline_bookmark_border_24,
            R.drawable.baseline_supervised_user_circle_24
    };

    public static int screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        link = getIntent().getStringExtra("save");
        init();
        viewPagerSet();
        onclick();
    }
    private void init(){
        imageView_menu = findViewById(R.id.menu);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        search = findViewById(R.id.icon_search);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        appName = findViewById(R.id.appName);
        navigationView.bringToFront();

        ScreenUtility utility = new ScreenUtility(this);

        if (utility.getDpWidth() <= 599.0){
            screenSize = 3;
            largerScreen = false;
        }else if (utility.getDpWidth() <= 700.0){
            screenSize = 5;
            largerScreen = false;
        }else if (utility.getDpWidth() <= 800.0){
            screenSize = 6;
            largerScreen = false;
        }else if (utility.getDpWidth() <= 900.0){
            screenSize = 7;
            largerScreen = false;
        } else if (utility.getDpWidth() > 900.0){
            screenSize = 7;
            largerScreen = true;
        }
    }
    private void viewPagerSet(){
        tabs.setupWithViewPager(viewPager);
        tabs.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new Home_fragment(),"خانه");
        vpAdapter.addFragment(new Category_fragment(),"دسته بندی");
        vpAdapter.addFragment(new Saved_fragment(),"ذخیره ها");
        vpAdapter.addFragment(new Profile_Fragment(),"پروفایل");
        vpAdapter.notifyDataSetChanged();
        viewPager.setAdapter(vpAdapter);
        viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        setupTabIcons();
    }
    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
        tabs.getTabAt(3).setIcon(tabIcons[3]);
    }
    @SuppressLint("NonConstantResourceId")
    private void onclick(){
        imageView_menu.setOnClickListener(this);
        search.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_movies:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowList_Movie.class));
                    break;
                case R.id.menu_serials:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowListSerials.class));
                    break;
                case R.id.menu_animations:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowListAnimations.class));
                    break;
                case R.id.menu_trending:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowListTrending.class));
                    break;
                case R.id.menu_imdb:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowList_IMDB.class));
                    break;
                case R.id.menu_userRate:
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(getApplicationContext(),ShowListUserRate.class));
                    break;
                case R.id.menu_share:
                    drawerLayout.closeDrawers();
                    Intent intentSend = new Intent();
                    intentSend.setAction(Intent.ACTION_SEND);
                    intentSend.putExtra(Intent.EXTRA_TEXT,"Click open site to download app http://Google.com");
                    intentSend.setType("text/plain");
                    Intent share = Intent.createChooser(intentSend, null);
                    startActivity(share);
                    break;
                case R.id.menu_contactUs:
                    drawerLayout.closeDrawers();
                    showContactUsDialog();
                    break;
                case R.id.menu_aboutUs:
                    drawerLayout.closeDrawers();
                    showAboutDialog();
                    break;
                case R.id.menu_exit:
                    drawerLayout.closeDrawers();
                    finish();
                    break;
            }
            return true;
        });
    }

    private void showContactUsDialog() {
        ContactUsDialog = new Dialog(MainActivity.this);
        ContactUsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ContactUsDialog.setContentView(R.layout.menu_contact_us);
        ContactUsDialog.show();
        ContactUsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ContactUsDialog.getWindow().setGravity(Gravity.CENTER);
        close_contactUs_dialog = ContactUsDialog.findViewById(R.id.close_contactUs_dialog);
        telegramID = ContactUsDialog.findViewById(R.id.telegramID);
        instagramID = ContactUsDialog.findViewById(R.id.instagramID);
        String uri = "http://www.google.com";
        telegramID.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
        });
        instagramID.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
        });

        close_contactUs_dialog.setOnClickListener(view -> ContactUsDialog.dismiss());
    }

    private void showAboutDialog() {
        AboutDialog = new Dialog(MainActivity.this);
        AboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AboutDialog.setContentView(R.layout.menu_about_us);
        AboutDialog.show();
        AboutDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        AboutDialog.getWindow().setGravity(Gravity.CENTER);
        close_dialog = AboutDialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(view -> AboutDialog.dismiss());
    }
    @SuppressLint("RtlHardcoded")
    @Override
    public void onClick(View view) {
        if (view == imageView_menu) {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
        if (view == search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)){
            drawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}