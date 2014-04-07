package de.mwiesner.mydroid.tasks;

import android.os.AsyncTask;

import com.squareup.otto.Bus;

import de.mwiesner.mydroid.backend.HeroBackend;
import de.mwiesner.mydroid.events.EventBus;
import de.mwiesner.mydroid.tasks.model.Tasks;

import retrofit.RestAdapter;


public class LoadTasksAsyncTask extends AsyncTask<Void, Void, Tasks> {

    private Bus bus = EventBus.getEventBus();

    @Override
    protected Tasks doInBackground(Void... params) {
        try {
            RestAdapter restAdapter = HeroBackend.getAdapter();
            TasksBackendInterface tasksBackendInterface= restAdapter.create(TasksBackendInterface.class);
            return tasksBackendInterface.tasks();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Tasks tasks) {
        bus.post(tasks);
    }
}
