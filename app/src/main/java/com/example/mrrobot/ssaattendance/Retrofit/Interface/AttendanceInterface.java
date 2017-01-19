package com.example.mrrobot.ssaattendance.Retrofit.Interface;

import com.example.mrrobot.ssaattendance.Model.AttendanceApiModel;
import com.example.mrrobot.ssaattendance.Model.AttendanceModel;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MrRobot on 10/25/2016.
 * Location interface for retrofit to query location based api
 */

public interface AttendanceInterface {
    @GET("bins/pdtxb")
    Call<ArrayList<AttendanceApiModel>> getAllAttendance();

    @POST("api/Attendance")
    Call<String> insertNewUser(
            @Field("userName") String userName
    );

    @GET("api/Attendance/{userName}")
    Call<String> sendAttendanceInfo(
            @Path("userName") String userName
    );
}
