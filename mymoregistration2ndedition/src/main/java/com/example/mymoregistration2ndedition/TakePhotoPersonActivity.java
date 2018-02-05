package com.example.mymoregistration2ndedition;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.aflak.ezcam.EZCam;
import me.aflak.ezcam.EZCamCallback;


public class TakePhotoPersonActivity extends AppCompatActivity implements EZCamCallback {

    ImageButton backBtn;
    private ImageSurfaceView mImageSurfaceView;
    private EZCam cam;
    private TextureView textureView;
    private SimpleDateFormat dateFormat;

    private FrameLayout cameraPreviewLayout;
    ImageButton cameraBtn;
    ImageView imageView;
    Button nextBtn;
    TextView openAccountHeader;
    TextView takePhotoDescription;
    boolean isAlreadyTakePhoto = false;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdf.format(new Date());

    int transactionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo_person);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());

        textureView = (TextureView) findViewById(R.id.textureView);

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

        openAccountHeader = findViewById(R.id.open_account_header);
        if (transactionNumber == 0){
            openAccountHeader.setVisibility(View.VISIBLE);
        }

        cam = new EZCam(this);
        cam.setCameraCallback(this);

        String id = cam.getCamerasList().get(CameraCharacteristics.LENS_FACING_BACK);
        cam.selectCamera(id);
        cam.open(CameraDevice.TEMPLATE_PREVIEW, textureView);

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
                if (transactionNumber != 0) {
                    Intent i = new Intent(TakePhotoPersonActivity.this, TermsAndConditionsActivity.class);
                    i.putExtra("transactionNumber", transactionNumber);
                    startActivity(i);
                } else {
                    Intent i = new Intent(TakePhotoPersonActivity.this, ChooseAccountActivity.class);
                    i.putExtra("transactionNumber", transactionNumber);
                    startActivity(i);
                }
            }
        });

        imageView = findViewById(R.id.imageView1);


        cameraBtn = findViewById(R.id.btn_camera);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAlreadyTakePhoto) {
                    onCameraReady();

                    takePhotoDescription.setText(R.string.take_photo_description);
                    isAlreadyTakePhoto = false;
                    nextBtn.setVisibility(View.INVISIBLE);
                } else {
                    isAlreadyTakePhoto = true;
                    cam.takePicture();
                    takePhotoDescription.setText(R.string.retake_id_card_photo_description);
                }
            }
        });
        takePhotoDescription = findViewById(R.id.take_id_card_photo_description);
    }


    @Override
    protected void onResume() {
        super.onResume();
        nextBtn.setVisibility(View.INVISIBLE);
        takePhotoDescription.setText(R.string.take_id_card_photo_description);
        isAlreadyTakePhoto = false;

    }


    public void onCameraReady() {
        cam.setCaptureSetting(CaptureRequest.COLOR_CORRECTION_ABERRATION_MODE, CameraMetadata.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY);
        cam.startPreview();

    }


    public void onPicture(Image image) {
        cam.stopPreview();
        try {
            String filename = "image_"+dateFormat.format(new Date())+".jpg";
            File file = new File(getFilesDir(), filename);
            EZCam.saveImage(image, file);
            String filepath = file.getAbsolutePath();

            nextBtn.setVisibility(View.VISIBLE);



            Log.e("GNOOO", filepath);
// From URL
            Bitmap src = BitmapFactory.decodeFile(filepath);


            Matrix matrix = new Matrix();

            matrix.postRotate(90);
            Bitmap rotatedBitmap = Bitmap.createBitmap(src , 0, 0, src.getWidth(), src.getHeight(), matrix, true);
            int x = 1200;
            int y = 900;

            int width = ((rotatedBitmap.getWidth()/2)-(x));
            Log.e("GNOOO", String.valueOf(rotatedBitmap.getWidth()));
            Log.e("GNOOO", String.valueOf(rotatedBitmap.getHeight()));
            int height = (rotatedBitmap.getHeight()/2)-(y);
            Log.e("GNOOOA", String.valueOf(width));
            Log.e("GNOOOA", String.valueOf(height));
            int crop = (width - height) / 2;
            Bitmap cropImg = Bitmap.createBitmap(rotatedBitmap, width, height, x*2, y*2);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(cropImg,1200,900,true);
            Log.e("GNOOOP", String.valueOf(cropImg.getWidth()));
            Log.e("GNOOOP", String.valueOf(cropImg.getHeight()));



            imageView.setImageBitmap(cropImg);
            saveImage(scaledBitmap,"PERSON");


            //Intent intent = new Intent(this, TakePhotoIdCardActivity.class);
            //intent.putExtra("filepath", file.getAbsolutePath());
            //startActivity(intent);
            //finish();
        } catch (IOException e) {
            Log.e("error", e.getMessage());
        }
    }


    public void onCameraDisconnected() {
        Log.e("error", "Camera disconnected");
    }


    public void onError(String message) {
        Log.e("error", message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cam.close();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString() + "/DCIM/SUMO";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("LOAD", String.valueOf(e));
        }
    }
}