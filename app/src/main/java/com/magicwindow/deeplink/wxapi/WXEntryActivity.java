package com.magicwindow.deeplink.wxapi;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;

import cn.magicwindow.marketing.share.activity.MWWXHandlerActivity;

/**
 * 微信分享activity
 *
 * @author youtui
 * @since 14/5/4
 */

public class WXEntryActivity extends MWWXHandlerActivity {

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        // super.onReq(req);必须加
        super.onReq(req);
        //todo: 在下面添加你可能需要实现的代码
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp response) {
        // super.onResp(response);必须加
        super.onResp(response);
        //todo: 在下面添加你可能需要实现的代码
    }

}
