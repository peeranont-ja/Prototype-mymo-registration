package com.example.mymoregistration2ndedition;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyCustomerInfoActivity extends AppCompatActivity {

    ImageView queryBackground;
    ImageView imageView;
    Button searchBtn;
    View textBackground;
    TextView openAccountHeader;
    EditText citizenInput;
    Button nextBtn;
    Button updateKycBtn;
    Button queryBtn;
    ImageButton backBtn;
    Spinner kycOccupation;
    Spinner kycIncome;
    Spinner kycIncomeSource;
    Spinner kycIncomeSourceCountry;
    Spinner kycEducation;
    RelativeLayout kycOccupationLayout;
    RelativeLayout kycIncomeLayout;
    RelativeLayout kycIncomeSrcLayout;
    RelativeLayout kycIncomeSrcCountryLayout;
    RelativeLayout kycEducationLayout;
    TextView kycOccupationText;
    TextView kycIncomeText;
    TextView kycIncomeSrcText;
    TextView kycIncomeSrcCountryText;
    TextView kycEducationText;


    Uri uri;
    int transactionNumber;
    boolean updateMode = false;
    private static final int MY_CAMERA_REQUEST_CODE = 0;
    public static final int REQUEST_CAMERA = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        permissionCheck();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionNumber = bundle.getInt("transactionNumber");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_customer_info);

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


        queryBackground = findViewById(R.id.bg_query);
        searchBtn = findViewById(R.id.btn_search);
        queryBtn = findViewById(R.id.btn_query);
        backBtn = findViewById(R.id.btn_back);
        nextBtn = findViewById(R.id.btn_next);
        updateKycBtn = findViewById(R.id.btn_update_kyc);
        textBackground = findViewById(R.id.bg_text);
        citizenInput = findViewById(R.id.citizen_input);
        imageView = findViewById(R.id.imageView1);
        openAccountHeader = findViewById(R.id.open_account_header);
        kycOccupationLayout = findViewById(R.id.kyc_occupation_layout);
        kycIncomeLayout = findViewById(R.id.kyc_income_layout);
        kycIncomeSrcLayout = findViewById(R.id.kyc_income_src_layout);
        kycIncomeSrcCountryLayout = findViewById(R.id.kyc_income_src_country_layout);
        kycEducationLayout = findViewById(R.id.kyc_education_layout);
        kycOccupationText = findViewById(R.id.kyc_occupation_text);
        kycIncomeText = findViewById(R.id.kyc_income_text);
        kycIncomeSrcText = findViewById(R.id.kyc_income_src_text);
        kycIncomeSrcCountryText = findViewById(R.id.kyc_income_src_country_text);
        kycEducationText = findViewById(R.id.kyc_education_text);


        if (transactionNumber == 0) {
            openAccountHeader.setVisibility(View.VISIBLE);
        }

        updateKycBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                if (updateMode == false) {
                    kycOccupationLayout.setVisibility(View.VISIBLE);
                    kycOccupationLayout.setLayoutParams(param);
                    kycIncomeLayout.setVisibility(View.VISIBLE);
                    kycIncomeLayout.setLayoutParams(param);
                    kycIncomeSrcLayout.setVisibility(View.VISIBLE);
                    kycIncomeSrcLayout.setLayoutParams(param);
                    kycIncomeSrcCountryLayout.setVisibility(View.VISIBLE);
                    kycIncomeSrcCountryLayout.setLayoutParams(param);
                    kycEducationLayout.setVisibility(View.VISIBLE);
                    kycEducationLayout.setLayoutParams(param);
                    kycOccupationText.setVisibility(View.GONE);
                    kycIncomeText.setVisibility(View.GONE);
                    kycIncomeSrcText.setVisibility(View.GONE);
                    kycIncomeSrcCountryText.setVisibility(View.GONE);
                    kycEducationText.setVisibility(View.GONE);

                    updateMode = true;
                } else {

                    param = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            0
                    );
                    kycOccupationLayout.setLayoutParams(param);
                    kycIncomeLayout.setLayoutParams(param);
                    kycIncomeSrcLayout.setLayoutParams(param);
                    kycIncomeSrcCountryLayout.setLayoutParams(param);
                    kycEducationLayout.setLayoutParams(param);
                    kycOccupationLayout.setVisibility(View.INVISIBLE);
                    kycIncomeLayout.setVisibility(View.INVISIBLE);
                    kycIncomeSrcLayout.setVisibility(View.INVISIBLE);
                    kycIncomeSrcCountryLayout.setVisibility(View.INVISIBLE);
                    kycEducationLayout.setVisibility(View.INVISIBLE);
                    kycOccupationText.setVisibility(View.VISIBLE);
                    kycIncomeText.setVisibility(View.VISIBLE);
                    kycIncomeSrcText.setVisibility(View.VISIBLE);
                    kycIncomeSrcCountryText.setVisibility(View.VISIBLE);
                    kycEducationText.setVisibility(View.VISIBLE);
                    kycEducationText.setText(kycEducation.getSelectedItem().toString());
                    kycIncomeText.setText(kycIncome.getSelectedItem().toString());
                    kycIncomeSrcText.setText(kycIncomeSource.getSelectedItem().toString());
                    kycOccupationText.setText(kycOccupation.getSelectedItem().toString());
                    kycIncomeSrcCountryText.setText(kycIncomeSourceCountry.getSelectedItem().toString());

                    updateMode = false;
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyCustomerInfoActivity.this, TakePhotoIdCardActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBackground.setVisibility(View.VISIBLE);
                searchBtn.setVisibility(View.VISIBLE);
                textBackground.setVisibility(View.VISIBLE);
                citizenInput.setVisibility(View.VISIBLE);
                citizenInput.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(citizenInput, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBackground.setVisibility(View.GONE);
                searchBtn.setVisibility(View.GONE);
                textBackground.setVisibility(View.GONE);
                citizenInput.setVisibility(View.GONE);
                hideKeyboard(v);
            }
        });
        queryBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryBackground.setVisibility(View.GONE);
                searchBtn.setVisibility(View.GONE);
                textBackground.setVisibility(View.GONE);
                citizenInput.setVisibility(View.GONE);
                hideKeyboard(v);
            }
        });

        kycOccupation = findViewById(R.id.kyc_occupation);
        String[] kycOccupationStringList = getResources().getStringArray(R.array.kyc_occupation);
        ArrayAdapter<String> adapterKycOccupation = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycOccupationStringList);
        kycOccupation.setAdapter(adapterKycOccupation);
        kycOccupation.setSelection(adapterKycOccupation.getPosition(kycOccupationText.getText().toString()));

        kycIncome = findViewById(R.id.kyc_income);
        String[] kycIncomeStringList = getResources().getStringArray(R.array.kyc_income);
        ArrayAdapter<String> adapterKycIncome = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeStringList);
        kycIncome.setAdapter(adapterKycIncome);
        kycIncome.setSelection(adapterKycIncome.getPosition(kycIncomeText.getText().toString()));

        kycIncomeSource = findViewById(R.id.kyc_income_src);
        String[] kycIncomeSourceStringList = getResources().getStringArray(R.array.kyc_income_src);
        ArrayAdapter<String> adapterKycIncomeSource = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeSourceStringList);
        kycIncomeSource.setAdapter(adapterKycIncomeSource);
        kycIncomeSource.setSelection(adapterKycIncomeSource.getPosition(
                kycIncomeSrcText.getText().toString()));

        kycIncomeSourceCountry = findViewById(R.id.kyc_income_src_country);
        String[] kycIncomeSourceCountryStringList = getResources().getStringArray(R.array.kyc_income_src_country);
        ArrayAdapter<String> adapterKycIncomeCountrySource = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeSourceCountryStringList);
        kycIncomeSourceCountry.setAdapter(adapterKycIncomeCountrySource);
        kycIncomeSourceCountry.setSelection(adapterKycIncomeCountrySource.getPosition(
                kycIncomeSrcCountryText.getText().toString()));

        kycEducation = findViewById(R.id.kyc_education);
        String[] kycEducationStringList = getResources().getStringArray(R.array.kyc_education);
        ArrayAdapter<String> adapterKycEducation = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycEducationStringList);
        kycEducation.setAdapter(adapterKycEducation);
        kycEducation.setSelection(adapterKycEducation.getPosition(
                kycEducationText.getText().toString()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            getContentResolver().notifyChange(uri, null);
            ContentResolver cr = getContentResolver();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                imageView.setImageBitmap(bitmap);
//                Toast.makeText(getApplicationContext()
//                        , uri.getPath(), Toast.LENGTH_SHORT).show();
                nextBtn.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void permissionCheck() {
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
