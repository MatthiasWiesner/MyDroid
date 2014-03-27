package de.mwiesner.mydroid.tasks.backend;

import android.os.AsyncTask;
import java.util.Date;

import de.mwiesner.mydroid.model.Weeks;
import de.mwiesner.mydroid.EventBus;
import com.squareup.otto.Bus;

public class LoadCalendarAsyncTask extends AsyncTask<Void, Void, Weeks> {

    private Bus bus = EventBus.getEventBus();

    @Override
    protected Weeks doInBackground(Void... params) {
        try {
            return HeroBackend.loadCalendar(new Date());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weeks weeks) {
        System.out.println(weeks);
        bus.post(weeks);
    }
}
