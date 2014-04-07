package de.mwiesner.mydroid.calendar;

import android.os.AsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.mwiesner.mydroid.calendar.model.Weeks;
import de.mwiesner.mydroid.events.EventBus;
import de.mwiesner.mydroid.backend.HeroBackend;
import retrofit.RestAdapter;

import com.squareup.otto.Bus;

public class LoadCalendarAsyncTask extends AsyncTask<Void, Void, Weeks> {

    private Bus bus = EventBus.getEventBus();

    @Override
    protected Weeks doInBackground(Void... params) {
        try {
            RestAdapter restAdapter = HeroBackend.getAdapter();
            CalendarBackendInterface loadCalendarInterface = restAdapter.create(CalendarBackendInterface.class);

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = formatter.format(new Date());

            return loadCalendarInterface.weeks(currDate, "next");

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weeks weeks) {
        bus.post(weeks);
    }
}
