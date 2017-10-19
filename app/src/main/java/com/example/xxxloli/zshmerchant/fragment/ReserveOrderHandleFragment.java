package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.adapter.OrderListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class ReserveOrderHandleFragment extends FragOrderList {

    public ReserveOrderHandleFragment(){
        super();
    }

    public ReserveOrderHandleFragment(String lineOrderType){
        super(lineOrderType);
    }

    @Override
    protected void readInstanceState() {
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
    }

    class Adapter extends OrderListAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final OrderEntity order, final int position) {
            super.setData(view, order, position);
            ViewHolder holder = (ViewHolder) view.getTag();

        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        super.onSuccess(tag, json);
        Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
        adapter.getData().remove(tag);
        adapter.notifyDataSetChanged();
    }
}
