package com.example.mrrobot.ssaattendance.Retrofit.Interface;

import com.example.mrrobot.ssaattendance.Model.AttendanceApiModel;
import com.example.mrrobot.ssaattendance.Model.AttendanceModel;
import com.example.mrrobot.ssaattendance.Model.NewUserModel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MrRobot on 10/25/2016.
 * Location interface for retrofit to query location based api
 */

public interface AttendanceInterface {
    @GET("api/Attendance")
    Call<ArrayList<AttendanceApiModel>> getAllAttendance();

    @POST("api/Attendance")
    Call<String> insertNewUser(
            @Header("Content-Type") String content_type,
            @Body NewUserModel userName
    );

    @GET("api/Attendance/{userName}/{training}")
    Call<Void> sendAttendanceInfo(
            @Path("userName") String userName,
            @Path("training") String training
    );
}
