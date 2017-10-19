package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.sgrape.adapter.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/15.
 */

public class LineDownExpenseAdapter extends BaseAdapter<OrderEntity> {


    private Context context;

    public LineDownExpenseAdapter(Context context) {
        super(context, new ArrayList<OrderEntity>());
        this.context = context;
    }

    @Override
    protected void setData(View view, OrderEntity orderEntity, int position) {
        final ViewHolder holder = (ViewHolder) view.getTag();

        holder.unfoldRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //账单  ListView
                if (holder.billList.getVisibility() == View.VISIBLE) {
//                    holder.peopleNumber.setText(commodities.size()+"");
                    holder.indicateImg.setRotation(360);
                    holder.billList.setVisibility(View.GONE);
                } else {
                    holder.billList.setVisibility(View.VISIBLE);
                    holder.indicateImg.setRotation(180);
                }
                //优惠RelativeLayout
                if (holder.discountsRL.getVisibility() == View.VISIBLE) {
                    holder.discountsRL.setVisibility(View.GONE);
                } else {
                    holder.discountsRL.setVisibility(View.VISIBLE);
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
        return R.layout.item_line_down_order;
    }

    public static class ViewHolder extends VH {
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
        public ListView billList;
        @BindView(R.id.red_packet_deduction)
        TextView redPacketDeduction;
        @BindView(R.id.discountsRL)
        public RelativeLayout discountsRL;
        @BindView(R.id.print_order)
        Button printOrder;
        @BindView(R.id.unfoldRL)
        public RelativeLayout unfoldRL;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.total_prices)
        TextView totalPrices;
        @BindView(R.id.actual_prices)
        TextView actualPrices;
        @BindView(R.id.indicate_img)
        public ImageView indicateImg;

        public ViewHolder(View view) {
            super(view);
        }

    }

}
