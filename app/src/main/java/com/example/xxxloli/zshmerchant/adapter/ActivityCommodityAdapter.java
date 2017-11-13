package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.ActivityCommodity;
import com.interfaceconfig.Config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ActivityCommodityAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<ActivityCommodity> activityCommodities;
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

    public ActivityCommodityAdapter(Context mContext, ArrayList<ActivityCommodity> activityCommodities, Callback callback) {
        this.activityCommodities = activityCommodities;
        this.mContext = mContext;
        mCallback = callback;
    }

    //刷新Adapter
    public void refresh(ArrayList<ActivityCommodity> activityCommodities) {
        this.activityCommodities = activityCommodities;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return activityCommodities == null ? 0 : activityCommodities.size();
    }

    @Override
    public Object getItem(int i) {
        return activityCommodities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_activity_commodity, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.statusTV.setText(activityCommodities.get(i).getStatus_value());
        holder.typeTV.setText(activityCommodities.get(i).getType_value());
        if (activityCommodities.get(i).getType().equals("Seckill")) {
            holder.timeTV.setText(activityCommodities.get(i).getSeckillTime() + "");
            holder.timeTV.setVisibility(View.VISIBLE);
        }
        holder.nameTV.setText(activityCommodities.get(i).getProductName() + "");
        holder.originalPriceTV.setText(activityCommodities.get(i).getOriginalPrice() + "");
        holder.activitesPriceTV.setText(activityCommodities.get(i).getBargainPrice() + "");
        holder.inventoryTV.setText(activityCommodities.get(i).getInventory() + "");
        holder.commodityName.setText(activityCommodities.get(i).getProductName() + "");
        Picasso.with(mContext).load(Config.Url.getUrl(Config.IMG_Commodity)+activityCommodities
                .get(i).getProductSmallImg()).into(holder.commodityImg);
        holder.deleteTV.setOnClickListener(this);
        holder.deleteTV.setTag(i);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.name_TV)
        TextView nameTV;
        @BindView(R.id.status_TV)
        TextView statusTV;
        @BindView(R.id.type_TV)
        TextView typeTV;
        @BindView(R.id.time_TV)
        TextView timeTV;
        @BindView(R.id.original_price_TV)
        TextView originalPriceTV;
        @BindView(R.id.activites_price_TV)
        TextView activitesPriceTV;
        @BindView(R.id.inventory_TV)
        TextView inventoryTV;
        @BindView(R.id.delete_TV)
        TextView deleteTV;
        @BindView(R.id.commodity_img)
        ImageView commodityImg;
        @BindView(R.id.commodity_name)
        TextView commodityName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
