package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.SecondsClassify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SecondsClassifyAdapter extends BaseAdapter {

    private ArrayList<SecondsClassify> secondsClassifies;
    private Context context;
    int mSelect = 0;
    private int defItem = -1;
    private OnItemListener onItemListener;


    public SecondsClassifyAdapter(Context context, ArrayList<SecondsClassify> secondsClassifies) {
        this.secondsClassifies = secondsClassifies;
        this.context = context;
    }

    public void setDefSelect(int position) {
        this.defItem = position;
        notifyDataSetChanged();
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
    public interface OnItemListener {
        void onClick(View v, int pos, String projectc);
    }

    @Override
    public int getCount() {
        return secondsClassifies == null ? 0 : secondsClassifies.size();
    }

    @Override
    public Object getItem(int i) {
        return secondsClassifies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_second_classify, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.secondLevel.setText(secondsClassifies.get(i).getName());
        if (defItem != -1) {
            if (defItem == i) {
                holder.secondLevel.setChecked(true);
            } else {
                holder.secondLevel.setChecked(false);
            }
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.second_level)
        CheckBox secondLevel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
