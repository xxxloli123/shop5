package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Activites;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ActivitesAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<Activites> activitess;
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

    public ActivitesAdapter(Context mContext, ArrayList<Activites> activitess, Callback callback) {
        this.activitess = activitess;
        this.mContext = mContext;
        mCallback = callback;
    }

    //刷新Adapter
    public void refresh(ArrayList<Activites> activitess) {
        this.activitess = activitess;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return activitess == null ? 0 : activitess.size();
    }

    @Override
    public Object getItem(int i) {
        return activitess.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_activity, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameTV.setText(activitess.get(i).getActivityName());
        holder.statusTV.setText(activitess.get(i).getStatus_value());
        holder.typeTV.setText(activitess.get(i).getType_value());
        holder.priorityTV.setText(activitess.get(i).getPriority() + "");
        holder.remarkTV.setText(activitess.get(i).getActivityRemarks());

        switch (activitess.get(i).getType_value()) {
            case "成为会员送优惠券":
                holder.YHQNameLL.setVisibility(View.VISIBLE);
                holder.moneyLL.setVisibility(View.VISIBLE);
                holder.restrictLL.setVisibility(View.VISIBLE);
                holder.timeLL.setVisibility(View.VISIBLE);

                holder.moneyTV.setText(activitess.get(i).getCost() + "");
                holder.restrictTV.setText(activitess.get(i).getLimitCost() + "");
                holder.YHQNameTV.setText(activitess.get(i).getCouponName());
                holder.timeTV.setText(activitess.get(i).getStartTime().substring(0, 9) +" 至 "+
                        activitess.get(i).getEndTime().substring(0, 9));
                break;
            case "满减活动":
                break;
            case "分享关注送优惠券":
                holder.YHQNameLL.setVisibility(View.VISIBLE);
                holder.moneyLL.setVisibility(View.VISIBLE);
                holder.restrictLL.setVisibility(View.VISIBLE);
                holder.timeLL.setVisibility(View.VISIBLE);
                holder.peoplesLL.setVisibility(View.VISIBLE);

                holder.moneyTV.setText(activitess.get(i).getCost() + "");
                holder.peoplesTV.setText(activitess.get(i).getFollowNumber() + "");
                holder.restrictTV.setText(activitess.get(i).getLimitCost() + "");
                holder.YHQNameTV.setText(activitess.get(i).getCouponName());
                holder.timeTV.setText(activitess.get(i).getStartTime().substring(0, 9) +" 至 "+
                        activitess.get(i).getEndTime().substring(0, 9));
                break;
            case "满赠活动":
                break;
            case "满免配送费活动":
                break;
        }
//        活动状态  Wait_audit("未发布 "), Normal("已发布"), Stop("已下架");
        if (activitess.get(i).getStatus().equals("Normal"))
            holder.issueORsoldOutTV.setText("下架");
        holder.editTV.setOnClickListener(this);
        holder.editTV.setTag(i);
        holder.issueORsoldOutTV.setOnClickListener(this);
        holder.issueORsoldOutTV.setTag(i);
        holder.deleteTV.setOnClickListener(this);
        holder.deleteTV.setTag(i);
        return view;
    }

//    public void updataView(int posi, ListView listView) {
//        int visibleFirstPosi = listView.getFirstVisiblePosition();
//        int visibleLastPosi = listView.getLastVisiblePosition();
//        if (posi >= visibleFirstPosi && posi <= visibleLastPosi) {
//            View view = listView.getChildAt(posi - visibleFirstPosi);
//            ViewHolder holder = (ViewHolder) view.getTag();
//
//            String txt = holder.issueORsoldOutTV.getText().toString();
//            txt = txt + "++;";
//            holder.strText.setText(txt);
//            strList.set(posi, txt);
//        } else {
//            String txt = strList.get(posi);
//            txt = txt + "++;";
//            strList.set(posi, txt);
//        }
//    }

    public static class ViewHolder {
        @BindView(R.id.status_TV)
        public TextView statusTV;
        @BindView(R.id.type_TV)
        TextView typeTV;
        @BindView(R.id.name_TV)
        TextView nameTV;
        @BindView(R.id.priority_TV)
        TextView priorityTV;
        @BindView(R.id.remark_TV)
        TextView remarkTV;
        @BindView(R.id.YHQ_name_TV)
        TextView YHQNameTV;
        @BindView(R.id.money_TV)
        TextView moneyTV;
        @BindView(R.id.restrict_TV)
        TextView restrictTV;
        @BindView(R.id.peoples_TV)
        TextView peoplesTV;
        @BindView(R.id.time_TV)
        TextView timeTV;
        @BindView(R.id.edit_TV)
        TextView editTV;
        @BindView(R.id.delete_TV)
        TextView deleteTV;

        @BindView(R.id.YHQ_name_LL)
        LinearLayout YHQNameLL;
        @BindView(R.id.money_LL)
        LinearLayout moneyLL;
        @BindView(R.id.restrict_LL)
        LinearLayout restrictLL;
        @BindView(R.id.peoples_LL)
        LinearLayout peoplesLL;
        @BindView(R.id.time_LL)
        LinearLayout timeLL;
        @BindView(R.id.issueORsold_out_TV)
       public TextView issueORsoldOutTV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
