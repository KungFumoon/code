package com.example.brm;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.webank.mbank.ocr.WbCloudOcrSDK;
import com.webank.mbank.ocr.tools.ErrorCode;
import com.webank.normal.tools.WLogger;

import java.security.SecureRandom;

public class MainActivity extends Activity implements View.OnClickListener  {
    private String openApiAppVersion;
    private String appId;
    private Button btn_next;
    private String userId;
    private String orderNo;
    private String nonce;
    private String title;
    private SignUseCase signUseCase;
    private ProgressDialog progressDlg;
    //private int typeIdCard = 1;
    private static final String TAG = "MainActivity";

    private WbCloudOcrSDK.WBOCRTYPEMODE type =  null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_next = findViewById(R.id.but_next);
        appId = "TIDA0001";
        openApiAppVersion = "1.0.0";

        initView();
    }
    private void initView(){
        //Button enterIDCardOcrSdk = findViewById(R.id.btn_standard);
        Button positive =findViewById(R.id.but_positate);
        Button opposite =findViewById(R.id.but_opposite);
         btn_next = findViewById(R.id.but_next);
       // enterIDCardOcrSdk.setOnClickListener(this);
        positive.setOnClickListener(this);
        opposite.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        initProgress();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),FacecoreActivity.class));

            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();//typeIdCard ==
       if (id == R.id.but_positate){
           type = WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeFrontSide;
            title = "身份证正面";
        }else if (id == R.id.but_opposite){
            type = WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeBackSide;
            title = "身份证反面";    
        }
        progressDlg.show();
        userId = "ocr" + System.currentTimeMillis();
        nonce = randomAlphabetic(32);
        orderNo = "ocr_orderNo" + System.currentTimeMillis();

        getSign();

    }
    public void hideLoading() {
        if (progressDlg != null) {
            progressDlg.dismiss();
        }
    }

    private void getSign() {
        appId = "TIDA0001";
        openApiAppVersion = "1.0.0";

        AppHandler appHandler = new AppHandler(this);
        signUseCase = new SignUseCase(appHandler);
        signUseCase.execute(AppHandler.DATA_MODE_OCR, appId, userId, nonce);
    }
    public void startOcrDemo (String sign){
        //启动SDK，进入SDK界面
        Bundle data = new Bundle();
        WbCloudOcrSDK.InputData inputData = new WbCloudOcrSDK.InputData(
                orderNo,
                appId,
                openApiAppVersion,
                nonce,
                userId,
                sign,
                "ip=xxx.xxx.xxx.xxx",
                "lgt=xxx,xxx;lat=xxx.xxx");
        data.putSerializable(WbCloudOcrSDK.INPUT_DATA, inputData);
        data.putString(WbCloudOcrSDK.TITLE_BAR_COLOR, "#ffffff");
        data.putString(WbCloudOcrSDK.TITLE_BAR_CONTENT, title);
        data.putString(WbCloudOcrSDK.WATER_MASK_TEXT, "仅供本次业务使用");
        data.putLong(WbCloudOcrSDK.SCAN_TIME, 20000);
        //这项配置可选，只适用标准模式：传“2”则在标准模式下会对正反面识别进行强校验，即正反面都识别了“完成”按钮才能点击；不传或传其他则不校验
        data.putString(WbCloudOcrSDK.OCR_FLAG, "1");
        //启动SDK，进入SDK界面
        //-----------------------------
        WbCloudOcrSDK.getInstance().init(getApplicationContext(), data, new WbCloudOcrSDK.OcrLoginListener() {
            @Override
            public void onLoginSuccess() {
                //登录成功,拉起SDk页面

                if (progressDlg != null) {
                    progressDlg.dismiss();
                }
                //证件结果回调接口
                WbCloudOcrSDK.getInstance().startActivityForOcr(MainActivity.this, new WbCloudOcrSDK.IDCardScanResultListener() {
                    @Override
                    public void onFinish(String resultCode, String resultMsg) {

                        // resultCode为0，则刷脸成功；否则刷脸失败
                        if ("0".equals(resultCode)) {
                            // 登录成功  第三方应用对扫描的结果进行操作
                            Intent i;
                            WbCloudOcrSDK.WBOCRTYPEMODE modeType = WbCloudOcrSDK.getInstance().getModeType();
                            //身份证识别
                            i = new Intent(MainActivity.this, ResultActivity.class);
                            //modeType.equals(WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeNormal) ||
                            if (modeType.equals(WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeFrontSide) ||
                                    modeType.equals(WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeBackSide)) {
                                WLogger.d(TAG, "识别成功" + WbCloudOcrSDK.getInstance().getResultReturn().frontFullImageSrc);
                                WLogger.d(TAG, "识别成功" + WbCloudOcrSDK.getInstance().getResultReturn().backFullImageSrc);
                                i = new Intent(MainActivity.this, ResultActivity.class);
                            }
                            startActivity(i);
                        } else {
                            WLogger.d(TAG, "识别失败");
                        }

                    }
                }, type);
            }

            @Override
            public void onLoginFailed(String errorCode, String errorMsg) {
                WLogger.d(TAG, "onLoginFailed()");
                if (progressDlg != null) {
                    progressDlg.dismiss();
                }
                if (errorCode.equals(ErrorCode.IDOCR_LOGIN_PARAMETER_ERROR)) {
                    Toast.makeText(MainActivity.this, "传入参数有误！" + errorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "登录OCR sdk失败！" + "errorCode= " + errorCode + " ;errorMsg=" + errorMsg, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void initProgress() {
        if (progressDlg != null) {
            progressDlg.dismiss();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            progressDlg = new ProgressDialog(this);
        } else {
            progressDlg = new ProgressDialog(this);
            progressDlg.setInverseBackgroundForced(true);
        }
        progressDlg.setMessage("加载中...");
        progressDlg.setIndeterminate(true);
        progressDlg.setCanceledOnTouchOutside(false);
        progressDlg.setCancelable(true);
        progressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDlg.setCancelable(false);

    }

    private static String randomAlphabetic(int count) {
        String rst = "";
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < count; i++) {
                int randomNum = Math.abs(secureRandom.nextInt() % 52);
                if (randomNum > 26) {
                    stringBuilder.append((char) ('a' + (randomNum - 26)));
                } else {
                    stringBuilder.append((char) ('A' + randomNum));
                }
            }

            rst = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }


}