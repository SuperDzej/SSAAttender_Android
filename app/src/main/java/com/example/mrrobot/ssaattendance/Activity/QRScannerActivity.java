package com.example.mrrobot.ssaattendance.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import com.example.mrrobot.ssaattendance.R;
import com.example.mrrobot.ssaattendance.Retrofit.Interface.AttendanceInterface;
import com.example.mrrobot.ssaattendance.Retrofit.RetrofitApiInstance;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MrRobot on 2/12/2017.
 *
 *
 */

public class QRScannerActivity extends AppCompatActivity {

    Button qrScanner;
    String scannedUserName, trainingSelected = null;
    Spinner trainingList;
    private String[] arraySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);

        qrScanner = (Button) findViewById(R.id.scanQRCode);
        qrScanner.setOnClickListener(qrScannerClicked);

        trainingList = (Spinner)findViewById(R.id.trainingList);
        this.arraySpinner = new String[] {
                "Time Management", "Leadership", "Motivation", "Stress Management", "Meetings", "Friendship bridge"
        };
        trainingSelected = arraySpinner[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, arraySpinner);
        trainingList.setAdapter(adapter);
        trainingList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                trainingSelected = arraySpinner[position];
                Log.d("QRScannerActivity"+58, trainingSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                trainingSelected = arraySpinner[0];
            }

        });
    }


    View.OnClickListener qrScannerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentIntegrator integrator = new IntentIntegrator(QRScannerActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt(getResources().getString(R.string.scan));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.scanningCancelled), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), result.getContents(), Toast.LENGTH_LONG).show();
                scannedUserName = result.getContents();
                if(trainingSelected != null){
                    sendUserAttendanceInfo(scannedUserName, trainingSelected);
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private  void sendUserAttendanceInfo(String userName, String training){
        AttendanceInterface loginUser = RetrofitApiInstance.getRetrofit().create(AttendanceInterface.class);
        final Call<Void> call =
                loginUser.sendAttendanceInfo(userName, training);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200)
                {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.attendanceRecorded), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.canNotGetAttendanceInfo), Toast.LENGTH_LONG).show();
            }
        });
    }
}
