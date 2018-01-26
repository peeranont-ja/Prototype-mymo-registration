package com.example.mymoregistration2ndedition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ChooseAccountActivity extends AppCompatActivity {

    Spinner accountRelationship;
    Spinner accountType;
    Spinner accountSubtype;
    ImageButton backBtn;
    TextView lowestDepositAmount;
    View hideAccountSubtypeBorder;
    Button nextBtn;
    int transactionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

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
                Intent i = new Intent(ChooseAccountActivity.this,
                        TermsAndConditionsActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });

        hideAccountSubtypeBorder = findViewById(R.id.hide_account_subtype_border);
        lowestDepositAmount = findViewById(R.id.lowest_deposit_amount);

        accountRelationship = findViewById(R.id.account_relationship);
        String[] accountRelationshipStringList = getResources().getStringArray(R.array.account_relationship);
        ArrayAdapter<String> adapterAccountRelationship = new ArrayAdapter<>(this,
                R.layout.spinner_item, accountRelationshipStringList);
        accountRelationship.setAdapter(adapterAccountRelationship);

        accountType = findViewById(R.id.account_type);
        String[] accountTypeStringList = getResources().getStringArray(R.array.account_type);
        ArrayAdapter<String> adapterAccountType = new ArrayAdapter<>(this,
                R.layout.spinner_item, accountTypeStringList);
        accountType.setAdapter(adapterAccountType);

        accountSubtype = findViewById(R.id.account_subtype);
        String[] accountSubtypeStringList = getResources().getStringArray(R.array.account_subtype_sav);
        final ArrayAdapter<String> adapterAccountSubtype = new ArrayAdapter<>(this,
                R.layout.spinner_item, accountSubtypeStringList);


        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    accountSubtype.setVisibility(View.GONE);
                    hideAccountSubtypeBorder.setVisibility(View.VISIBLE);
                    lowestDepositAmount.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.GONE);
                } else {
                    accountSubtype.setAdapter(adapterAccountSubtype);
                    accountSubtype.setVisibility(View.VISIBLE);
                    hideAccountSubtypeBorder.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        accountSubtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    lowestDepositAmount.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.GONE);
                } else {
                    lowestDepositAmount.setVisibility(View.VISIBLE);
                    nextBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
