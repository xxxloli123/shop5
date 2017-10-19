package com.example.xxxloli.zshmerchant.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.Activity.ShopInfoActivity;
import com.example.xxxloli.zshmerchant.R;

/**
 * Created by Administrator on 2017/9/16.
 */

public class EditChangedListener implements TextWatcher {
    private CharSequence temp;//监听前的文本
    private int editStart;//光标开始位置
    private int editEnd;//光标结束位置
    private final int charMaxNum = 10;
    private Context context;
    private int nowText;


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        if (DEBUG)
//            Log.i(TAG, "输入文本之前的状态");
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (DEBUG)
//            Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shop_notice, null);
//        TextView hint=view.findViewById(R.id.hint_text);
//        hint.setText("还能输入" + (charMaxNum - s.length()) + "字符");
        nowText=s.length();
    }

    @Override
    public void afterTextChanged(Editable s) {

        /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
//        editStart = mEditTextMsg.getSelectionStart();
//        editEnd = mEditTextMsg.getSelectionEnd();
//        if (temp.length() > charMaxNum) {
//            Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
//            s.delete(editStart - 1, editEnd);
//            int tempSelection = editStart;
//            mEditTextMsg.setText(s);
//            mEditTextMsg.setSelection(tempSelection);
//        }

    }
    public int getNowText(){
        return nowText;
    }
}