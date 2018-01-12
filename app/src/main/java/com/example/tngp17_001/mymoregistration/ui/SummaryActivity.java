package com.example.tngp17_001.mymoregistration.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.tngp17_001.mymoregistration.R;

public class SummaryActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("ต้องการทำรายการต่อ?")
                .content("กรุณากรอกรหัสผ่านเพื่อใช้งานต่อ")
                .positiveText("เข้าสู่ระบบ")
                .negativeText("ยกเลิก")
                .cancelable(false)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

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
        finishBtn = findViewById(R.id.btn_finish);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MaterialDialog dialog = builder.build();
//                dialog.show();
                MaterialDialog dialog = new MaterialDialog.Builder(SummaryActivity.this)
                        .title("Title")
                        .content("content")
                        .positiveText("agree")
                        .show();
            }
        });


    }
}
