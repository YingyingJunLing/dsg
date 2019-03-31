package com.bw.erji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bw.erji.model.bean.LfteBean;
import com.bw.erji.model.bean.RightBean;
import com.bw.erji.presenter.MainPresenter;
import com.bw.erji.view.activity.BaseActivity;
import com.bw.erji.view.adapter.LeftAdapter;
import com.bw.erji.view.adapter.RightAdapter;
import com.bw.erji.view.interfaces.MainInterface;

import java.util.List;

public class MainActivity extends BaseActivity implements MainInterface {

RecyclerView left1;
RecyclerView right1;
    private RightAdapter rightAdapter;
    private MainPresenter mainPresenter;

    @Override
    protected void bindEvent() {

    }



    @Override
    protected void initView() {
        left1 = findViewById(R.id.left1);
        right1 = findViewById(R.id.right1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        left1.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,3);
        right1.setLayoutManager(gridLayoutManager);


    }
    @Override
    protected void initData() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.getData();
        mainPresenter.getRightData(1);
        mainPresenter.setView(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(Object t) {
        if( t instanceof LfteBean)
        {
           final LfteBean lfteBean = (LfteBean) t;
            LeftAdapter leftAdapter = new LeftAdapter(MainActivity.this, lfteBean);
            left1.setAdapter(leftAdapter);
            leftAdapter.ClickListener(new LeftAdapter.ClickListener() {
                @Override
                public void CallBack(int position, List<LfteBean.DataBean> data) {
                   mainPresenter .getRightData(lfteBean.getData().get(position).getCid());
                    rightAdapter.notifyDataSetChanged();
                }
            });
        }
        if (t instanceof RightBean)
        {
            RightBean rightBean = (RightBean) t;
            Log.e("rightBean",rightBean+"");
            rightAdapter = new RightAdapter(MainActivity.this, rightBean);
            right1.setAdapter(rightAdapter);
        }

    }

    @Override
    public void onFail(String s) {

    }
}
