package com.example.mymoregistration2ndedition;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class BindBookActivity extends AppCompatActivity {

    Button nextBtn;
    TextView bookBankResult;
    ImageButton searchBtn;
    ImageButton backBtn;

    int REQUEST_CODE = 191;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_book);

        bookBankResult = findViewById(R.id.book_bank_result);
        nextBtn = findViewById(R.id.btn_next);
        bookBankResult.setText("");
        nextBtn.setVisibility(View.INVISIBLE);


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

        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BindBookActivity.this, DepositOpenAccountActivity.class);
                startActivity(i);
            }
        });

        searchBtn = findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BindBookActivity.this, ScanBarcodeActivity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 191) {
            if (resultCode == Activity.RESULT_OK) {
                String barcodeNumber = data.getStringExtra("barcodeNumber");
                bookBankResult.setText(barcodeNumber);
                nextBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
