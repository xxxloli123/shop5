package com.example.xxxloli.zshmerchant.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.FeedbackRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/24.
 */

public class FeedbackRecordAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<FeedbackRecord> datas;

    public FeedbackRecordAdapter(Activity activity, List<FeedbackRecord> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeedbackRecordVH(LayoutInflater.from(activity).inflate(R.layout.item_feedback_record,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((FeedbackRecordVH)holder).time.setText(datas.get(position).getTime());
        ((FeedbackRecordVH)holder).query.setText(datas.get(position).getQuery());
        ((FeedbackRecordVH)holder).reply.setText(datas.get(position).getReply());
        ((FeedbackRecordVH)holder).images.setAdapter(new ImageAdapter(activity,datas.get(position).getImages(),false));
    }

    @Override
    public int getItemCount() {
        return null == datas? 0 : datas.size();
    }
    public class FeedbackRecordVH extends RecyclerView.ViewHolder{
        @BindView(R.id.text_time)TextView time;
        @BindView(R.id.text_query)TextView query;
        @BindView(R.id.text_reply)TextView reply;
        @BindView(R.id.recycler_images)RecyclerView images;
        public FeedbackRecordVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            images.setLayoutManager(new GridLayoutManager(activity,6,GridLayoutManager.VERTICAL,false));
        }
    }
}
