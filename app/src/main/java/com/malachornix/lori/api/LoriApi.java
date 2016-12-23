package com.malachornix.lori.api;

import com.malachornix.lori.model.Project;
import com.malachornix.lori.model.Task;
import com.malachornix.lori.model.TimeEntry;
import com.malachornix.lori.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoriApi {

    @GET("login?")
    Call<String> login(@Query("u") String username, @Query("p") String password);

    @GET("logout?")
    Call<String> logout(@Query("session") String sessionId);

    @GET("query.json?e=sec$User&q=select+te+from+sec$User+te")
    Call<List<User>> getUsers(@Query("s") String sessionId);

    @GET("query.json?e=sec$User&q=select+te+from+sec$User+te+where+te.login=:login")
    Call<List<User>> getUserByLogin(@Query("s") String sessionId, @Query("login") String login);

    @GET("query.json?e=ts$TimeEntry&q=select+te+from+ts$TimeEntry+te+where+te.createdBy=:name+and+te.date+between+:from+and+:to")
    Call<List<TimeEntry>> getTimeEntries(@Query("s") String sessionId,
                                         @Query("name") String username,
                                         @Query("from") String fromDate,
                                         @Query("to") String tillDate);

    @POST("commit?")
    @Headers("Content-Type: application/json")
    Call<String> commitEntity(@Query("s") String sessionId,
                              @Body String body);

    @GET("query.json?e=ts$Project&q=select+te+from+ts$Project+te")
    Call<List<Project>> getProjects(@Query("s") String sessionId);

    @GET("query.json?e=ts$Task&q=select+te+from+ts$Task+te&view=_minimal")
    Call<List<Task>> getTasks(@Query("s") String sessionId);
}
