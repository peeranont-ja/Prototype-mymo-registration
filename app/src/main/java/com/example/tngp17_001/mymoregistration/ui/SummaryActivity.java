package com.example.tngp17_001.mymoregistration.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.tngp17_001.mymoregistration.R;

import static com.example.tngp17_001.mymoregistration.R.layout.dialog_send_sms;

public class SummaryActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

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

        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Dialog dialog = new Dialog(SummaryActivity.this);
        dialog.setTitle("Password Dialog");
        dialog.setContentView(R.layout.dialog_staff_confirm);

        final Dialog dialogSMS = new Dialog(SummaryActivity.this);
        dialogSMS.setTitle("SMS Dialog");
        dialogSMS.setContentView(R.layout.dialog_send_sms);

        final Dialog dialogSuccess = new Dialog(SummaryActivity.this);
        dialogSuccess.setTitle("Success Dialog");
        dialogSuccess.setContentView(R.layout.dialog_success);

        Button sendPwdBtn = dialog.findViewById(R.id.btn_send_password);
        Button sendSMSBtn = dialogSMS.findViewById(R.id.btn_send_sms);
        Button backToMainMenuBtn = dialogSuccess.findViewById(R.id.btn_main_menu);
        Button backToregisterMymo = dialogSuccess.findViewById(R.id.btn_register_mymo);

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
                Intent i = new Intent(SummaryActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        backToregisterMymo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryActivity.this, IdQueryActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
}
