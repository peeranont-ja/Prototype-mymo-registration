package com.example.mymoregistration2ndedition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button registerBtn;
    Button openBookBtn;
    int transactionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        openBookBtn = findViewById(R.id.btn_open_account);
        openBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionNumber = 0;
                Intent i = new Intent(MenuActivity.this, VerifyCustomerInfoActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });

        registerBtn = findViewById(R.id.btn_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionNumber = 1;
                Intent i = new Intent(MenuActivity.this, VerifyCustomerInfoActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });

    }

}
