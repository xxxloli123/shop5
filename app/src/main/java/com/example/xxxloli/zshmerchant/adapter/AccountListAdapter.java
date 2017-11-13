package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.Account;
import com.example.xxxloli.zshmerchant.objectmodel.Info;
import com.interfaceconfig.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class AccountListAdapter extends BaseAdapter {

    private List<Account> accounts;
    private Context context;

    public AccountListAdapter(Context context, List<Account> accounts) {
        this.accounts = accounts;
        this.context = context;
    }


    @Override
    public int getCount() {
        return accounts == null ? 0 : accounts.size();
    }

    @Override
    public Object getItem(int i) {
        return accounts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_account, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(Config.Url.getUrl(Config.IMG_Hear)+accounts.get(i).getHead()).into(holder.headImg);
        holder.name.setText(accounts.get(i).getName());
        holder.phone.setText(accounts.get(i).getPhone());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.head_img)
        ImageView headImg;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.add_registerLL)
        LinearLayout addRegisterLL;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
