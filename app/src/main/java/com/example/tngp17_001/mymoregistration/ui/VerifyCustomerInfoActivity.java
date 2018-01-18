package com.example.tngp17_001.mymoregistration.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tngp17_001.mymoregistration.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyCustomerInfoActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageView bg_query;
    Button btn_search;
    View bg_text;
    EditText citizen_input;
    Button cameraBtn;
    Button nextBtn;
    Button queryBtn;
    LinearLayout watermarkLayout;
    private static final int MY_CAMERA_REQUEST_CODE = 0;
    public static final int REQUEST_CAMERA = 2;
    ImageView imageView;
    Uri uri;

    TextView registerTimestamp;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        permissionCheck();
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


        bg_query = findViewById(R.id.bg_query);
        btn_search = findViewById(R.id.btn_search);
        bg_text = findViewById(R.id.bg_text);
        citizen_input = findViewById(R.id.citizen_input);
        imageView = findViewById(R.id.imageView1);
        backBtn = findViewById(R.id.btn_back);
        nextBtn = findViewById(R.id.btn_next);
        queryBtn = findViewById(R.id.btn_query);
        watermarkLayout = findViewById(R.id.watermark_layout);

        registerTimestamp = findViewById(R.id.register_timestamp);
        registerTimestamp.setText(currentDate);

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
                bg_query.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.VISIBLE);
                bg_text.setVisibility(View.VISIBLE);
                citizen_input.setVisibility(View.VISIBLE);
                citizen_input.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(citizen_input, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bg_query.setVisibility(View.GONE);
                btn_search.setVisibility(View.GONE);
                bg_text.setVisibility(View.GONE);
                citizen_input.setVisibility(View.GONE);
                hideKeyboard(v);
            }
        });
        bg_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bg_query.setVisibility(View.GONE);
                btn_search.setVisibility(View.GONE);
                bg_text.setVisibility(View.GONE);
                citizen_input.setVisibility(View.GONE);
                hideKeyboard(v);
            }
        });
        cameraBtn = findViewById(R.id.btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String timeStamp =
                        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "IMG_" + timeStamp + ".jpg";
                File f = new File(Environment.getExternalStorageDirectory()
                        , "DCIM/Camera/" + imageFileName);
                uri = Uri.fromFile(f);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(Intent.createChooser(intent
                        , "Take a picture with"), REQUEST_CAMERA);
            }
        });
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
                watermarkLayout.setVisibility(View.VISIBLE);
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
    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
