package com.example.mymoregistration2ndedition;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SummaryActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView mainBg;
    Button finishBtn;
    Button makerBtn;
    Button checkerBtn;
    TextView openAccountHeader;
    TextView registerTimestamp;
    TextView watermarkDescription;
    CheckBox makerCheckBOx;
    CheckBox checkerCheckBox;
    int transactionNumber;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ImageView cardID_mymo = (ImageView) findViewById(R.id.id_card_mymo);
        cardID_mymo.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-CARD.jpg"));

        ImageView person_mymo = (ImageView) findViewById(R.id.person_mymo);
        person_mymo.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-PERSON.jpg"));

        ImageView signature = (ImageView) findViewById(R.id.signature);
        signature.setImageURI(Uri.parse("file:///storage/emulated/0/Pictures/SignaturePad/Signature.png"));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionNumber = bundle.getInt("transactionNumber");
        }

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

        mainBg = findViewById(R.id.main_bg);
        watermarkDescription = findViewById(R.id.watermark_description);
        openAccountHeader = findViewById(R.id.open_account_header);

        makerBtn = findViewById(R.id.maker_check_data_btn);
        checkerBtn = findViewById(R.id.checker_check_data_btn);
        finishBtn = findViewById(R.id.btn_finish);
        makerCheckBOx = findViewById(R.id.maker_checkBox);
        checkerCheckBox = findViewById(R.id.checker_checkBox);

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

        final Dialog dialogMaker = new Dialog(SummaryActivity.this);
        dialogMaker.setTitle("Maker Verification");
        dialogMaker.setContentView(R.layout.dialog_maker);
        dialogMaker.setCanceledOnTouchOutside(false);

        final Dialog dialogChecker = new Dialog(SummaryActivity.this);
        dialogChecker.setTitle("Checker Verification");
        dialogChecker.setContentView(R.layout.dialog_checker);
        dialogChecker.setCanceledOnTouchOutside(false);

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

        if (transactionNumber == 0) {
//            openAccountHeader.setVisibility(View.VISIBLE);

            cardID_mymo.setVisibility(View.GONE);
            person_mymo.setVisibility(View.GONE);



            ImageView cardID_account = (ImageView) findViewById(R.id.id_card_account);
            cardID_account.setVisibility(View.VISIBLE);
            cardID_account.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-CARD.jpg"));

            ImageView person_account = (ImageView) findViewById(R.id.person_account);
            person_account.setVisibility(View.VISIBLE);
            person_account.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-PERSON.jpg"));
            mainBg.setImageResource(R.drawable.background_85);
            watermarkDescription.setText("ใช้สำหรับการเปิดบัญชีเท่านั้น");
            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SummaryActivity.this, BindBookActivity.class);
                    startActivity(i);
                }
            });
        } else {
//            makerBtn.setVisibility(View.GONE);
//            checkerBtn.setVisibility(View.GONE);
//            finishBtn.setVisibility(View.VISIBLE);
//            makerCheckBOx.setVisibility(View.GONE);
//            checkerCheckBox.setVisibility(View.GONE);
//            finishBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.show();
//                }
//            });
            openAccountHeader.setVisibility(View.VISIBLE);
            openAccountHeader.setText("สมัคร MyMo");

            cardID_mymo.setVisibility(View.GONE);
            person_mymo.setVisibility(View.GONE);


            ImageView cardID_account = findViewById(R.id.id_card_account);
            cardID_account.setVisibility(View.VISIBLE);
            cardID_account.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-CARD.jpg"));

            ImageView person_account = findViewById(R.id.person_account);
            person_account.setVisibility(View.VISIBLE);
            person_account.setImageURI(Uri.parse("file:///storage/emulated/0/DCIM/SUMO/Image-PERSON.jpg"));
            mainBg.setImageResource(R.drawable.background_85);
            watermarkDescription.setText("ใช้สำหรับการเปิดบัญชีเท่านั้น");
            finishBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SummaryActivity.this, BindBookActivity.class);
                    startActivity(i);
                }
            });
        }

        Button sendPwdBtn = dialog.findViewById(R.id.btn_send_password);
        Button sendSMSBtn = dialogSMS.findViewById(R.id.btn_send_sms);
        Button backToMainMenuBtn = dialogSuccess.findViewById(R.id.btn_main_menu);
        Button makerVerifyBtn = dialogMaker.findViewById(R.id.btn_marker_verification);
        Button checkerVerifyBtn = dialogChecker.findViewById(R.id.btn_checker_verification);
        Button backToQueryBtn = dialogSuccess.findViewById(R.id.btn_register_mymo);
        makerBtn = findViewById(R.id.maker_check_data_btn);
        checkerBtn = findViewById(R.id.checker_check_data_btn);
        finishBtn = findViewById(R.id.btn_finish);
        makerCheckBOx = findViewById(R.id.maker_checkBox);
        checkerCheckBox = findViewById(R.id.checker_checkBox);

        makerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (makerCheckBOx.isChecked()) {
                    makerCheckBOx.setChecked(false);
                } else {
                    dialogMaker.show();
                }
            }
        });
        checkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkerCheckBox.isChecked()) {
                    checkerCheckBox.setChecked(false);
                } else {
                    dialogChecker.show();
                }
            }
        });
        makerVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogMaker.dismiss();
                makerCheckBOx.setChecked(true);

            }
        });

        checkerVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChecker.dismiss();
                checkerCheckBox.setChecked(true);
            }
        });

        makerCheckBOx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkerCheckBox.isChecked() && makerCheckBOx.isChecked()) {
                    finishBtn.setVisibility(View.VISIBLE);
                } else {
                    finishBtn.setVisibility(View.GONE);
                }
            }
        });

        checkerCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkerCheckBox.isChecked() && makerCheckBOx.isChecked()) {
                    finishBtn.setVisibility(View.VISIBLE);
                } else {
                    finishBtn.setVisibility(View.GONE);
                }
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

        backToQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SummaryActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }
    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }
}
