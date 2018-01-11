package com.example.tngp17_001.mymoregistration.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tngp17_001.mymoregistration.R;

public class IdQueryActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button searchBtn;
    EditText citizenInput;
    int textlength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_query);

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
        searchBtn = findViewById(R.id.btn_search);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IdQueryActivity.this, VerifyCustomerInfoActivity.class);

                startActivity(i);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        citizenInput = findViewById(R.id.citizen_input);
//
//        citizenInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = citizenInput.getText().toString();
//                textlength = citizenInput.getText().length();
//
//                if (text.endsWith(" "))
//                    return;
//
//                if (textlength == 1 || textlength == 6 || textlength == 13) {
//                    citizenInput.setText(text + "-");
//                    citizenInput.setSelection(citizenInput.getText().length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        citizenInput.addTextChangedListener( new TextWatcher() {
//            boolean isEdiging;
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(isEdiging) return;
//                isEdiging = true;
//                // removing old dashes
//                StringBuilder sb = new StringBuilder();
//                sb.append(s.toString().replace("-", ""));
//
//                if (sb.length()> 1)
//                    sb.insert(1, "-");
//                if (sb.length()> 6)
//                    sb.insert(6, "-");
//                if (sb.length()> 13)
//                    sb.insert(13, "-");
//                if (sb.length()> 15)
//                    sb.insert(15, "-");
//                if(sb.length()> 17)
//                    sb.delete(17, sb.length());
//
//                s.replace(0, s.length(), sb.toString());
//                isEdiging = false;
//            }
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//        });

    }
}
