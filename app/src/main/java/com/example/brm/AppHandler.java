package com.example.brm;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.webank.facelight.ui.FaceVerifyStatus;
import com.webank.normal.tools.WLogger;
public class AppHandler {
    private static final String TAG = "FaceVerifyDemoActivity";
    public static final int ERROR_DATA = -100;
    public static final int ERROR_LOCAL = -101;
    public static final String DATA_MODE_REFLECT = "data_mode_reflect";
    public static final String DATA_MODE_REFLECT_DESENSE = "data_mode_reflect_desense";
    public static final String DATA_MODE_OCR = "data_mode_ocr";
    private static final int WHAT_SIGN = 1;
    private static final int ARG1_SUCCESS = 1;
    private static final int ARG1_FAILED = 2;
    private static final String DATA_MODE = "data_mode";
    private static final String DATA_SIGN = "data_sign";
    private static final String DATA_CODE = "data_code";
    private static final String DATA_MSG = "data_msg";

    public FacecoreActivity getFacecoreActivity(){
        return facecoreactivity;
    }
    private FacecoreActivity facecoreactivity;

    public MainActivity getActivity() {
        return activity;
    }
    private MainActivity activity;




    private   Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            FacecoreActivity facecoreActivity = new FacecoreActivity();
            FacecoreActivity activity = facecoreActivity.getActivity();
            if (msg.arg1 == ARG1_SUCCESS) {
                    String sign = msg.getData().getString(DATA_SIGN);
                    Log.d(TAG,"--------------------------------------"+sign);
                    String mode = msg.getData().getString(DATA_MODE);
                    AppHandler.this.activity.startOcrDemo(sign);
                    if (mode == DATA_MODE_REFLECT_DESENSE){
                        activity.getFaceId(FaceVerifyStatus.Mode.REFLECTION,sign);
                    }
                } else {
                    int code = msg.getData().getInt(DATA_CODE);
                    String message = msg.getData().getString(DATA_MSG);
                    WLogger.e("AppHandler", "请求失败:[" + code + "]," + message);
                      //  Toast.makeText(activity, "请求失败:[" + code + "]," + message, Toast.LENGTH_SHORT).show();
                   // Activity.hideLoading();
                    }

                }
    };

    public  AppHandler(MainActivity mainActivity) {
        this.activity = mainActivity;

    }

    /*public AppHandler(BankOcrResultActivity mainActivity) {
        this.bankOcrResultActivity = mainActivity;
    }*/


    public void sendSignError(int code, String msg) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_FAILED;
        final Bundle data = new Bundle();
        data.putInt(DATA_CODE, code);
        data.putString(DATA_MSG, msg);
        message.setData(data);
        handler.sendMessage(message);
    }

    public void sendSignSuccess(String mode, String sign) {
        Message message = new Message();
        message.what = WHAT_SIGN;
        message.arg1 = ARG1_SUCCESS;
        final Bundle data = new Bundle();
        data.putString(DATA_SIGN, sign);
        data.putString(DATA_MODE, mode);
        message.setData(data);
        handler.sendMessage(message);
    }
}

