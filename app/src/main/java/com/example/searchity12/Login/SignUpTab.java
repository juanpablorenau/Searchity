package com.example.searchity12.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.searchity12.R;

public class SignUpTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle){
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.sign_up_tab, viewGroup, false);
        return vg;
    }
}
