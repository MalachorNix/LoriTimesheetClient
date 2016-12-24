package com.malachornix.lori.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.malachornix.lori.R;
import com.malachornix.lori.api.LoriApi;
import com.malachornix.lori.model.Project;
import com.malachornix.lori.model.Task;
import com.malachornix.lori.model.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TimeEntryActivity extends AppCompatActivity {

    private Spinner projectSpinner;
    private Spinner tasksByProjectSpinner;
    private SharedPreferences preferences;
    private LoriApi loriApi;
    private int projectPosition;
    private int taskPosition;

    private static final String TIME_ENTRY_TAG = "TIME_ENTRY_TAG";
    private List<String> projectNames = new ArrayList<>();
    private List<String> tasksNames = new ArrayList<>();
    private List<Task> tasks;

    private static Calendar date;
    private static String time;
    private Button dateButton;
    private Button timeButton;
    private Button saveButton;
    private EditText dateText;
    private EditText timeText;
    private String strDate;
    private String taskId;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_entry);

        preferences = getSharedPreferences("LoriApp", MODE_PRIVATE);

        String baseUrl = preferences.getString("BaseUrl", getResources().getString(R.string.apiUrl));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loriApi = retrofit.create(LoriApi.class);

        projectSpinner = (Spinner) findViewById(R.id.spinner_project);
        tasksByProjectSpinner = (Spinner) findViewById(R.id.spinner_task);

        dateButton = (Button) findViewById(R.id.te_date_button);
        timeButton = (Button) findViewById(R.id.te_time_button);
        saveButton = (Button) findViewById(R.id.save_time_entry);


        dateText = (EditText) findViewById(R.id.te_date);
        timeText = (EditText) findViewById(R.id.time_entry_time_text);

        setUserId();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerFragment(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "DatePickerDialog");
                dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (date != null) {
                            strDate = sdf.format(date.getTime());
                            dateText.setText(strDate);
                        }
                    }
                });
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerFragment(), 0, 0, true);
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.show(getFragmentManager(), "TimePickerDialog");
                tpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        timeText.setText(time);
                    }
                });
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String strDate = dateText.getText().toString();
                String strTime = timeText.getText().toString();
                try {
                    dateFormat.parse(strDate);
                    timeFormat.parse(strTime);
                    saveTimeEntry();
                } catch (ParseException e) {
                    showDateTimeParseError();
                    e.printStackTrace();
                    Log.d(TIME_ENTRY_TAG, "Date or time is not valid. Date: " + strDate + " Time: " + strTime);
                }
            }


        });

        fillSpinners();
    }

    private void showDateTimeParseError() {
        Toast.makeText(this, "Date or time are not valid", Toast.LENGTH_SHORT).show();
    }


    private void setUserId() {
        String sessionId = preferences.getString("SessionId", "");
        String username = preferences.getString("Username", "");
        Call<List<User>> userByLoginCall = loriApi.getUserByLogin(sessionId, username);
        userByLoginCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> body = response.body();
                userId = body.get(0).getId();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TIME_ENTRY_TAG, "fail userId");
            }
        });
    }

    private void saveTimeEntry() {
        String sessionId = preferences.getString("SessionId", "");
        String splitter = Pattern.quote(":");
        String[] hoursAndMinutes = time.split(splitter);
        int hours = Integer.parseInt(hoursAndMinutes[0]);
        int minutes = Integer.parseInt(hoursAndMinutes[1]);
        int timeInMinutes = hours * 60 + minutes;
        DecimalFormat df = new DecimalFormat("####0.00");
        String timeInHours = df.format(timeInMinutes / 60);

        JSONObject mainObject = new JSONObject();
        JSONArray commitInstances = new JSONArray();
        JSONObject savedTimeEntry = new JSONObject();

        try {
            savedTimeEntry.put("id", "NEW-ts$TimeEntry");
            savedTimeEntry.put("date", strDate);
            savedTimeEntry.put("status", "new");

            JSONObject task = new JSONObject();
            task.put("id", taskId);
            savedTimeEntry.put("task", task);

            JSONObject user = new JSONObject();
            user.put("id", userId);
            savedTimeEntry.put("user", user);

            savedTimeEntry.put("timeInHours", timeInHours);
            savedTimeEntry.put("timeInMinutes", String.valueOf(timeInMinutes));

            commitInstances.put(savedTimeEntry);
            mainObject.put("commitInstances", commitInstances);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Call<String> saveCall = loriApi.commitEntity(sessionId, mainObject.toString());
        saveCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                moveToTheMainActivity();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TIME_ENTRY_TAG, "onFailure: " + t);
            }
        });
    }

    private void moveToTheMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void fillSpinners() {
        String sessionId = preferences.getString("SessionId", "");

        Call<List<Task>> tasksCall = loriApi.getTasks(sessionId);
        tasksCall.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                tasks = response.body();
                fillProjectSpinner();
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });
    }


    private void fillProjectSpinner() {

        projectNames.clear();
        tasksNames.clear();

        for (Task task : tasks) {
            Project project = task.getProject();
            String projectName = project.getName();
            if (!projectNames.contains(projectName)) {
                projectNames.add(projectName);
            }
        }


        if (projectPosition > projectNames.size() - 1) {
            projectPosition = 0;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, projectNames);
        projectSpinner.setAdapter(adapter);
        projectSpinner.setSelection(projectPosition);


        projectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                projectPosition = i;
                String selectedProject = projectSpinner.getSelectedItem().toString();
                fillTaskSpinner(selectedProject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void fillTaskSpinner(String projectName) {

        tasksNames.clear();


        for (Task task : tasks) {
            Project project = task.getProject();
            if (project.getName().equals(projectName)) {
                tasksNames.add(task.getName());
            }
        }

        if (taskPosition > tasksNames.size() - 1) {
            taskPosition = 0;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tasksNames);
        tasksByProjectSpinner.setAdapter(adapter);
        tasksByProjectSpinner.setSelection(taskPosition);

        tasksByProjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                taskPosition = i;
                String taskName = (String) tasksByProjectSpinner.getSelectedItem();
                for (Task task : tasks) {
                    if (task.getName().equals(taskName)) {
                        taskId = task.getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ProjectPosition", projectPosition);
        outState.putInt("TaskPosition", taskPosition);
        outState.putString("StrDate", strDate);
        outState.putSerializable("Date", date);
        outState.putString("Time", time);
        outState.putString("TaskId", taskId);
        outState.putString("UserId", userId);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        projectPosition = savedInstanceState.getInt("ProjectPosition", 0);
        taskPosition = savedInstanceState.getInt("TaskPosition", 0);
        strDate = savedInstanceState.getString("StrDate", "");
        date = (Calendar) savedInstanceState.getSerializable("Date");
        time = savedInstanceState.getString("Time");
        taskId = savedInstanceState.getString("TaskId");
        userId = savedInstanceState.getString("UserId");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            date = calendar;
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            String strHour = String.valueOf(hourOfDay);
            String strMinute = String.valueOf(minute);
            if (hourOfDay < 10) {
                strHour = "0" + strHour;
            }
            if (minute < 10) {
                strMinute = "0" + strMinute;
            }
            time = strHour + ":" + strMinute;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.time_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.time_entry_menu_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        String sessionId = preferences.getString("SessionId", "");
        String username = preferences.getString("Username", "");
        preferences.edit().putBoolean("RememberMe", false).apply();

        Log.d(TIME_ENTRY_TAG, "Session id from LoginActivity is: " + sessionId);
        Log.d(TIME_ENTRY_TAG, "Username from LoginActivity is: " + username);

        Call<String> logoutCall = loriApi.logout(sessionId);

        logoutCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                int statusCode = response.code();

                Log.d(TIME_ENTRY_TAG, "Response code is: " + statusCode);

                Intent intent = new Intent(TimeEntryActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TIME_ENTRY_TAG, t.toString());
                Toast toast = Toast.makeText(TimeEntryActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
