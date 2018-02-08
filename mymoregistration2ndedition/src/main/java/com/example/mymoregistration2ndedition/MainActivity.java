package com.example.mymoregistration2ndedition;

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

import com.roger.catloadinglibrary.CatLoadingView;

import rd.TDA.TDA;

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
    CheckReaderStatusTask checkCardReaderStatus;
    startReaderService startReaderService;

    private long startReadInfo, endReadInfo, startReadPhoto, endReadPhoto;
    private long timeDiffReadInfo, timeDiffReadPhoto;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, MenuActivity.class);
//                startActivity(i);
                readPhotoTime.setText("");
                readTextTime.setText("");
                cardDataText.setText("");
                imageView.setImageBitmap(null);
                startReadInfo = 0;
                endReadInfo = 0;
                startReadPhoto = 0;
                endReadPhoto = 0;
                Thread thread = new Thread(new Runnable() {
                    byte[] Photo;
                    Bitmap bPhoto;
                    String Data;

                    @Override
                    public void run() {

                        //clear Screen
                        handler.post(new Runnable() {
                            public void run() {
                                citizenIdInput.setText("");                     //Clear Data Text  on Screen
                                imageView.setImageBitmap(null);         //Clear Photo on Screen
                            }
                        });

                        //Read Text from NID card
                        startReadInfo = System.currentTimeMillis();
                        Data = TDA.nidTextTA("0");                      //ReadText
                        if (Data.compareTo("-2") == 0) {                //Check if un-registered reader
                            TDA.serviceTA("2");                         //Update license file
                            Data = TDA.nidTextTA("0");                  //Read Text Again
//                            Data = TDA.nidNumberTA("0");
                        }

                        handler.post(new Runnable() {
                            public void run() {
                                endReadInfo = System.currentTimeMillis();
                                citizenIdInput.setText(Data); //Set Data Text on Screen
                                cardDataText.setText("Card Data: " + Data);
                                readTextTime.setText("Start Time: " + startReadInfo +
                                        "\nEnd Time: " + endReadInfo +
                                        "\nTime Difference: " + (endReadInfo - startReadInfo));
                            }
                        });

                        //Read Photo from NID card
                        startReadPhoto = System.currentTimeMillis();
                        Photo = TDA.nidPhotoTA("0");                     //Read Photo
                        bPhoto = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);     // Decode Byte Array to Bitmap
                        handler.post(new Runnable() {
                            public void run() {
                                endReadPhoto = System.currentTimeMillis();
                                imageView.setImageBitmap(bPhoto);       //set Bitmap on Screen
                                readPhotoTime.setText("Start Time: " + startReadPhoto +
                                        "\nEnd Time: " + endReadPhoto +
                                        "\nTime Difference: " + (endReadPhoto - startReadPhoto));
                            }
                        });
                    }
                });
                thread.start();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startReaderService = new startReaderService();
        startReaderService.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        citizenIdInput.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        checkCardReaderStatus = new CheckReaderStatusTask();
//        checkCardReaderStatus.execute();
    }


    private void startProcess() {
        TDA.serviceTA("0");                                             //Close previous service if exist
        while (TDA.serviceTA("9").compareTo("00") != 0) ;                //Wait until service closed
        TDA.serviceTA("1,MyMo Registration 2nd Edition");                                   //Start TDAService with “MyMo Registration 2nd Edition”
        while (TDA.serviceTA("9").compareTo("01") != 0)
            ;                //Wait until service started

        //Check license file
        String check = TDA.infoTA("4");                                 //Test Command
        Log.i("Check", "check = " + check);                             //Print Log

        /************************** check recieve data is Error Code **************************/
        // -2 = INVALID LICENSE
        // -12 = LICENSE FILE ERROR
        if (check.compareTo("-2") == 0 || check.compareTo("-12") == 0) {
            if (isOnline()) {                                           //Method Check Internet
                TDA.serviceTA("2");                                     //Update license file
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);     //initail Object Connect Manager
        NetworkInfo netInfo = cm.getActiveNetworkInfo();                //get Network Info
        return netInfo != null && netInfo.isConnectedOrConnecting();    //Check Network and Return
    }

//    private void searchBluetooth() {
//        String result = TDA.readerTA("2");                              //Auto scan Bluetooth reader
//        if (result.compareTo("02") == 0) {                              //Check Result //02 = Card Present
////            Toast.makeText(this, "Search Blutooth", Toast.LENGTH_SHORT).show();     //Show balloon
//        }
//
//    }

    private class CheckReaderStatusTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            while (TDA.infoTA("3").compareTo("20") != 0) ;
            {
                if (!catView.isAdded()) {
                    catView.show(getSupportFragmentManager(), "");
                }
            }
            return null;
        }

        protected void onPostExecute(Void param) {
//            Thread thread = new Thread(new Runnable() {
//                byte[] Photo;
//                Bitmap bPhoto;
//                String Data;
//
//                @Override
//                public void run() {
//
//                    //clear Screen
//                    handler.post(new Runnable() {
//                        public void run() {
//                            citizenIdInput.setText("");                     //Clear Data Text  on Screen
//                            imageView.setImageBitmap(null);         //Clear Photo on Screen
//                        }
//                    });
//
//                    //Read Text from NID card
//                    startReadInfo = System.currentTimeMillis();
//                    Data = TDA.nidTextTA("0");                      //ReadText
//                    if (Data.compareTo("-2") == 0) {                //Check if un-registered reader
//                        TDA.serviceTA("2");                         //Update license file
//                        Data = TDA.nidTextTA("0");                  //Read Text Again
////                            Data = TDA.nidNumberTA("0");
//                    }
//
//                    handler.post(new Runnable() {
//                        public void run() {
//                            endReadInfo = System.currentTimeMillis();
//                            catView.dismiss();
//                            citizenIdInput.setText(Data); //Set Data Text on Screen
//                            cardDataText.setText("Card Data: " + Data);
//                            readTextTime.setText("Start Time: " + startReadInfo +
//                                    "\nEnd Time: " + endReadInfo +
//                                    "\nTime Difference: " + (endReadInfo-startReadInfo));
//                        }
//                    });
//
//                    //Read Photo from NID card
//                    startReadPhoto = System.currentTimeMillis();
//                    Photo = TDA.nidPhotoTA("0");                     //Read Photo
//                    bPhoto = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);     // Decode Byte Array to Bitmap
//                    handler.post(new Runnable() {
//                        public void run() {
//                            endReadPhoto = System.currentTimeMillis();
//                            imageView.setImageBitmap(bPhoto);       //set Bitmap on Screen
//                            readPhotoTime.setText("Start Time: " + startReadPhoto +
//                                    "\nEnd Time: " + endReadPhoto +
//                                    "\nTime Difference: " + (endReadPhoto-startReadPhoto));
//                        }
//                    });
//                }
//            });
//            thread.start();
        }
    }

    private class startReaderService extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            startProcess(); //Method Init App
//            searchBluetooth();
            return null;
        }
    }
}

