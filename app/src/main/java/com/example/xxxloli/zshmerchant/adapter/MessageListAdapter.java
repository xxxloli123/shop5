package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Message;
import com.sgrape.adapter.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class MessageListAdapter extends BaseAdapter<Message> {

    public MessageListAdapter(Context context) {

        super(context, new ArrayList<Message>());
    }

    private boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    @Override
    protected void setData(final View view,  Message message, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(message.getContent());
    }


    @Override
    protected VH newViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_message;
    }

    public static class ViewHolder extends VH {

        @BindView(R.id.message)
        TextView message;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
