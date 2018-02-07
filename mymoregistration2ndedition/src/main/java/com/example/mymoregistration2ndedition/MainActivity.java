package com.example.mymoregistration2ndedition;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.roger.catloadinglibrary.CatLoadingView;

import rd.TDA.TDA;

public class MainActivity extends AppCompatActivity {

    TDA TDA;
    ImageButton backBtn;
    EditText citizenIdInput;
    ImageView imageView;
    Button searchBtn;
    CatLoadingView catView;

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
        backBtn = findViewById(R.id.btn_back);
        searchBtn = findViewById(R.id.btn_search);
        citizenIdInput = findViewById(R.id.citizen_input);
        imageView = findViewById(R.id.imageView);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        citizenIdInput.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckReaderStatusTask checkCardReaderStatus = new CheckReaderStatusTask();
        checkCardReaderStatus.execute();
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
//            Toast.makeText(this, "Search Blutooth", Toast.LENGTH_SHORT).show();     //Show balloon
//        }
//
//    }

    private class CheckReaderStatusTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            startProcess(); //Method Init App
            catView.show(getSupportFragmentManager(), "");
            catView.setCancelable(false);
            catView.setCanceledOnTouchOutside(false);
//          searchBluetooth();
            while (TDA.infoTA("3").compareTo("20") != 0);
            return null;
        }

        protected void onPostExecute(Void param) {
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
                    Data = TDA.nidTextTA("0");                      //ReadText
                    if (Data.compareTo("-2") == 0) {                //Check if un-registered reader
                        TDA.serviceTA("2");                         //Update license file
                        Data = TDA.nidTextTA("0");                  //Read Text Again
//                            Data = TDA.nidNumberTA("0");
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            catView.dismiss();
                            citizenIdInput.setText(Data);                   //Set Data Text on Screen
                            Intent i = new Intent(MainActivity.this, MenuActivity.class);
                            i.putExtra("cardData", Data);
                            startActivity(i);
                        }
                    });

                    //Read Photo from NID card
//                        Photo = TDA.nidPhotoTA("0");                     //Read Photo
//                        bPhoto = BitmapFactory.decodeByteArray(Photo, 0, Photo.length);     // Decode Byte Array to Bitmap
//                        handler.post(new Runnable() {
//                            public void run() {
//                                imageView.setImageBitmap(bPhoto);       //set Bitmap on Screen
//                            }
//                        });
                }
            });
            thread.start();
        }
    }
}

