package com.example.xxxloli.zshmerchant.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class Main2Activity extends AppCompatActivity {

    private ArrayList<BluetoothDevice> mDeviceList;
    private BluetoothAdapter mBlueAdapter;
    private BluetoothSocket mBlueSocket;
    private OutputStream mOutputStream = null;
    private boolean mIsConnect = false;

    public static final byte[][] byteCommands = { { 0x1b, 0x40 },// 复位打印机
            { 0x1b, 0x4d, 0x00 },// 标准ASCII字体
            { 0x1b, 0x4d, 0x01 },// 压缩ASCII字体
            { 0x1d, 0x21, 0x00 },// 字体不放大
            { 0x1d, 0x21, 0x11 },// 宽高加倍
            { 0x1b, 0x45, 0x00 },// 取消加粗模式
            { 0x1b, 0x45, 0x01 },// 选择加粗模式
            { 0x1b, 0x7b, 0x00 },// 取消倒置打印
            { 0x1b, 0x7b, 0x01 },// 选择倒置打印
            { 0x1d, 0x42, 0x00 },// 取消黑白反显
            { 0x1d, 0x42, 0x01 },// 选择黑白反显
            { 0x1b, 0x56, 0x00 },// 取消顺时针旋转90°
            { 0x1b, 0x56, 0x01 },// 选择顺时针旋转90°
            { 0x1b, 0x69 } // 选择顺时针旋转90°
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // 获取蓝牙适配器
        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();
        // 如果获取的蓝牙适配器为空 说明 该设备不支持蓝牙
        if (mBlueAdapter == null) {
            finish();
        }
        mDeviceList = new ArrayList<BluetoothDevice>();

        initIntentFilter();
    }

    private void initIntentFilter() {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播
        unregisterReceiver(receiver);
    }

    /**
     * 蓝牙广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        ProgressDialog progressDialog = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    // 该设备是之前配对设备
                } else {
                    // 该设备是没有进行过配对的设备
                    mDeviceList.add(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                progressDialog = ProgressDialog.show(context, "请稍等...",
                        "正在搜索蓝牙设备...", true);
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                // 蓝牙设备搜索完毕
                progressDialog.dismiss();
            }
        }

    };

    /**
     * * using the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB.
     * However if you are connecting to an Android peer then please generate
     * your own unique UUID.
     * <p>
     * Requires {@link android.Manifest.permission#BLUETOOTH}
     *
     * @param
//     *             record uuid to lookup RFCOMM channel
     * @return a RFCOMM BluetoothServerSocket ready for an outgoing connection
     * @throws IOException
     *             on error, for example Bluetooth not available, or
     *             insufficient permissions
     * @param device
     * @return
     */
    public void connect(BluetoothDevice device) {
        // 蓝牙连接的uuid 注意：蓝牙设备连接必须使用该uuid 上面注释从源码中拷贝出来的
        // 可以看到 using the well-known SPP UUID
        // 00001101-0000-1000-8000-00805F9B34FB.
        // 而且别忘了给使用蓝牙的程序添加所需要的权限
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        try {
            // 根据uuid 获取一个蓝牙socket
            mBlueSocket = device.createRfcommSocketToServiceRecord(uuid);
            // 进行连接
            mBlueSocket.connect();
            // 连接后获取输出流
            mOutputStream = mBlueSocket.getOutputStream();
            // 如果蓝牙还在搜索的话 则停止搜索 （蓝牙搜索比较耗资源）
            if (mBlueAdapter.isDiscovering()) {
                mBlueAdapter.cancelDiscovery();
            }
        } catch (Exception e) {
            Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
        }
        mIsConnect = true;
        Toast.makeText(this, device.getName() + "连接成功！", Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 打印内容
     */
    public void printContent() {
        // 如果连接成功
        if (mIsConnect) {
            try {
                String title = "打印的标题";
                //执行其他命令之前  先进行复位
                mOutputStream.write(byteCommands[0]);
                //宽高加倍指令
                mOutputStream.write(byteCommands[4]);
                byte[] titleData = title.getBytes("GB2312");
                mOutputStream.write(titleData, 0, titleData.length);

                String content = "这是需要打印的内容 hello word";
                mOutputStream.write(byteCommands[0]);
                //恢复到标准字体

                mOutputStream.write(byteCommands[1]);
                byte[] contentData = title.getBytes("GB2312");
                mOutputStream.write(contentData, 0, contentData.length);
                mOutputStream.flush();
            } catch (IOException e) {
                Toast.makeText(this, "打印失败！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "蓝牙设备为连接，或者连接失败！", Toast.LENGTH_SHORT).show();
        }
    }

}


