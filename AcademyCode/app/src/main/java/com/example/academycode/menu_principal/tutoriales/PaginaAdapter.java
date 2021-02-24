package com.example.academycode.menu_principal.tutoriales;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class PaginaAdapter extends FragmentPagerAdapter {

    int numoftabs;
    Context mCtx;

    public PaginaAdapter(@NonNull FragmentManager fm, int behavior, Context mCtx) {
        super(fm, behavior);
        this.numoftabs = behavior;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentoVideo("java");
            case 1:
                return new FragmentoVideo("python");
            case 2:
                return new FragmentoVideo("cplus");
            case 3:
                return new FragmentoVideo("javascript");
            case 4:
                return new FragmentoVideo("php");
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numoftabs;
    }


}
