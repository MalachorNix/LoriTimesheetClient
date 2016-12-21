package com.malachornix.lori.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.malachornix.lori.R;
import com.malachornix.lori.TimeEntriesAdapter;
import com.malachornix.lori.interfaces.LoriApi;
import com.malachornix.lori.model.TimeEntry;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static LoriApi loriApi;
    private static final String MAIN_TAG = "MAIN";
    private SharedPreferences preferences;
    private static String fromDate;
    private static String tillDate;
    private TextView textView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.time_entries_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        fromDate = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        tillDate = sdf.format(calendar.getTime());

        fillScreen();
    }

    private void fillScreen() {
        preferences = getSharedPreferences("LoriApp", MODE_PRIVATE);
        String baseUrl = preferences.getString("BaseUrl", getResources().getString(R.string.apiUrl));
        String username = preferences.getString("Username", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loriApi = retrofit.create(LoriApi.class);

        String sessionId = preferences.getString("SessionId", "");
        Call<List<TimeEntry>> timeEntriesCall =
                loriApi.getTimeEntries(sessionId, username, fromDate, tillDate);
        timeEntriesCall.enqueue(new Callback<List<TimeEntry>>() {
            @Override
            public void onResponse(Call<List<TimeEntry>> call, Response<List<TimeEntry>> response) {
                List<TimeEntry> timeEntries = response.body();
                TimeEntriesAdapter adapter = new TimeEntriesAdapter(timeEntries, getApplicationContext());

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<TimeEntry>> call, Throwable t) {
                t.printStackTrace();
                Toast toast = Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void logout() {
        String sessionId = preferences.getString("SessionId", "");
        String username = preferences.getString("Username", "");

        Log.d(MAIN_TAG, "Session id from LoginActivity is: " + sessionId);
        Log.d(MAIN_TAG, "Username from LoginActivity is: " + username);

        Call<String> logoutCall = loriApi.logout(sessionId);

        logoutCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                Log.d(MAIN_TAG, "Response code is: " + statusCode);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(MAIN_TAG, t.toString());
                Toast toast = Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_calendar:
                Calendar calendar = Calendar.getInstance(Locale.FRANCE);
                DatePickerDialog dpd = DatePickerDialog.newInstance(new WeekPickerFragment(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "WeekPickerDialog");
                dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        fillScreen();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class WeekPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            Log.d(MAIN_TAG, date);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (calendar.get(Calendar.DAY_OF_WEEK) != calendar.getFirstDayOfWeek()) {
                calendar.add(Calendar.DATE, -1);
            }
            fromDate = sdf.format(calendar.getTime());
            calendar.add(Calendar.DATE, 6);
            tillDate = sdf.format(calendar.getTime());
            Log.d(MAIN_TAG, fromDate + " " + tillDate);

        }
    }


}
