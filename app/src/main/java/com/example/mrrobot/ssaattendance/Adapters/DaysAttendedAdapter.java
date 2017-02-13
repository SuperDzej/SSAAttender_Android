package com.example.mrrobot.ssaattendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.mrrobot.ssaattendance.Model.AttendanceModel;
import com.example.mrrobot.ssaattendance.Model.KeyValueModel;
import com.example.mrrobot.ssaattendance.R;
import com.google.android.gms.vision.text.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by MrRobot on 1/15/2017.
 *
 * Adapter for list view when all days that user attended are shown
 */

public class DaysAttendedAdapter extends BaseAdapter {

    private Context context;
    private AttendanceModel attendanceData;
    private static LayoutInflater inflater = null;

    public DaysAttendedAdapter(Context context, AttendanceModel data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.attendanceData = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return attendanceData.getDaysAttended().size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return attendanceData.getDaysAttended().get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.user_attendance_row, null);
        }

        List<KeyValueModel> allAttendance
                = attendanceData.getDaysAttended();

        if(allAttendance != null && position < allAttendance.size()){
            TextView timeAttended = (TextView) vi.findViewById(R.id.userName);
            KeyValueModel attendanceInfo = allAttendance.get(position);

            Long timeLong = attendanceInfo.getKey();
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(timeLong);

            String timeDescription = time.get(Calendar.DAY_OF_MONTH) + "/"+ (time.get(Calendar.MONTH)+1)
                    + "/"+ time.get(Calendar.YEAR);

            timeAttended.setText(timeDescription);

            TextView numAttendance = (TextView)vi.findViewById(R.id.numberOfAttendance);

            Boolean numAttend = attendanceInfo.getValue();

            if (numAttend) {
                numAttendance.setText(context.getResources().getString(R.string.yes));
            } else {
                numAttendance.setText(context.getResources().getString(R.string.no));
            }
            TextView trainingAttended = (TextView)vi.findViewById(R.id.trainingAttended);
            trainingAttended.setText(attendanceInfo.getTraining());
        }

        return vi;
    }
}
