package com.doudou.facerecongnition.net;

import com.alibaba.fastjson.JSONObject;

/**
*网络请求回调接口
*@author 豆豆
*时间:
*/
public interface HttpCallBackListener {
    void onFinish(JSONObject response);
    void onError(Exception e);
}
