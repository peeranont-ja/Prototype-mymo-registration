package com.example.mymoregistration2ndedition;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SummaryActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView mainBg;
    Button finishBtn;
    TextView openAccountHeader;
    TextView registerTimestamp;
    TextView watermarkDescription;
    int transactionNumber;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionNumber = bundle.getInt("transactionNumber");
        }

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        else {
            if (actionBar != null) actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mainBg = findViewById(R.id.main_bg);
        watermarkDescription = findViewById(R.id.watermark_description);
        openAccountHeader = findViewById(R.id.open_account_header);
        if (transactionNumber == 0){
//            openAccountHeader.setVisibility(View.VISIBLE);
            mainBg.setImageResource(R.drawable.background_85);
            watermarkDescription.setText("ใช้สำหรับการเปิดบัญชีเท่านั้น");
        }

        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerTimestamp = findViewById(R.id.register_timestamp);
        registerTimestamp.setText(currentDate);


        final Dialog dialog = new Dialog(SummaryActivity.this);
        dialog.setTitle("Password Dialog");
        dialog.setContentView(R.layout.dialog_staff_confirm);
        dialog.setCanceledOnTouchOutside(false);

        final Dialog dialogSMS = new Dialog(SummaryActivity.this);
        dialogSMS.setTitle("SMS Dialog");
        dialogSMS.setContentView(R.layout.dialog_send_sms);
        dialogSMS.setCanceledOnTouchOutside(false);
        dialogSMS.setCancelable(false);

        final Dialog dialogSuccess = new Dialog(SummaryActivity.this);
        dialogSuccess.setTitle("Success Dialog");
        dialogSuccess.setContentView(R.layout.dialog_success);
        dialogSuccess.setCanceledOnTouchOutside(false);
        dialogSuccess.setCancelable(false);

        Button sendPwdBtn = dialog.findViewById(R.id.btn_send_password);
        Button sendSMSBtn = dialogSMS.findViewById(R.id.btn_send_sms);
        Button backToMainMenuBtn = dialogSuccess.findViewById(R.id.btn_main_menu);
        finishBtn = findViewById(R.id.btn_finish);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        sendPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogSMS.show();
            }
        });

        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSMS.dismiss();
                dialogSuccess.show();
            }
        });

        backToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryActivity.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}
