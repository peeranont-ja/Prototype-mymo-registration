package com.example.tngp17_001.mymoregistration.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tngp17_001.mymoregistration.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakePhotoWithCardActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton cameraBtn;
    Button nextBtn;
    TextView description;
    private static final int MY_CAMERA_REQUEST_CODE = 0;
    public static final int REQUEST_CAMERA = 2;
    ImageView imageView;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        permissionCheck();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_with_card);

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
        nextBtn = findViewById(R.id.btn_next);
        cameraBtn = findViewById(R.id.btn_camera);
        imageView = findViewById(R.id.imageView1);
        description = findViewById(R.id.signature_pad_description);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TakePhotoWithCardActivity.this, SummaryActivity.class);
                startActivity(i);
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
                nextBtn.setVisibility(View.VISIBLE);
                description.setVisibility(View.INVISIBLE);

//                Toast.makeText(getApplicationContext()
//                        , uri.getPath(), Toast.LENGTH_SHORT).show();
//                nextBtn.setVisibility(View.VISIBLE);
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

}

