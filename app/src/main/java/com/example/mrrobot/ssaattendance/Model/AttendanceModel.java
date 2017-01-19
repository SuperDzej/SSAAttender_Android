package com.example.mrrobot.ssaattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by MrRobot on 1/14/2017.
 *
 * Model of user attendance with all info related to his attendance
 */

public class AttendanceModel implements Parcelable {

    @SerializedName("userName")
    private String userName = "";
    @SerializedName("numberOfAttendance")
    private int numberOfAttendance = -1;
    @SerializedName("daysAttended")
    private List<KeyValueModel> daysAttendedInfo = new ArrayList<>();

    public AttendanceModel(){
        userName = "";
        numberOfAttendance = -1;
        daysAttendedInfo = new ArrayList<>();
    }

    public AttendanceModel(String userName, int numAttendance,
                           ArrayList<KeyValueModel> daysAttended){
        this.userName = userName;
        this.numberOfAttendance = numAttendance;
        this.daysAttendedInfo = daysAttended;
    }

    public String getUserName(){
        return userName;
    }

    public int getNumberOfAttendance(){
        return numberOfAttendance;
    }

    public List<KeyValueModel> getDaysAttended(){
        return daysAttendedInfo;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setDaysAttended(ArrayList<KeyValueModel> daysAttended){
        this.daysAttendedInfo = daysAttended;
    }

    public void setNumberOfAttendance(int numberOfAttendance){
        this.numberOfAttendance = numberOfAttendance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeInt(numberOfAttendance);
        parcel.writeList(daysAttendedInfo);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<AttendanceModel> CREATOR = new Creator<AttendanceModel>() {
        public AttendanceModel createFromParcel(Parcel in) {
            return new AttendanceModel(in);
        }

        public AttendanceModel[] newArray(int size) {
            return new AttendanceModel[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private AttendanceModel(Parcel in) {
        userName = in.readString();
        numberOfAttendance = in.readInt();
        in.readList(daysAttendedInfo, KeyValueModel.class.getClassLoader());
    }

    @Override
    public String toString(){
        return "Username: "+userName+" NumberAttended: "+numberOfAttendance + " DaysAttended: "+daysAttendedInfo.toString();
    }
}
