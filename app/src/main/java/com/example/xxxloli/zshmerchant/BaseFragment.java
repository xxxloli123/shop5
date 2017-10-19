package com.example.xxxloli.zshmerchant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/1/19 .
 */
public class BaseFragment extends Fragment {
    public BaseFragment() {
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void putArg(Bundle bundle) {
        getArguments().putBundle("args", bundle);
    }

    @Override
    public Context getContext() {
        if (getView() != null) return getView().getContext();
        if (super.getContext() != null) return super.getContext();
        if (getActivity() != null) return getActivity();
        return null;
    }
}
