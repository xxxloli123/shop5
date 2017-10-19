package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ClassifyExamineAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Classify> classifies;
    private Context mContext;
    private Callback mCallback;

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     *
     * @author Ivan Xu
     *         2014-11-26
     */
    public interface Callback {
        public void click(View v);
    }

    public ClassifyExamineAdapter(Context mContext, ArrayList<Classify> classifies, Callback callback) {
        this.classifies = classifies;
        this.mContext = mContext;
        mCallback = callback;
    }

    //刷新Adapter
    public void refresh(ArrayList<Classify> classifies) {
        this.classifies = classifies;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return classifies == null ? 0 : classifies.size();
    }

    @Override
    public Object getItem(int i) {
        return classifies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_classify1, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.classifyName.setText("" + classifies.get(i).getProductClassName());
        holder.deleteBt.setOnClickListener(this);
        holder.deleteBt.setTag(i);
        holder.editBt.setOnClickListener(this);
        holder.editBt.setTag(i);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.classify_name)
        TextView classifyName;
        @BindView(R.id.delete_bt)
        Button deleteBt;
        @BindView(R.id.edit_bt)
        Button editBt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
