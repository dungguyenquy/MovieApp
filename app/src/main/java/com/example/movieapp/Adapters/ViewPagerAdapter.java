package com.example.movieapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.movieapp.Fragment.FragmentBanners;
import com.example.movieapp.Fragment.FragmentProfile;
import com.example.movieapp.Fragment.FragmentTopMovie;
import com.example.movieapp.Fragment.FragmentUpcomming;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentBanners();
            case 1: return new FragmentTopMovie();
            case 2: return new FragmentUpcomming();
            case 3: return new FragmentProfile();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
