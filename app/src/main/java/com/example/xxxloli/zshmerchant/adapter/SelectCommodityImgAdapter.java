package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.SelectCommodityImg;
import com.interfaceconfig.Config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SelectCommodityImgAdapter extends BaseAdapter {

    private ArrayList<SelectCommodityImg> selectCommodityImgs;
    private Context context;

    public SelectCommodityImgAdapter(Context context, ArrayList<SelectCommodityImg> selectCommodityImgs) {
        this.selectCommodityImgs = selectCommodityImgs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return selectCommodityImgs == null ? 0 : selectCommodityImgs.size();
    }

    @Override
    public Object getItem(int i) {
        return selectCommodityImgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_select_commodity_img, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.headlineText.setText(selectCommodityImgs.get(i).getImgName());
        Picasso.with(context).load(Config.Url.getUrl(Config.IMG)+selectCommodityImgs.get(i).getSmallImg()).into(holder.commodityImg);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.commodity_img)
        ImageView commodityImg;
        @BindView(R.id.headline_text)
        TextView headlineText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
