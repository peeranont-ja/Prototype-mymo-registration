package com.example.mymoregistration2ndedition;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mymoregistration2ndedition.DeviceUtils.NumberTextWatcher;

public class DepositOpenAccountActivity extends AppCompatActivity {

    Button nextBtn;
    ImageButton backBtn;
    TextView successHeader;
    TextView successContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_open_account);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
        else {
            if (actionBar != null) actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Dialog dialog = new Dialog(DepositOpenAccountActivity.this);
        dialog.setTitle("Password Dialog");
        dialog.setContentView(R.layout.dialog_staff_confirm);
        dialog.setCanceledOnTouchOutside(false);

        final Dialog dialogOpenAccountSuccess = new Dialog(DepositOpenAccountActivity.this);
        dialogOpenAccountSuccess.setTitle("Open Account Success Dialog");
        dialogOpenAccountSuccess.setContentView(R.layout.dialog_open_account_success);
        dialogOpenAccountSuccess.setCanceledOnTouchOutside(false);
        dialogOpenAccountSuccess.setCancelable(false);

        final Dialog dialogOpenAccountSendSMS = new Dialog(DepositOpenAccountActivity.this);
        dialogOpenAccountSendSMS.setTitle("Send SMS Dialog");
        dialogOpenAccountSendSMS.setContentView(R.layout.dialog_open_account_send_sms);
        dialogOpenAccountSendSMS.setCanceledOnTouchOutside(false);
        dialogOpenAccountSendSMS.setCancelable(false);

        final Dialog dialogSuccess = new Dialog(DepositOpenAccountActivity.this);
        dialogSuccess.setTitle("Complete Dialog");
        dialogSuccess.setContentView(R.layout.dialog_success);
        dialogSuccess.setCanceledOnTouchOutside(false);
        dialogSuccess.setCancelable(false);

        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn = findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        Button sendPwdBtn = dialog.findViewById(R.id.btn_send_password);
        Button sendSMSBtn = dialogOpenAccountSendSMS.findViewById(R.id.btn_send_sms);
        Button finishBtn = dialogOpenAccountSuccess.findViewById(R.id.btn_finish);
        final TextView depositAmount = dialogOpenAccountSuccess.findViewById(R.id.deposit_amount);
        Button backToMainMenuBtn = dialogSuccess.findViewById(R.id.btn_main_menu);
        Button backToQueryBtn = dialogSuccess.findViewById(R.id.btn_register_mymo);
        successHeader = dialogSuccess.findViewById(R.id.text_success_header);
        successContent = dialogSuccess.findViewById(R.id.text_success_content);

        final EditText depositAmountInput = findViewById(R.id.deposit_amount_input);
        depositAmountInput.addTextChangedListener(new NumberTextWatcher(depositAmountInput, "#,###"));

        sendPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogOpenAccountSuccess.show();
                depositAmount.setText("จำนวนเงิน " + depositAmountInput.getText() + " บาท");
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpenAccountSuccess.dismiss();
                dialogOpenAccountSendSMS.show();

            }
        });

        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOpenAccountSendSMS.dismiss();
                successHeader.setText("เปิดบัญชีเรียบร้อยแล้ว");
                successContent.setText("โปรดตรวจสอบว่าเลขที่บัญชี\n" +
                        "ถูกส่งไปถึงผู้เปิดใช้บริการแล้ว");
                dialogSuccess.show();
            }
        });

        backToMainMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DepositOpenAccountActivity.this, MenuActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        backToQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DepositOpenAccountActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}
