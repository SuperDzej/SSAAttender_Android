package com.example.mrrobot.ssaattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrRobot on 1/15/2017.
 *
 * Class that corresponds to model that is sent from api
 */

public class AttendanceApiModel implements Parcelable {
    @SerializedName("userName")
    private String userName = "";
    @SerializedName("numberOfAttendance")
    private int numberOfAttendance = -1;
    @SerializedName("daysAttended")
    private List<Long> daysAttendedInfo = new ArrayList<>();
    @SerializedName("daysDidAttend")
    private List<Boolean> didAttended = new ArrayList<>();

    public AttendanceApiModel(){
        userName = "";
        numberOfAttendance = -1;
        daysAttendedInfo = new ArrayList<>();
        didAttended = new ArrayList<>();
    }

    public AttendanceApiModel(String userName, int numAttendance,
                           ArrayList<Long> daysAttended, ArrayList<Boolean> didAttended){
        this.userName = userName;
        this.numberOfAttendance = numAttendance;
        this.daysAttendedInfo = daysAttended;
        this.didAttended = didAttended;
    }

    public String getUserName(){
        return userName;
    }

    public int getNumberOfAttendance(){
        return numberOfAttendance;
    }

    public List<Long> getDaysAttended(){
        return daysAttendedInfo;
    }

    public List<Boolean> getDidAttended(){
        return didAttended;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setDaysAttended(ArrayList<Long> daysAttended){
        this.daysAttendedInfo = daysAttended;
    }

    public void setNumberOfAttendance(int numberOfAttendance){
        this.numberOfAttendance = numberOfAttendance;
    }

    public void setDidAttended(ArrayList<Boolean> daysAttended){
        this.didAttended = daysAttended;
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
        parcel.writeList(didAttended);
    }

    // this is used to regenerate your object. All AttendanceApiModel must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<AttendanceApiModel> CREATOR = new Parcelable.Creator<AttendanceApiModel>() {
        public AttendanceApiModel createFromParcel(Parcel in) {
            return new AttendanceApiModel(in);
        }

        public AttendanceApiModel[] newArray(int size) {
            return new AttendanceApiModel[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private AttendanceApiModel(Parcel in) {
        userName = in.readString();
        numberOfAttendance = in.readInt();
        in.readList(daysAttendedInfo, Long.class.getClassLoader());
        in.readList(didAttended, Boolean.class.getClassLoader());
    }

    @Override
    public String toString(){
        return "Username: "+userName+" NumberAttended: "+numberOfAttendance + " DaysAttended: "+daysAttendedInfo.toString()
                +" DidAttend: "+didAttended.toString();
    }
}
