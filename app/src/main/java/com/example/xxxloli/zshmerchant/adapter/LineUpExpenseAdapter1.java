package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.example.xxxloli.zshmerchant.view.MyListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class LineUpExpenseAdapter1 extends BaseAdapter implements View.OnClickListener {

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

    public LineUpExpenseAdapter1(Context mContext, ArrayList<OrderEntity> orderEntities, Callback callback) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_line_up_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.downOrderTime.setText(orderEntities.get(i).getCreateDate());
        holder.orderNumber.setText(orderEntities.get(i).getOrderNumber());
        holder.namePhone.setText(orderEntities.get(i).getCreateUserName() + "  " + orderEntities.get(i).getCreateUserPhone());
        holder.address.setText(orderEntities.get(i).getEndHouseNumber()+"");
        holder.distance.setText(orderEntities.get(i).getShopUserDistance() + "km");

        if (orderEntities.size()<4){
            holder.packagingDispatchingRL.setVisibility(View.VISIBLE);
            holder.billList.setVisibility(View.VISIBLE);
            holder.orderMoneyRL.setVisibility(View.VISIBLE);
        }else {
            holder.packagingDispatchingRL.setVisibility(View.GONE);
            holder.billList.setVisibility(View.GONE);
            holder.orderMoneyRL.setVisibility(View.GONE);
        }

        holder.packagingMoney.setText(orderEntities.get(i).getPackingprice() + "");
        holder.dispatchingMoney.setText(orderEntities.get(i).getDeliveryFee() + "");

        BillAdapter billAdapter = new BillAdapter(mContext, orderEntities.get(i).getGoods());
        holder.billList.setAdapter(billAdapter);
        holder.orderMoney.setText(orderEntities.get(i).getUserActualFee() + "");
        if (orderEntities.get(i).getPayStatus_value().equals("已付款")) holder.isPayment.setVisibility(View.VISIBLE);
        else holder.isPayment.setVisibility(View.GONE);
        holder.serialNumber.setText((orderEntities.size()-i)+"");

        final ViewHolder finalHolder = holder;
        holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalHolder.billList.getVisibility() == View.GONE) {
                    finalHolder.billList.setVisibility(View.VISIBLE);
                    finalHolder.packagingDispatchingRL.setVisibility(View.VISIBLE);
                    finalHolder.orderMoneyRL.setVisibility(View.VISIBLE);
                } else {
                    finalHolder.packagingDispatchingRL.setVisibility(View.GONE);
                    finalHolder.billList.setVisibility(View.GONE);
                    finalHolder.orderMoneyRL.setVisibility(View.GONE);
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
        @BindView(R.id.down_order_time)
        TextView downOrderTime;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.name_phone)
        TextView namePhone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.bill_list)
        MyListView billList;
        @BindView(R.id.packaging_money)
        TextView packagingMoney;
        @BindView(R.id.dispatching_money)
        TextView dispatchingMoney;
        @BindView(R.id.packaging_dispatchingRL)
        RelativeLayout packagingDispatchingRL;
        @BindView(R.id.t)
        TextView t;
        @BindView(R.id.red_packet_deduction)
        TextView redPacketDeduction;
        @BindView(R.id.discountsRL)
        RelativeLayout discountsRL;
        @BindView(R.id.is_payment)
        TextView isPayment;
        @BindView(R.id.order_money)
        TextView orderMoney;
        @BindView(R.id.order_moneyRL)
        RelativeLayout orderMoneyRL;
        @BindView(R.id.order_income)
        TextView orderIncome;
        @BindView(R.id.order_incomeRL)
        RelativeLayout orderIncomeRL;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.dispatching_info)
        LinearLayout dispatchingInfo;
        @BindView(R.id.cancel_order)
        View cancelOrder;
        @BindView(R.id.print_order)
        Button printOrder;
        @BindView(R.id.LL)
        LinearLayout LL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
