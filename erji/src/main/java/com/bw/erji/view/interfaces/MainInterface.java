package com.bw.erji.view.interfaces;

/**
 * @Auther: 赵维鸣
 * @Date: 2019/3/2 10:37:34
 * @Description:
 */
public interface MainInterface<T> extends BaseInterface {
    void onSuccess(Object t);
    void onFail(String s);
}
