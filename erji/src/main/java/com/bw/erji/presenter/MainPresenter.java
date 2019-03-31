package com.bw.erji.presenter;

import com.bw.erji.MainActivity;
import com.bw.erji.model.api.Api;
import com.bw.erji.model.bean.LfteBean;
import com.bw.erji.model.bean.RightBean;
import com.bw.erji.model.utils.HttpUtils;
import com.bw.erji.view.interfaces.MainInterface;

import java.io.IOException;


public class MainPresenter extends BasePresenter<MainInterface<LfteBean>> {

    private final HttpUtils httpUtils;

    public MainPresenter(MainActivity mainActivity)
    {
        httpUtils = HttpUtils.getInstance();
    }
    public void getData()
    {
        httpUtils.getData(Api.URL, LfteBean.class, new HttpUtils.HttpCallBackData() {
            @Override
            public void onResponse(Object object) {
                getView().onSuccess(object);
            }

            @Override
            public void onFailure(IOException e) {

            }
        });
    }

    public void getRightData(int id) {
        httpUtils.getData(Api.RIGHT + "?cid=" + id, RightBean.class, new HttpUtils.HttpCallBackData() {
            @Override
            public void onResponse(Object object) {
                getView().onSuccess(object);

            }

            @Override
            public void onFailure(IOException e) {

            }
        });
    }
}
