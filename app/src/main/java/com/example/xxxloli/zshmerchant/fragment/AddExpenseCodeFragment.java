package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.CodeAdapter;
import com.example.xxxloli.zshmerchant.adapter.TableAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.ExpenseCode;
import com.example.xxxloli.zshmerchant.objectmodel.Table;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/14.
 */

public class AddExpenseCodeFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.code_show)
    GridView codeShow;


    private ArrayList<ExpenseCode> expenseCodes;
    private CodeAdapter codeAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_expense_code;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_ExpenseCode:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                JSONArray arr = json.getJSONArray("listcode");
                Gson gson = new Gson();
                expenseCodes = new ArrayList<>();
                codeAdapter = new CodeAdapter(getActivity(), expenseCodes);
                codeShow.setAdapter(codeAdapter);
                for (int i = 0; i < arr.length(); i++) {
                    expenseCodes.add(gson.fromJson(arr.getString(i), ExpenseCode.class));
                }
                ExpenseCode code=new ExpenseCode();
                code.setId("666");
                expenseCodes.add(code);
                codeAdapter.refresh(expenseCodes);
                break;
            case Config.ADD_ExpenseCode:
                initView();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

        Map<String, Object> params = new HashMap<>();
        params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
        newCall(Config.Url.getUrl(Config.GET_ExpenseCode), params);
        codeShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=expenseCodes.size()-1)return;
                addCode();
            }
        });
    }

    private void addCode() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_table_number, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView add = view.findViewById(R.id.add);
        TextView hint = view.findViewById(R.id.hint_text);
        hint.setText("请输入三位数的消费码就行添加");
        final EditText number = view.findViewById(R.id.edit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(number.getText())) {
                    Toast.makeText(getActivity(), "请输入消费码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (number.getText().toString().length()!=3){
                    Toast.makeText(getActivity(), "请输入三位数的消费码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                JSONObject shoptableStr = new JSONObject();
                try {
                    //[shopName店名, shopId店铺id, shopkeeperId店主id, shopkeeperName店主名称, consumeCode 点餐码]
                    shoptableStr.put("shopName", "asdhfkjhasd");
                    shoptableStr.put("shopId", "402880e75f000ab6015f0043a1fc0004");
                    shoptableStr.put("shopkeeperId", "402880e75f000ab6015f0043a1210002");
                    shoptableStr.put("shopkeeperName", "梁非凡");
                    shoptableStr.put("consumeCode", number.getText().toString());

                    params.put("shopconsumecodeStr", shoptableStr);
                    newCall(Config.Url.getUrl(Config.ADD_ExpenseCode), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
