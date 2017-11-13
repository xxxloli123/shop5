package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.interfaceconfig.Config;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CommodityAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Commodity> commodities;
    private Context context;
    private Callback mCallback;

    //刷新Adapter
    public void refresh(ArrayList<Commodity> commodities) {
        this.commodities = commodities;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    public CommodityAdapter(Context context, ArrayList<Commodity> commodities, Callback callback) {
        this.commodities = commodities;
        this.context = context;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return commodities == null ? 0 : commodities.size();
    }

    @Override
    public Object getItem(int i) {
        return commodities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_commodity, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //   status 取值：：：{Wait_audit(未发布 ), Normal(已发布), Stop(已下架)};]
        if (commodities.get(i).getStatus().equals("Stop"))
            holder.backgroundLL.setBackgroundResource(R.color.hint1_text_color);
        else holder.backgroundLL.setBackgroundResource(R.color.white1);
        holder.commodityName.setText(commodities.get(i).getProductName() + "");
        Picasso.with(context).load(Config.Url.getUrl(Config.IMG_Commodity) + commodities.get(i).getSmallImg())
                .into(holder.commodityImg);
        holder.priceTv.setText(commodities.get(i).getSinglePrice() + "");
        holder.editBt.setOnClickListener(this);
        holder.editBt.setTag(i);
        holder.suspensionOfSale.setOnClickListener(this);
        holder.suspensionOfSale.setTag(i);
        return view;
    }

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


    public static class ViewHolder {
        @BindView(R.id.commodity_img)
        ImageView commodityImg;
        @BindView(R.id.commodity_name)
        TextView commodityName;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.edit_bt)
        Button editBt;
        @BindView(R.id.suspension_of_sale)
        public Button suspensionOfSale;
        @BindView(R.id.backgroundLL)
        public LinearLayout backgroundLL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
