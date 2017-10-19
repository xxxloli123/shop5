package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.sgrape.adapter.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class OrderListAdapter extends BaseAdapter<OrderEntity> {


    private Context context;

    public OrderListAdapter(Context context) {
        super(context, new ArrayList<OrderEntity>());
        this.context = context;
    }

    private boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    @Override
    protected void setData(final View view, final OrderEntity order, int position) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.money.setText(order.getUserActualFee() + "");
        holder.downOrderTime.setText(order.getCreateDate());
        holder.orderNumber.setText(order.getOrderNumber());
        if (order.getLineOrder_value().equals("预定单")) {
            holder.psdjText.setText("预定单");
            holder.psdjText.setBackgroundResource(R.drawable.round_yellow_background);
        } else if (order.getLineOrder_value().equals("到店消费")) {
            holder.psdjText.setText("到店消费");
            holder.psdjText.setBackgroundResource(R.drawable.round_red_background);
        }
        holder.namePhone.setText(order.getCreateUserName() + "  " + order.getCreateUserPhone());
        holder.address.setText(order.getEndHouseNumber());
        holder.distance.setText(order.getShopUserDistance() + "km");

        holder.packagingDispatchingRL.setVisibility(View.GONE);
        holder.remark.setVisibility(View.GONE);
        holder.billList.setVisibility(View.GONE);
        holder.orderMoneyRL.setVisibility(View.GONE);
        holder.line.setVisibility(View.GONE);

        holder.packagingMoney.setText(order.getPackingprice() + "");
        holder.dispatchingMoney.setText(order.getDeliveryFee() + "");

        BillAdapter billAdapter = new BillAdapter(context, order.getGoods());
        holder.billList.setAdapter(billAdapter);
        holder.remark.setText("备注: " + order.getComment());
        holder.orderMoney.setText(order.getUserPayFee() + "");
        if (order.getPayStatus_value().equals("已付款")) holder.isPayment.setVisibility(View.VISIBLE);
        else holder.isPayment.setVisibility(View.GONE);
        holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.remark.getVisibility() == View.GONE) {
                    holder.remark.setVisibility(View.VISIBLE);
                    holder.billList.setVisibility(View.VISIBLE);
                    holder.packagingDispatchingRL.setVisibility(View.VISIBLE);
                    holder.orderMoneyRL.setVisibility(View.VISIBLE);
                    holder.line.setVisibility(View.VISIBLE);
                } else {
                    holder.packagingDispatchingRL.setVisibility(View.GONE);
                    holder.remark.setVisibility(View.GONE);
                    holder.billList.setVisibility(View.GONE);
                    holder.orderMoneyRL.setVisibility(View.GONE);
                    holder.line.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected VH newViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_handle_order;
    }

    public static class ViewHolder extends VH {

        @BindView(R.id.bill_list)
        MyListView billList;
        @BindView(R.id.line)
        View line;
        @BindView(R.id.order_moneyRL)
        RelativeLayout orderMoneyRL;
        @BindView(R.id.isPayment)
        TextView isPayment;
        @BindView(R.id.remark)
        TextView remark;
        @BindView(R.id.LL)
        LinearLayout LL;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.down_order_time)
        TextView downOrderTime;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.psdj_text)
        TextView psdjText;
        @BindView(R.id.name_phone)
        TextView namePhone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.distance)
        TextView distance;
        @BindView(R.id.packaging_money)
        TextView packagingMoney;
        @BindView(R.id.dispatching_money)
        TextView dispatchingMoney;
        @BindView(R.id.packaging_dispatchingRL)
        RelativeLayout packagingDispatchingRL;
        @BindView(R.id.red_packet_deduction)
        TextView redPacketDeduction;
        @BindView(R.id.order_money)
        TextView orderMoney;
        @BindView(R.id.reject)
        public Button reject;
        @BindView(R.id.receiving_order)
        public Button receivingOrder;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
