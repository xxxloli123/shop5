package com.example.xxxloli.zshmerchant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xxxloli.zshmerchant.Activity.MJActivity;
import com.example.xxxloli.zshmerchant.Activity.OrderingEvaluateActivity;
import com.example.xxxloli.zshmerchant.R;
import com.sgrape.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xxxloli on 2017/10/17.
 */

public class NewBuildFragmet extends BaseFragment {
    @BindView(R.id.MJ)
    Button MJ;
    @BindView(R.id.CWHY)
    Button CWHY;
    @BindView(R.id.FXGZ)
    Button FXGZ;
    @BindView(R.id.MZ)
    Button MZ;
    @BindView(R.id.MMPSF)
    Button MMPSF;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_build;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.MJ, R.id.CWHY, R.id.FXGZ, R.id.MZ, R.id.MMPSF})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.MJ:
                startActivity(new Intent(getActivity(), MJActivity.class));
                break;
            case R.id.CWHY:
                startActivity(new Intent(getActivity(), MJActivity.class));
                break;
            case R.id.FXGZ:
                break;
            case R.id.MZ:
                break;
            case R.id.MMPSF:
                break;
        }
    }
}
