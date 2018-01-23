package com.example.mymoregistration2ndedition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ChooseAccountActivity extends AppCompatActivity {

    Spinner accountRelationship;
    Spinner accountType;
    Spinner accountSubtype;
    int onItemselected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            transactionNumber = bundle.getInt("transactionNumber");
//        }

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
        ArrayAdapter<String> adapterAccountSubtype = new ArrayAdapter<>(this,
                R.layout.spinner_item, accountSubtypeStringList);
        accountSubtype.setAdapter(adapterAccountSubtype);

        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(onItemselected == 0){
                    accountSubtype.setVisibility(View.INVISIBLE);
                }
                else {
                    accountSubtype.setVisibility(View.VISIBLE);
                }
                onItemselected++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                accountSubtype.setVisibility(View.VISIBLE);
            }
        });
    }
}
