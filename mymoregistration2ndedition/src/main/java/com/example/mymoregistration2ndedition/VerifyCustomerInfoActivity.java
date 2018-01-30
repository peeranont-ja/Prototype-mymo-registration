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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyCustomerInfoActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView queryBackground;
    Button searchBtn;
    View textBackground;
    TextView openAccountHeader;
    EditText citizenInput;
    Button cameraBtn;
    Button nextBtn;
    Button queryBtn;
    Spinner kycOccupation;
    Spinner kycIncome;
    Spinner kycIncomeSource;
    Spinner kycIncomeSourceCountry;
    Spinner kycEducation;
    private static final int MY_CAMERA_REQUEST_CODE = 0;
    public static final int REQUEST_CAMERA = 2;
    ImageView imageView;
    Uri uri;
    int transactionNumber;


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


        queryBackground = findViewById(R.id.bg_query);
        searchBtn = findViewById(R.id.btn_search);
        textBackground = findViewById(R.id.bg_text);
        citizenInput = findViewById(R.id.citizen_input);
        imageView = findViewById(R.id.imageView1);
        backBtn = findViewById(R.id.btn_back);
        nextBtn = findViewById(R.id.btn_next);
        queryBtn = findViewById(R.id.btn_query);
        openAccountHeader = findViewById(R.id.open_account_header);
        if (transactionNumber == 0) {
            openAccountHeader.setVisibility(View.VISIBLE);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyCustomerInfoActivity.this, TermsAndConditionsActivity.class);
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
        cameraBtn = findViewById(R.id.btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VerifyCustomerInfoActivity.this, TakePhotoIdCardActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });

        kycOccupation = findViewById(R.id.kyc_occupation);
        String[] kycOccupationStringList = getResources().getStringArray(R.array.kyc_occupation);
        ArrayAdapter<String> adapterKycOccupation = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycOccupationStringList);
        kycOccupation.setAdapter(adapterKycOccupation);

        kycIncome = findViewById(R.id.kyc_income);
        String[] kycIncomeStringList = getResources().getStringArray(R.array.kyc_income);
        ArrayAdapter<String> adapterKycIncome = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeStringList);
        kycIncome.setAdapter(adapterKycIncome);

        kycIncomeSource = findViewById(R.id.kyc_income_src);
        String[] kycIncomeSourceStringList = getResources().getStringArray(R.array.kyc_income_src);
        ArrayAdapter<String> adapterKycIncomeSource = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeSourceStringList);
        kycIncomeSource.setAdapter(adapterKycIncomeSource);

        kycIncomeSourceCountry = findViewById(R.id.kyc_income_src_country);
        String[] kycIncomeSourceCountryStringList = getResources().getStringArray(R.array.kyc_income_src_country);
        ArrayAdapter<String> adapterKycIncomeCountrySource = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycIncomeSourceCountryStringList);
        kycIncomeSourceCountry.setAdapter(adapterKycIncomeCountrySource);

        kycEducation = findViewById(R.id.kyc_education);
        String[] kycEducationStringList = getResources().getStringArray(R.array.kyc_education);
        ArrayAdapter<String> adapterKycEducation = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, kycEducationStringList);
        kycEducation.setAdapter(adapterKycEducation);
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
