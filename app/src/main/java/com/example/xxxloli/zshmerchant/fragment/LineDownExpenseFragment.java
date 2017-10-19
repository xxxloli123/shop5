package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.LineDownExpenseAdapter;
import com.example.xxxloli.zshmerchant.adapter.SelectDateAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.sgrape.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LineDownExpenseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;
    @BindView(R.id.indicate_img)
    ImageView indicateImg;
    @BindView(R.id.select_date_text)
    TextView selectDateText;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.no_order)
    LinearLayout noOrder;
    @BindView(R.id.order_list)
    ListView orderList;

    private LineDownExpenseAdapter adapter;
    private int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_line_up_expense;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void readInstanceState() {
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
    }

    @Override
    protected void initListener() {
        orderList.setOnScrollListener(new OnScrollListener());
        srl.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        page = 0;
        if (srl != null && !srl.isRefreshing())
            srl.setRefreshing(true);
        loadData();
    }

    class Adapter extends LineDownExpenseAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final OrderEntity order, final int position) {
            super.setData(view, order, position);
            final ViewHolder holder = (ViewHolder) view.getTag();
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        intnView();
        return rootView;
    }

    private void intnView() {
        if (orderList.getAdapter() == null)
            orderList.setAdapter(adapter);
        if (orderList.getCount() > 0) {
            noOrder.setVisibility(View.GONE);
        } else {
            noOrder.setVisibility(View.VISIBLE);
        }
        loadData();
    }

    @Override
    protected void loadData() {
        List<OrderEntity> orders = adapter.getData();
        if (orders == null) orders = new ArrayList<>();
        if (page == 1 && !orders.isEmpty()) orders.clear();

        if (orders.isEmpty()) noOrder.setVisibility(View.VISIBLE);
        else noOrder.setVisibility(View.GONE);
        srl.setRefreshing(false);
        firstLoad = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.select_date_text, R.id.all_orders_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_date_text:
                selectDate();
                break;
            case R.id.all_orders_text:
                break;
        }
    }

    private void selectDate() {
        final String dates[] = {"今日", "昨日", "近7日", "更多"};
        final ListPopupWindow listPopupWindow = new ListPopupWindow(getActivity());
        SelectDateAdapter selectDateAdapter = new SelectDateAdapter(getActivity(), dates);
        listPopupWindow.setAdapter(selectDateAdapter);
        listPopupWindow.setAnchorView(selectDateText);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setModal(true);
        indicateImg.setRotation(180);
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                indicateImg.setRotation(360);
            }
        });
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                selectDateText.setText(dates[position]);
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

//    private void evaluateCourier(){
//
//    }


    private void isCancelOrder() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_cancel_oder, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    class OnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
//            if (firstVisibleItem + visibleItemCount > orderList.getAdapter().getCount() - 5
//                    && orderList.getAdapter().getCount() % 20 == 0
//                    && orderList.getAdapter().getCount() != 0) {
//                loadData();
//            }
        }
    }
}
