package com.jarry.weibo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jarry.weibo.R;

/**
 * Created by Jarry 2018/5/2.
 *
 *
 * fragment of find something
 */
public class FindFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find,container,false);
        return rootView;
    }
}
