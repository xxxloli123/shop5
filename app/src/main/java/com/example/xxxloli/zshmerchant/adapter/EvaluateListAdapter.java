package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Evaluate;
import com.example.xxxloli.zshmerchant.view.RatingBar;
import com.sgrape.adapter.BaseAdapter;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class EvaluateListAdapter extends BaseAdapter<Evaluate> {


    private int textNumer;

    public EvaluateListAdapter(Context context) {

        super(context, new ArrayList<Evaluate>());
    }

    private boolean enable = true;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    protected void setData(final View view, Evaluate evaluate, int position) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.rbSpeed.setCountSelected(evaluate.getGrade());
        holder.time.setText(evaluate.getCreateDate());
        holder.evaluate.setText(evaluate.getComment());
        if (evaluate.getReplyStatus().equals("yes")) {
            holder.reply.setVisibility(View.GONE);
            holder.hint.setVisibility(View.GONE);
            holder.sure.setVisibility(View.GONE);
            holder.replyTv.setVisibility(View.VISIBLE);
        }
        holder.replyTv.setText(evaluate.getReplycomment()+"");
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
    protected VH newViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_evaluate;
    }

    public static class ViewHolder extends VH {

        @BindView(R.id.rb_speed)
        RatingBar rbSpeed;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.evaluate)
        TextView evaluate;
        @BindView(R.id.hint)
        TextView hint;
        @BindView(R.id.sure)
        public Button sure;
        @BindView(R.id.reply)
        public EditText reply;
        @BindView(R.id.reply_Tv)
        TextView replyTv;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
