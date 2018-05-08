package com.doudou.facerecongnition.net;

import com.alibaba.fastjson.JSON;

/**
*大文件上传的监听器
*@author 豆豆
*时间:
*/
public interface BigFileUploadListener {
    void onProcess(JSON json);
    void onFinish(JSON json);
    void onError(JSON json);
}
