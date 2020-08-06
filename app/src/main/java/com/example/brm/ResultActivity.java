package com.example.brm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webank.mbank.ocr.WbCloudOcrSDK;
import com.webank.mbank.ocr.net.EXIDCardResult;

public class ResultActivity extends Activity implements View.OnClickListener {

    private TextView name,idNo;
    private EXIDCardResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //初始化控件
        initViews();
        init();
    }

    private void initViews() {
        name = findViewById(R.id.user_name);
        idNo = findViewById(R.id.user_idNo);


    }

    private void init() {
        result = WbCloudOcrSDK.getInstance().getResultReturn();
        if (result.type == 1) {
            this.name.setText(result.name);
            this.idNo.setText(result.cardNum);
        }  else if (result.type == 0) {
            this.name.setText(result.name);
            this.idNo.setText(result.cardNum);

        }
    }

    @Override
    public void onClick(View v) {

            if (WbCloudOcrSDK.getInstance().getModeType() != WbCloudOcrSDK.WBOCRTYPEMODE.WBOCRSDKTypeNormal) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        if (v.getId() == R.id.finish_bt) {
            //调用跳转的接口方法
            finish();

        }
    }
}

