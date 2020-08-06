package com.example.brm;
import com.webank.mbank.wehttp.WeLog;
import com.webank.mbank.wehttp.WeOkHttp;
import com.webank.mbank.wehttp.WeReq;
import com.webank.normal.tools.WLogger;
import android.text.TextUtils;
import java.io.IOException;
import java.io.Serializable;

public class SignUseCase {
    private static final String TAG = "SignUseCase";

    private AppHandler handler;

    public SignUseCase(AppHandler handler) {
        this.handler = handler;
        initHttp();
    }

    private WeOkHttp myOkHttp = new WeOkHttp();

    private void initHttp() {
        //拿到OkHttp的配置对象进行配置
        //WeHttp封装的配置
        myOkHttp.config()
                //配置超时,单位:s
                .timeout(20, 20, 20)
                //添加PIN
                .log(WeLog.Level.BODY);
    }

    public void execute(final String mode, String appId, String userId, String nonce) {
        final String url = getUrl(appId, userId, nonce);

        requestExec(url, new WeReq.WeCallback<SignResponse>() {
            @Override
            public void onStart(WeReq weReq) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onFailed(WeReq weReq, int i, int i1, String s, IOException e) {
                requestError(i1, s);
            }

            @Override
            public void onSuccess(WeReq weReq, SignResponse signResponse) {
                if (signResponse != null) {
                    String sign = signResponse.sign;
                    processBody(mode, sign);
                } else {
                    requestError(AppHandler.ERROR_DATA, "get response is null");
                }
            }
        });
    }

    private void requestError(int code, String message) {
        WLogger.d(TAG, "签名请求失败:code=" + code + ",message=" + message);
        handler.sendSignError(code, message);
    }

    private void processBody(String mode, String sign) {
        if (TextUtils.isEmpty(sign) || "null".equals(sign)) {
            handler.sendSignError(AppHandler.ERROR_DATA, "sign is null.");
        } else {
            WLogger.d(TAG, "签名请求成功:" + sign);
            handler.sendSignSuccess(mode, sign);
        }
    }

    public static class SignResponse implements Serializable {
        public String sign;     //签名
    }

    private void requestExec(String url, WeReq.WeCallback<SignResponse> callback) {
        myOkHttp.<SignResponse>get(url).execute(SignResponse.class, callback);
    }

    private String getUrl(String appId, String userId, String nonce) {
        final String s = "https://ida.webank.com/ems-partner/cert/signature?appid=" + appId + "&nonce=" + nonce + "&userid=" + userId;
        WLogger.d(TAG, "get sign url=" + s);
        return s;
    }
}

