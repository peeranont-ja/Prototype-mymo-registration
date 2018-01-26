package com.example.mymoregistration2ndedition;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TermsAndConditionsActivity extends AppCompatActivity {

    ImageButton backBtn;
    private SignaturePad mSignaturePad;
    private TextView signAgreement;
    private LinearLayout eraser;
    private ImageButton eraserIcon;
    private TextView eraserText;
    private TextView signDescription;
    private Button mSaveButton;
    private View mSaveButtonBg;
    private TextView termsAndConditions;
    private ImageView mainBg;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int transactionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        verifyStoragePermissions(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionNumber = bundle.getInt("transactionNumber");
        }

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        final float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        final float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        final int windowsHeight = displayMetrics.heightPixels;
        final int windowsWidth = displayMetrics.widthPixels;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

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

        mainBg = findViewById(R.id.main_bg);
        if (transactionNumber == 0) {
            mainBg.setImageResource(R.drawable.background_91);
        }
        backBtn = findViewById(R.id.btn_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mSignaturePad = findViewById(R.id.signature_pad);
        signAgreement = findViewById(R.id.signature_pad_description);
        eraser = findViewById(R.id.eraser_layout);
        eraserIcon = findViewById(R.id.eraser_icon);
        eraserText = findViewById(R.id.eraser_text);
        signDescription = findViewById(R.id.sign_description);
        mSaveButton = findViewById(R.id.btn_accept);
        mSaveButtonBg = findViewById(R.id.btn_accept_bg);
        termsAndConditions = findViewById(R.id.terms_and_conditions_description);
        termsAndConditions.setText(Html.fromHtml(getString(R.string.terms_and_conditions)));

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                signDescription.setVisibility(View.INVISIBLE);
                eraser.setVisibility(View.VISIBLE);


            }

            @Override
            public void onSigned() {
                mSaveButton.setVisibility(View.VISIBLE);
                mSaveButtonBg.setVisibility(View.INVISIBLE);
                signAgreement.setVisibility(View.VISIBLE);

            }

            @Override
            public void onClear() {
                signDescription.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.INVISIBLE);
                mSaveButtonBg.setVisibility(View.VISIBLE);
                eraser.setVisibility(View.INVISIBLE);
                signAgreement.setVisibility(View.INVISIBLE);
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });
        eraserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });
        eraserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignaturePad.clear();
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getTransparentSignatureBitmap();
                if (addPngSignatureToGallery(signatureBitmap)) {
                } else {
                }
                if (addSvgSignatureToGallery(mSignaturePad.getSignatureSvg())) {
                } else {
                }

                Intent i = new Intent(TermsAndConditionsActivity.this, SummaryActivity.class);
                i.putExtra("transactionNumber", transactionNumber);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); commented this line in order to disable back press
        //Write your code here
        Toast.makeText(getApplicationContext(), "ไม่สามารถกดย้อนกลับได้", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(TermsAndConditionsActivity.this, "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToPNG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 40, stream);
        stream.close();
    }

    public boolean addPngSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.png", System.currentTimeMillis()));
            saveBitmapToPNG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        TermsAndConditionsActivity.this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
