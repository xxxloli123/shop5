package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class LineDownExpenseAdapter1 extends BaseAdapter implements View.OnClickListener {

    private ArrayList<OrderEntity> orderEntities;
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

    public LineDownExpenseAdapter1(Context mContext, ArrayList<OrderEntity> orderEntities, Callback callback) {
        this.orderEntities = orderEntities;
        this.mContext = mContext;
        mCallback = callback;
    }

    //刷新Adapter
    public void refresh(ArrayList<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderEntities == null ? 0 : orderEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return orderEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_line_down_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.serialNumber.setText((orderEntities.size()-i)+"");
        holder.redPacketDeduction.setText(orderEntities.get(i).getRedPacketsFee() + "");
        holder.actualPrices.setText(orderEntities.get(i).getUserActualFee() + "");
        holder.orderingCode.setText(orderEntities.get(i).getMealcode() + "");
        holder.peopleNumber.setText(orderEntities.get(i).getMealnumber() + "");
        holder.downOrderTime.setText(orderEntities.get(i).getCreateDate() + "");
        holder.orderNumber.setText(orderEntities.get(i).getOrderNumber() + "");
        BillAdapter billAdapter = new BillAdapter(mContext, orderEntities.get(i).getGoods());
        holder.billList.setAdapter(billAdapter);
        final ViewHolder finalHolder1 = holder;
        holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalHolder1.billList.getVisibility() == View.VISIBLE) {
                    finalHolder1.indicateImg.setRotation(360);
                    finalHolder1.billList.setVisibility(View.GONE);
                    finalHolder1.redLL.setVisibility(View.GONE);
                } else {
                    finalHolder1.indicateImg.setRotation(180);
                    finalHolder1.billList.setVisibility(View.VISIBLE);
                    finalHolder1.redLL.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.printOrder.setOnClickListener(this);
        holder.printOrder.setTag(i);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.serial_number)
        TextView serialNumber;
        @BindView(R.id.ordering_code)
        TextView orderingCode;
        @BindView(R.id.people_number)
        TextView peopleNumber;
        @BindView(R.id.down_order_time)
        TextView downOrderTime;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.bill_list)
        ListView billList;
        @BindView(R.id.t)
        TextView t;
        @BindView(R.id.red_packet_deduction)
        TextView redPacketDeduction;
        @BindView(R.id.red_LL)
        RelativeLayout redLL;
        @BindView(R.id.print_order)
        Button printOrder;
        @BindView(R.id.indicate_img)
        ImageView indicateImg;
        @BindView(R.id.unfoldRL)
        RelativeLayout unfoldRL;
        @BindView(R.id.actual_prices)
        TextView actualPrices;
        @BindView(R.id.LL)
        LinearLayout LL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
