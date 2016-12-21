package com.malachornix.lori;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.malachornix.lori.interfaces.LoriApi;
import com.malachornix.lori.model.TimeEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class TimeEntriesAdapter extends RecyclerView.Adapter<TimeEntriesAdapter.TimeEntriesViewHolder> {

    private static final String TIME_ENTRIES_ADAPTER = "TIME_ENTRIES_ADAPTER";

    private List<TimeEntry> timeEntries;
    private Context context;

    public TimeEntriesAdapter(List<TimeEntry> timeEntries) {
        this.timeEntries = timeEntries;
    }

    public TimeEntriesAdapter(List<TimeEntry> timeEntries, Context context) {
        this.timeEntries = timeEntries;
        this.context = context;
    }


    @Override
    public TimeEntriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_entries_layout, parent, false);
        TimeEntriesViewHolder timeEntriesViewHolder = new TimeEntriesViewHolder(viewItem);
        return timeEntriesViewHolder;
    }

    @Override
    public void onBindViewHolder(TimeEntriesViewHolder holder, final int position) {
        holder.date.setText(timeEntries.get(position).getDate());
        holder.taskName.setText(timeEntries.get(position).getTaskName());
        holder.time.setText(timeEntries.get(position).getTimeInMinutes());
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeTimeEntry(position);
            }
        });
    }

    private void removeTimeEntry(final int position) {
        final SharedPreferences preferences
                = context.getSharedPreferences("LoriApp", MODE_PRIVATE);

        String baseUrl = preferences.getString("BaseUrl", context.getResources().getString(R.string.apiUrl));
        String sessionId = preferences.getString("SessionId", "");


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoriApi loriApi = retrofit.create(LoriApi.class);

        JSONObject mainObject = new JSONObject();
        JSONArray removeInstances = new JSONArray();
        JSONObject removedTimeEntry = new JSONObject();
        try {
            removedTimeEntry.put("id", timeEntries.get(position).getId());
            removeInstances.put(removedTimeEntry);
            mainObject.put("removeInstances", removeInstances);
            mainObject.put("softDeletion", "true");
        } catch (JSONException e) {
            Log.d(TIME_ENTRIES_ADAPTER, e.getMessage());
        }

        Call<String> removeCall = loriApi.removeTimeEntry(sessionId, mainObject.toString());
        removeCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                timeEntries.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, timeEntries.size());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.d(TIME_ENTRIES_ADAPTER, t.getMessage());
            }
        });



    }

    @Override
    public int getItemCount() {
        return timeEntries.size();
    }

    public static class TimeEntriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.time_entries_view)
        CardView cardViewTimeEntries;
        @BindView(R.id.time_entry_date)
        TextView date;
        @BindView(R.id.time_entry_task_name)
        TextView taskName;
        @BindView(R.id.time_entry_time)
        TextView time;
        @BindView(R.id.time_entry_remove_button)
        Button removeButton;

        public TimeEntriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
