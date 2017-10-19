package com.sgrape.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sgrape.library.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by sgrape on 2017/4/29.
 */

public class Dialog extends android.app.Dialog implements View.OnClickListener {

    protected TextView title;
    protected View done;
    protected View close;
    protected View cancel;
    private Unbinder unbinder;
    private DialogButtonClickListener buttonClickListener;

    public Dialog(@NonNull Context context) {
        super(context);
    }

    public Dialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        WindowManager.LayoutParams attrs = win.getAttributes();
    }


    public void setLayout(int width, int height) {
        getWindow().setLayout(width, height);
    }


    protected <T> T searchView(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(view);
        initView();
    }

    @Override
    public void setContentView(@NonNull View view, @Nullable ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initView();
    }


    private void initView(){
        unbinder = ButterKnife.bind(this, getWindow().getDecorView());
        title = searchView(R.id.dialog_title);
        done = findViewById(R.id.dialog_done);
        close = findViewById(R.id.dialog_close);
        cancel = findViewById(R.id.dialog_cancel);
        if (done != null) done.setOnClickListener(this);
        if (close != null) close.setOnClickListener(this);
        if (cancel != null) cancel.setOnClickListener(this);
    }
    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
            if (unbinder != null) unbinder.unbind();
        }
    }


    @Override
    public void setTitle(@Nullable CharSequence title) {
        if (this.title != null) this.title.setText(title);
        super.setTitle(title);
    }

    @Override
    public void setTitle(@StringRes int titleId) {
        if (this.title != null) this.title.setText(titleId);
        super.setTitle(titleId);
    }

    public void setButtonClickListener(DialogButtonClickListener l) {
        buttonClickListener = l;
    }

    @Override
    public void show() {
        if (!isShowing())
            super.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_cancel) {
            if (buttonClickListener != null) buttonClickListener.cancel(this);
            dismiss();
        } else if (v.getId() == R.id.dialog_close) {
            if (buttonClickListener != null) buttonClickListener.close(this);
            dismiss();
        } else if (v.getId() == R.id.dialog_done) {
            if (buttonClickListener != null) buttonClickListener.done(this);
            dismiss();
        }
    }

    public interface DialogButtonClickListener<T extends Dialog> {
        void done(T dialog);

        void cancel(T dialog);

        void close(T dialog);
    }
}
