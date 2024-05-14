package com.example.movieapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.movieapp.Adapters.ViewPagerAdapter;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        navigationView=findViewById(R.id.bottom_Banners);
        viewPager=findViewById(R.id.bannerViewpaper);
        fab=findViewById(R.id.fabBanners);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:navigationView.getMenu().findItem(R.id.mBanners).setChecked(true);
                        break;
                    case 1:navigationView.getMenu().findItem(R.id.mTopMovies).setChecked(true);
                        break;
                    case 2:navigationView.getMenu().findItem(R.id.mUpcomming).setChecked(true);
                        break;
                    case 3:navigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mBanners:viewPager.setCurrentItem(0);
                        break;
                    case R.id.mTopMovies:viewPager.setCurrentItem(1);
                        break;
                    case R.id.mUpcomming:viewPager.setCurrentItem(2);
                        break;
                    case R.id.mProfile:viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
    }
}