package com.example.mrrobot.ssaattendance.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
    ImageView backToMain;
    ArrayList<AttendanceModel> attendances = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance);

        getAttendanceInfo();

        backToMain = (ImageView) findViewById(R.id.backToMainActivity);
        //attendances = generateAttendance();

        attendanceList = (ListView) findViewById(R.id.attendanceList);
        setAdapter(attendances);
        attendanceList.setOnItemClickListener(attendanceRowClicked);

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckAttendance.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
                keyValueModels.add(new KeyValueModel(model.getDaysAttended().get(i),
                        model.getDidAttended().get(i)));
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

    private ArrayList<AttendanceModel> generateAttendance(){
        /*ArrayList<AttendanceModel> attendances = new ArrayList<>();
        Long day1 = Calendar.getInstance().getTimeInMillis();
        Long day2 = Calendar.getInstance().getTimeInMillis();
        Long day3 = Calendar.getInstance().getTimeInMillis();
        Long day4 = Calendar.getInstance().getTimeInMillis();

        Boolean attended1 = true;
        Boolean attended2 = false;
        Boolean attended3 = true;
        Boolean attended4 = true;

        ArrayList<KeyValueModel> allAttendance1 = new ArrayList<>();
        allAttendance1.add(new KeyValueModel(day1,attended1));
        allAttendance1.add(new KeyValueModel(day2,attended2));
        allAttendance1.add(new KeyValueModel(day3,attended3));

        ArrayList<KeyValueModel> allAttendance2 = new ArrayList<>();
        allAttendance2.add(new KeyValueModel(day1,attended1));
        allAttendance2.add(new KeyValueModel(day2,attended2));
        allAttendance2.add(new KeyValueModel(day3,attended3));
        allAttendance2.add(new KeyValueModel(day4,attended4));

        attendances.add(new AttendanceModel("Dzejson Megrao", 2, allAttendance1));
        attendances.add(new AttendanceModel("Ismar Music", 2, allAttendance1));
        attendances.add(new AttendanceModel("Dzoni Simic", 2, allAttendance1));
        attendances.add(new AttendanceModel("Nejra Muhic", 3, allAttendance2));
        attendances.add(new AttendanceModel("Rijad Dizdarevic", 3, allAttendance2));
        return attendances;*/
        return null;
    }
}
