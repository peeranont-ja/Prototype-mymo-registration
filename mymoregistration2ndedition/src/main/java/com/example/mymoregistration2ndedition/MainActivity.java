package com.example.mymoregistration2ndedition;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;

import rd.TDA.TDA;
import th.co.tbsp.BTDevice;
import th.co.tbsp.CardData;

public class MainActivity extends AppCompatActivity {

    TDA TDA;
    ImageButton backBtn;
    TextView readTextTime;
    TextView readPhotoTime;
    TextView cardDataText;
    EditText citizenIdInput;
    ImageView imageView;
    Button searchBtn;
    CatLoadingView catView;
    BluetoothDevice blueToothDevice;
    BTDevice device;


    private TextView etStatus = null;
    Button btnGetList;
    Button btnConnect;
    Button btnDisconnect;
    Button btnPowerOn;
    Button btnPowerOff;
    Button btnReadCard;

    private long startReadInfo, endReadInfo, startReadPhoto, endReadPhoto;
    private Handler handler = new Handler();

    byte[] Photo;
    Bitmap bPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        TDA = new TDA(this);
        catView = new CatLoadingView();
        catView.setCancelable(false);
        backBtn = findViewById(R.id.btn_back);
        searchBtn = findViewById(R.id.btn_search);
        citizenIdInput = findViewById(R.id.citizen_input);
        imageView = findViewById(R.id.imageView);
        readPhotoTime = findViewById(R.id.readPhotoTime);
        readTextTime = findViewById(R.id.readTextTime);
        cardDataText = findViewById(R.id.cardDataText);

        etStatus = findViewById(R.id.status);
        btnGetList = findViewById(R.id.getList);
        btnConnect = findViewById(R.id.connect);
        btnDisconnect = findViewById(R.id.disconnect);
        btnPowerOn = findViewById(R.id.powerOn);
        btnPowerOff = findViewById(R.id.powerOff);
        btnReadCard = findViewById(R.id.readcard);

        device = new BTDevice();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBluetoothCardReader();
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectBluetoothCardReader();
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectBluetoothCardReader();
            }
        });
        btnPowerOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                powerOnBluetoothCardReader();
            }
        });
        btnPowerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                powerOffBluetoothCardReader();
            }
        });
        btnReadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDataText.setText("");
                readPhotoTime.setText("");
                readTextTime.setText("");
                imageView.setImageBitmap(null);
                getCardDataBluetoothCardReader();
            }
        });


    }

    private void setBluetoothCardReader() {
        ArrayList<BluetoothDevice> arrayForBlueToothDevice;
        try {
            arrayForBlueToothDevice = device.getlistDevice();
            etStatus.setText("Device Name : " + arrayForBlueToothDevice.get(0).getName());
            Toast.makeText(this, "Device Name : " + arrayForBlueToothDevice.get(0).getName(),
                    Toast.LENGTH_SHORT).show();
            blueToothDevice = arrayForBlueToothDevice.get(0);
            Log.d("kuy", String.valueOf(blueToothDevice));
        } catch (Exception e) {
            Log.d("ReadCard ", "Error : " + e.getMessage());
            blueToothDevice = null;
        }
    }

    private void connectBluetoothCardReader() {
        try {
            if (device.connectDevice(blueToothDevice)) {
                etStatus.setText("Connect Device Success");
                Toast.makeText(this, "Connect Device Success", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            etStatus.setText("Connect Device Error : " + e.getMessage());
            Toast.makeText(this, "Connect Device Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void disconnectBluetoothCardReader() {
        try {
            if (device.disconnectDevice()) {
                etStatus.setText("Disconnect Device Success");
            }
        } catch (Exception e) {
            etStatus.setText("Disconnect Device Error : " + e.getMessage());
        }
    }

    private void powerOnBluetoothCardReader() {
        try {
            if (device.powerOn()) {
                etStatus.setText("Power On Device Success");
            }
        } catch (Exception e) {
            etStatus.setText("Power On Device Error : " + e.getMessage());
        }
    }

    private void powerOffBluetoothCardReader() {
        try {
            if (device.powerOff()) {
                etStatus.setText("Power Off Device Success");
            }
        } catch (Exception e) {
            etStatus.setText("Power Off Device Error : " + e.getMessage());
        }
    }

    private void getCardDataBluetoothCardReader() {
        try {
            CardData data;
            startReadInfo = System.currentTimeMillis();
            data = device.readTextOnly();
//            Log.i("ReadCard ", "All Data : " + data);
//            Log.i("ReadCard ", "Card ID : " + data.getNationalID());
//            Log.i("ReadCard ", "Name : " + data.getName());
//            Log.i("ReadCard ", "NameEn : " + data.getNameEn());
//            Log.i("ReadCard ", "Birth Date : " + data.getBirthDate());
//            Log.i("ReadCard ", "Expire Date : " + data.getExpiredDate());
//            Log.i("ReadCard ", "Issue Date : " + data.getIssueDate());
//            Log.i("ReadCard ", "Picture Number : " + data.getPictureNumber());
//            Log.i("ReadCard ", "Sex : " + data.getSex());
//            Log.i("ReadCard ", "Address : " + data.getAddress());
//            Log.i("ReadCard ", "Picture Base 64 : " + data.getBasePicture());
//            Log.i("ReadCard ", "Picture Base Byte : " + data.getBytePicture());
            String allCardData = data.getNationalID() + data.getName() + data.getNameEn() +
                    data.getBirthDate() + data.getExpiredDate() + data.getIssueDate() +
                    data.getPictureNumber() + data.getSex() + data.getAddress();
            endReadInfo = System.currentTimeMillis();
            cardDataText.setText(allCardData);
            readTextTime.setText("Start Time: " + startReadInfo +

                    "\nEnd Time: " + endReadInfo +

                    "\nTime Difference: " + (endReadInfo - startReadInfo));

            CardData data2;
            startReadPhoto = System.currentTimeMillis();
            data2 = device.readAll();
            Photo = data2.getBytePicture();
            bPhoto = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);     // Decode Byte Array to Bitmap
            handler.post(new Runnable() {
                public void run() {
                    endReadPhoto = System.currentTimeMillis();
                    imageView.setImageBitmap(bPhoto);
                    readPhotoTime.setText("Start Time: " + startReadPhoto +

                            "\nEnd Time: " + endReadPhoto +

                            "\nTime Difference: " + (endReadPhoto - startReadPhoto));

                }
            });
        } catch (Exception e) {
            etStatus.setText("Read Card Error : " + e.getMessage());
        }

    }

}

