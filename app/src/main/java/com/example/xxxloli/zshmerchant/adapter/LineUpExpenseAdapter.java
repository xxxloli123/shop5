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
 * Created by Administrator on 2017/9/15.
 */

public class LineUpExpenseAdapter extends BaseAdapter<OrderEntity> {


    private Context context;

    public LineUpExpenseAdapter(Context context) {
        super(context, new ArrayList<OrderEntity>());
        this.context = context;
    }

    @Override
    protected void setData(View view, OrderEntity order, int position) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.downOrderTime.setText(order.getCreateDate());
        holder.orderNumber.setText(order.getOrderNumber());
        holder.namePhone.setText(order.getCreateUserName() + "  " + order.getCreateUserPhone());
        holder.address.setText(order.getEndHouseNumber());
        holder.distance.setText(order.getShopUserDistance() + "km");

        holder.packagingDispatchingRL.setVisibility(View.GONE);
        holder.billList.setVisibility(View.GONE);
        holder.orderMoneyRL.setVisibility(View.GONE);

        holder.packagingMoney.setText(order.getPackingprice() + "");
        holder.dispatchingMoney.setText(order.getDeliveryFee() + "");

        BillAdapter billAdapter = new BillAdapter(context, order.getGoods());
        holder.billList.setAdapter(billAdapter);
        holder.orderMoney.setText(order.getUserPayFee() + "");
        if (order.getPayStatus_value().equals("已付款")) holder.isPayment.setVisibility(View.VISIBLE);
        else holder.isPayment.setVisibility(View.GONE);
        holder.LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.billList.getVisibility() == View.GONE) {
                    holder.billList.setVisibility(View.VISIBLE);
                    holder.packagingDispatchingRL.setVisibility(View.VISIBLE);
                    holder.orderMoneyRL.setVisibility(View.VISIBLE);
                } else {
                    holder.packagingDispatchingRL.setVisibility(View.GONE);
                    holder.billList.setVisibility(View.GONE);
                    holder.orderMoneyRL.setVisibility(View.GONE);
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
        return R.layout.item_line_up_order;
    }

    public static class ViewHolder extends VH {

        @BindView(R.id.is_payment)
        TextView isPayment;
        @BindView(R.id.dispatching_info)
        LinearLayout dispatchingInfo;
        @BindView(R.id.LL)
        LinearLayout LL;
        @BindView(R.id.serial_number)
        public TextView serialNumber;
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
        public MyListView billList;
        @BindView(R.id.packaging_money)
        TextView packagingMoney;
        @BindView(R.id.dispatching_money)
        TextView dispatchingMoney;
        @BindView(R.id.red_packet_deduction)
        TextView redPacketDeduction;
        @BindView(R.id.order_money)
        TextView orderMoney;
        @BindView(R.id.order_income)
        TextView orderIncome;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.print_order)
        public Button printOrder;
        @BindView(R.id.packaging_dispatchingRL)
        public RelativeLayout packagingDispatchingRL;
        @BindView(R.id.discountsRL)
        public RelativeLayout discountsRL;
        @BindView(R.id.order_moneyRL)
        public RelativeLayout orderMoneyRL;
        @BindView(R.id.order_incomeRL)
        public RelativeLayout orderIncomeRL;

        public ViewHolder(View view) {
            super(view);
        }

    }

}
