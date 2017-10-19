package com.sgrape.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.DialogBg;
import com.sgrape.util.R;

/**
 * Created by sgrape on 2017/5/14.
 * e-mail: sgrape1153@gmail.com
 */

public class LoadDialog extends Dialog {
    private TextView msg;


    public LoadDialog(@NonNull Context context) {
        super(context, R.style.DialogStyle);
        ProgressBar prog = new ProgressBar(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(prog);
        msg = new TextView(context);

        linearLayout.addView(msg);
        linearLayout.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackground(new DialogBg(20, Color.BLACK));
        } else linearLayout.setBackgroundDrawable(new DialogBg(20, Color.BLACK));
        int size=context.getResources().getDimensionPixelOffset(R.dimen.loadDialogSize);
        setLayout(size,size);
        setContentView(linearLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setMessage(CharSequence txt) {
        this.msg.setText(txt);
    }

}
