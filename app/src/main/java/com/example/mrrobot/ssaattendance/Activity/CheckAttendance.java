package com.example.mrrobot.ssaattendance.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mrrobot.ssaattendance.Adapters.AttendanceAdapter;
import com.example.mrrobot.ssaattendance.Model.AttendanceApiModel;
import com.example.mrrobot.ssaattendance.Model.AttendanceModel;
import com.example.mrrobot.ssaattendance.Model.KeyValueModel;
import com.example.mrrobot.ssaattendance.R;
import com.example.mrrobot.ssaattendance.Retrofit.Interface.AttendanceInterface;
import com.example.mrrobot.ssaattendance.Retrofit.RetrofitApiInstance;
import com.google.gson.Gson;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MrRobot on 1/14/2017.
 *
 * Activity that will handle rendering of all users and their attendance
 */

public class CheckAttendance extends Activity {
    ListView attendanceList;
    ArrayList<AttendanceModel> attendances = new ArrayList<>();
    TextView noAttendanceReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance);

        getAttendanceInfo();

        //attendances = generateAttendance();

        attendanceList = (ListView) findViewById(R.id.attendanceList);
        setAdapter(attendances);
        attendanceList.setOnItemClickListener(attendanceRowClicked);
        noAttendanceReg = (TextView)findViewById(R.id.noAttendanceReg);
    }

    AdapterView.OnItemClickListener attendanceRowClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(CheckAttendance.this, UserAttendanceInfo.class);
            intent.putExtra(UserAttendanceInfo.intentAttendance, attendances.get(i));
            startActivity(intent);
        }
    };

    private  void getAttendanceInfo(){
        AttendanceInterface loginUser = RetrofitApiInstance.getRetrofit().create(AttendanceInterface.class);
        final Call<ArrayList<AttendanceApiModel>> call =
                loginUser.getAllAttendance();

        call.enqueue(new Callback<ArrayList<AttendanceApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AttendanceApiModel>> call, Response<ArrayList<AttendanceApiModel>> response) {
                if(response.code() == 200)
                {
                    ArrayList<AttendanceApiModel> result = response.body();
                    if(result != null){

                        if(parseApiToAppModel(result)){
                            setAdapter(attendances);
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    getResources().getString(R.string.invalidData), Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        noAttendanceReg.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<AttendanceApiModel>> call, Throwable t) {
                Log.d(CheckAttendance.class.getSimpleName(), t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.canNotGetAttendanceInfo), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapter(ArrayList<AttendanceModel> attendances){
        attendanceList.setAdapter(new AttendanceAdapter(this, attendances));
    }

    private boolean parseApiToAppModel(ArrayList<AttendanceApiModel> result){
        for(AttendanceApiModel model : result){
            Log.d(CheckAttendance.class.getSimpleName()+110, model.toString());
            AttendanceModel attendanceModel = new AttendanceModel();
            ArrayList<KeyValueModel> keyValueModels = new ArrayList<>();
            if(model.getDaysAttended().size() != model.getDidAttended().size())
                return false;

            for(int i = 0;i < model.getDaysAttended().size();i++){
                keyValueModels.add(new KeyValueModel(model.getDaysAttended() != null ? model.getDaysAttended().get(i):0,
                        model.getDidAttended()  != null ? model.getDidAttended().get(i):false,
                        model.getTrainingAttended()  != null ? model.getTrainingAttended().get(i):"No data"));
            }
            //Setting all values
            attendanceModel.setDaysAttended(keyValueModels);
            attendanceModel.setNumberOfAttendance(model.getNumberOfAttendance());
            attendanceModel.setUserName(model.getUserName());

            attendances.add(attendanceModel);
        }
        Log.d(CheckAttendance.class.getSimpleName()+85, attendances.toString());
        return true;
    }

    @Override
    public  void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(CheckAttendance.this, MainActivity.class);
        startActivity(i);
    }
}
