package com.example.xxxloli.zshmerchant.base;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.util.ToastUtil;

/**
 * Created by xxxloli on 2017/11/29.
 */
public class MyAsyncTask extends AsyncTask<Integer,Integer,String>
{
    private TextView txt;
    private ProgressBar pgbar;
    private Context context;

    public MyAsyncTask(TextView txt, ProgressBar pgbar, Context context)
    {
        super();
        this.txt = txt;
        this.pgbar = pgbar;
        this.context=context;
    }

    public class DelayOperator {
        //延时操作,用来模拟下载
        public void delay()
        {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    //该方法不运行在UI线程中,主要用于异步操作,通过调用publishProgress()方法
    //触发onProgressUpdate对UI进行操作
    @Override
    protected String doInBackground(Integer... params) {
        DelayOperator dop = new DelayOperator();
        int i = 0;
        for (i = 1;i <= 10;i+=1)
        {
            dop.delay();
            publishProgress(i);
        }
        return  i + params[0].intValue() + "";
    }

    //该方法运行在UI线程中,可对UI控件进行设置
    @Override
    protected void onPreExecute() {
        txt.setText("开始执行异步线程~");
    }


    //在doBackground方法中,每次调用publishProgress方法都会触发该方法
    //运行在UI线程中,可对UI控件进行操作


    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        ToastUtil.showToast(context,value+"");
        pgbar.setProgress(value);
    }
}