package com.example.mymoregistration2ndedition;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TakePhotoIdCardActivity extends AppCompatActivity {

    ImageButton backBtn;
    private ImageSurfaceView mImageSurfaceView;
    private Camera camera;

    private FrameLayout cameraPreviewLayout;
    ImageButton cameraBtn;
    ImageView imageView;
    Button nextBtn;
    TextView registerTimestamp;
    TextView takePhotoDescription;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());
    boolean isAlreadyTakePhoto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_id_card);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
                Intent i = new Intent(TakePhotoIdCardActivity.this,
                        VerifyCustomerInfoShowPicActivity.class);
                startActivity(i);
            }
        });

        cameraPreviewLayout = findViewById(R.id.camera_preview);
        imageView = findViewById(R.id.imageView1);
        registerTimestamp = findViewById(R.id.register_timestamp);
        registerTimestamp.setText(currentDate);
        takePhotoDescription = findViewById(R.id.take_id_card_photo_description);

        camera = checkDeviceCamera();
        camera.setDisplayOrientation(90);
        mImageSurfaceView = new ImageSurfaceView(TakePhotoIdCardActivity.this, camera);
        cameraPreviewLayout.addView(mImageSurfaceView);

        cameraBtn = findViewById(R.id.btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAlreadyTakePhoto == true) {
                    cameraPreviewLayout.removeView(mImageSurfaceView);
                    cameraPreviewLayout.addView(mImageSurfaceView);
                    takePhotoDescription.setText(R.string.take_id_card_photo_description);
                    isAlreadyTakePhoto = false;
                    nextBtn.setVisibility(View.VISIBLE);
                } else {
                    String focusMode = camera.getParameters().getFocusMode();
                    if (focusMode.equals(Camera.Parameters.FOCUS_MODE_AUTO) || focusMode.equals(Camera.Parameters.FOCUS_MODE_MACRO)) {
                        camera.autoFocus(new Camera.AutoFocusCallback() {
                            @Override
                            public void onAutoFocus(boolean success, Camera camera) {
                                camera.takePicture(null, null, pictureCallback);
                            }
                        });
                    } else {
                        camera.takePicture(null, null, pictureCallback);
                    }
                    takePhotoDescription.setText(R.string.retake_id_card_photo_description);
                    isAlreadyTakePhoto = true;
                }
            }
        });


    }

    private Camera checkDeviceCamera() {
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCamera;
    }


    Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            if (bitmap == null) {
                Toast.makeText(TakePhotoIdCardActivity.this, "Captured image is empty", Toast.LENGTH_LONG).show();
                return;
            }
            imageView.setImageBitmap(scaleDownBitmapImage(bitmap, 642, 447));
            nextBtn.setVisibility(View.VISIBLE);
        }
    };

    private Bitmap scaleDownBitmapImage(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
        return resizedBitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nextBtn.setVisibility(View.INVISIBLE);
        takePhotoDescription.setText(R.string.take_id_card_photo_description);
        isAlreadyTakePhoto = false;

    }


}
