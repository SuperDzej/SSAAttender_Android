package com.example.mrrobot.ssaattendance.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mrrobot.ssaattendance.Adapters.DaysAttendedAdapter;
import com.example.mrrobot.ssaattendance.Model.AttendanceModel;
import com.example.mrrobot.ssaattendance.Model.KeyValueModel;
import com.example.mrrobot.ssaattendance.R;

import java.util.Calendar;

/**
 * Created by MrRobot on 1/15/2017.
 *
 * Activity that handles user attendance info and displays all information about his current attendance
 */

public class UserAttendanceInfo extends Activity {
    public  static String intentAttendance = "Attendance";

    ListView attendancePerDay;
    AttendanceModel attendance;
    TextView userName, numAttended;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.user_attendance_info);
        Intent i = getIntent();
        attendance = i.getParcelableExtra(intentAttendance);

        userName = (TextView)findViewById(R.id.userNameInfo);
        numAttended = (TextView)findViewById(R.id.attendedInfo);

        attendancePerDay = (ListView) findViewById(R.id.dayAttended);
        attendancePerDay.setAdapter(new DaysAttendedAdapter(this, attendance));
        attendancePerDay.setOnItemClickListener(attendanceRowClicked);

        userName.setText(attendance.getUserName());
        String attended = getResources().getString(R.string.attended) +"  " + attendance.getNumberOfAttendance();
        numAttended.setText(attended);
    }

    AdapterView.OnItemClickListener attendanceRowClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            KeyValueModel attend = attendance.getDaysAttended().get(i);
            Long timeLong = attend.getKey();
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(timeLong);

            String timeDescription = time.get(Calendar.DAY_OF_MONTH) + "/"+ (time.get(Calendar.MONTH)+1)
                    + "/"+ time.get(Calendar.YEAR) + "  " + time.get(Calendar.HOUR)+" : "+time.get(Calendar.MINUTE);

            Toast.makeText(getApplicationContext(), timeDescription, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public  void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(UserAttendanceInfo.this, CheckAttendance.class);
        startActivity(i);
    }
}
