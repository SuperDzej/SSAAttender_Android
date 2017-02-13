package com.example.mrrobot.ssaattendance.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by MrRobot on 1/15/2017.
 *
 *
 */

public class KeyValueModel implements Parcelable {
    private Long key;
    private Boolean value;
    private String training;

    public KeyValueModel(){
        key = 1484487333563L;
        value = false;
        training = "";
    }

    public KeyValueModel(Long key, Boolean value, String training){
        this.key = key;
        this.value = value;
        this.training = training;
    }

    public void setKey(Long t) { this.key = t; }
    public Long getKey() { return key; }
    public void setValue(Boolean value){this.value = value;}
    public Boolean getValue(){return  value;}
    public String getTraining(){return  training;}

    @Override
    public int describeContents() {
        return 0;
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Creator<KeyValueModel> CREATOR = new Creator<KeyValueModel>() {
        public KeyValueModel createFromParcel(Parcel in) {
            return new KeyValueModel(in);
        }

        public KeyValueModel[] newArray(int size) {
            return new KeyValueModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(key);
        if(value)
            parcel.writeInt(1);
        else
            parcel.writeInt(0);
        parcel.writeString(training);
    }

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private KeyValueModel(Parcel in) {
        key = in.readLong();
        int i = in.readInt();
        value = i == 1;
        training = in.readString();
    }
}
