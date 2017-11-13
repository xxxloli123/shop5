package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Evaluate;
import com.example.xxxloli.zshmerchant.view.RatingBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/15.
 */

public class EvaluateListAdapter1 extends RecyclerView.Adapter<EvaluateListAdapter1.ViewHolder> implements View.OnClickListener {

    private int textNumer;

    private LayoutInflater mInflater;
    private ArrayList<Evaluate> evaluates;
    int mSelect = 0;
    private Callback mCallback;
    private Context context;

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

    public EvaluateListAdapter1(Context context, ArrayList<Evaluate> evaluates, Callback callback) {
        mInflater = LayoutInflater.from(context);
        this.evaluates = evaluates;
        mCallback = callback;
        this.context = context;
    }

    //刷新Adapter
    public void refresh(ArrayList<Evaluate> evaluates) {
        this.evaluates = evaluates;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }
        @BindView(R.id.rb_speed)
        RatingBar rbSpeed;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.evaluate)
        TextView evaluate;
        @BindView(R.id.reply_Tv)
        TextView replyTv;
        @BindView(R.id.reply)
        EditText reply;
        @BindView(R.id.hint)
        TextView hint;
        @BindView(R.id.sure)
        Button sure;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_evaluate, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.rbSpeed = view.findViewById(R.id.rb_speed);
        viewHolder.time = view.findViewById(R.id.time);
        viewHolder.evaluate = view.findViewById(R.id.evaluate);
        viewHolder.replyTv = view.findViewById(R.id.reply_Tv);
        viewHolder.reply = view.findViewById(R.id.reply);
        viewHolder.hint = view.findViewById(R.id.hint);
        viewHolder.sure = view.findViewById(R.id.sure);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.rbSpeed.setCountSelected(evaluates.get(position).getGrade());
        holder.time.setText(evaluates.get(position).getCreateDate());
        holder.evaluate.setText(evaluates.get(position).getComment());
        if (evaluates.get(position).getReplyStatus().equals("yes")) {
            holder.reply.setVisibility(View.GONE);
            holder.hint.setVisibility(View.GONE);
            holder.sure.setVisibility(View.GONE);
            holder.replyTv.setVisibility(View.VISIBLE);
        }
        holder.replyTv.setText(evaluates.get(position).getReplycomment()+"");
        holder.reply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textNumer = charSequence.length();
                holder.hint.setText(textNumer + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return evaluates.size();
    }
}
