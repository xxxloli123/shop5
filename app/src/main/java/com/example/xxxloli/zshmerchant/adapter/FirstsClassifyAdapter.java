package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.FirstsClassify;
import com.example.xxxloli.zshmerchant.objectmodel.SecondsClassify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/21.
 */

public class FirstsClassifyAdapter extends BaseAdapter {

    private ArrayList<FirstsClassify> firstsClassifies;
    private Context mContext;
    private SecondsClassifyAdapter secondsClassifyAdapter;


    public FirstsClassifyAdapter(Context mContext,  ArrayList<FirstsClassify> firstsClassifies) {
        this.firstsClassifies = firstsClassifies;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return firstsClassifies == null ? 0 : firstsClassifies.size();
    }

    @Override
    public Object getItem(int i) {
        return firstsClassifies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_first_classify, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.firstLevelText.setText(firstsClassifies.get(i).getName());
        ArrayList<SecondsClassify> secondsClassifies=firstsClassifies.get(i).getSecondsClassifies();
        secondsClassifyAdapter=new SecondsClassifyAdapter(mContext,secondsClassifies);
        holder.secondLevelGrid.setAdapter(secondsClassifyAdapter);
        secondsClassifyAdapter.setOnItemListener(new SecondsClassifyAdapter.OnItemListener() {
            @Override
            public void onClick(View v, int pos, String projectc) {
                secondsClassifyAdapter.setDefSelect(pos);
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.first_level_text)
        TextView firstLevelText;
        @BindView(R.id.second_level_grid)
        GridView secondLevelGrid;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
