package com.example.mrrobot.ssaattendance.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mrrobot.ssaattendance.R;
import com.example.mrrobot.ssaattendance.Retrofit.Interface.AttendanceInterface;
import com.example.mrrobot.ssaattendance.Retrofit.RetrofitApiInstance;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button qrScanner, checkAttendance, other;
    String scannedUserName;
    private String insertNewUserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScanner = (Button) findViewById(R.id.scanQR);
        checkAttendance = (Button) findViewById(R.id.checkAttendance);
        other = (Button)findViewById(R.id.other);

        qrScanner.setOnClickListener(qrScannerClicked);
        checkAttendance.setOnClickListener(checkAttendanceClicked);
        other.setOnClickListener(otherClicked);
    }

    View.OnClickListener qrScannerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt(getResources().getString(R.string.scan));
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    };

    View.OnClickListener checkAttendanceClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CheckAttendance.class);
            startActivity(intent);
        }
    };

    View.OnClickListener otherClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getResources().getString(R.string.addUser));

            // Set up the input
            final EditText input = new EditText(MainActivity.this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    insertNewUserName = input.getText().toString();
                    Log.d("MainActivity"+82, insertNewUserName);
                    //registerNewUserAttending(insertNewUserName);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
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
                //sendUserAttendanceInfo(scannedUserName);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private  void sendUserAttendanceInfo(String userName){
        AttendanceInterface loginUser = RetrofitApiInstance.getRetrofit().create(AttendanceInterface.class);
        final Call<String> call =
                loginUser.sendAttendanceInfo(userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    String result = response.body();
                    if(result != null){
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.attendanceRecorded), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.canNotGetAttendanceInfo), Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void registerNewUserAttending(String userName){
        AttendanceInterface loginUser = RetrofitApiInstance.getRetrofit().create(AttendanceInterface.class);
        final Call<String> call =
                loginUser.insertNewUser(userName);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200)
                {
                    String result = response.body();
                    if(result != null){
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.userAttendingClasses), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.canNotGetAttendanceInfo), Toast.LENGTH_LONG).show();
            }
        });
    }
}
