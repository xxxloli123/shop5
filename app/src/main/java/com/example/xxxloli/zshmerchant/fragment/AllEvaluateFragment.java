package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.adapter.EvaluateListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class AllEvaluateFragment extends FragEvaluateList {

    public AllEvaluateFragment(){
        super();
    }

    public AllEvaluateFragment(String rob, String status){
        super(rob,status);
    }

    @Override
    protected void readInstanceState() {
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
    }

    class Adapter extends EvaluateListAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final Message message, final int position) {
            super.setData(view, message, position);
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"丢了个雷姆",Toast.LENGTH_SHORT).show();
                }
            });
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
