package com.example.mrrobot.ssaattendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mrrobot.ssaattendance.Model.AttendanceModel;
import com.example.mrrobot.ssaattendance.R;

import java.util.ArrayList;

/**
 * Created by MrRobot on 1/14/2017.
 *
 * Adapter responsible to handler data for attendance list view, in check attendance xml for list
 */

public class AttendanceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<AttendanceModel> attendanceData;
    private static LayoutInflater inflater = null;

    public AttendanceAdapter(Context context, ArrayList<AttendanceModel> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.attendanceData = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return attendanceData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return attendanceData.get(position);
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
            vi = inflater.inflate(R.layout.attendance_list_row, null);
        }

        if(attendanceData != null && attendanceData.size() > position){
            TextView userName = (TextView) vi.findViewById(R.id.userName);
            userName.setText(attendanceData.get(position).getUserName());

            TextView numAttendance = (TextView)vi.findViewById(R.id.numberOfAttendance);
            String numAttend = attendanceData.get(position).getNumberOfAttendance()+"";
            numAttendance.setText(numAttend);
        }

        return vi;
    }
}
