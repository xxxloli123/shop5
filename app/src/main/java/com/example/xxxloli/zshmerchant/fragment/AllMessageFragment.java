package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.MessageContentActivity;
import com.example.xxxloli.zshmerchant.adapter.MessageListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class AllMessageFragment extends FragMessageList {

    public AllMessageFragment(){
        super();
    }

    public AllMessageFragment(String rob, String status){
        super(rob,status);
    }

    @Override
    protected void readInstanceState() {
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
    }

    class Adapter extends MessageListAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final Message message, final int position) {
            super.setData(view, message, position);
            ViewHolder holder = (ViewHolder) view.getTag();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), MessageContentActivity.class);
                    intent.putExtra("title", "人的传统风格v也不会叫你门口，来则是相当脆弱反光" +
                            "板预后年纪门口，色系的脆弱头发v更便于回娘家密恐，熟悉的人吃饭刚回家你" +
                            "肯定乳房TV公布于河南寂寞空虚的风格v海军男科");
                    intent.putExtra("content", message.getContent());
                    startActivity(intent);
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
