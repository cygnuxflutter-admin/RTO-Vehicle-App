package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;


public class Task_PagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;

    public Task_PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return this.fragments.get(i).toString().toString();
    }
}
