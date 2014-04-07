package de.mwiesner.mydroid.tasks;


import de.mwiesner.mydroid.tasks.model.Tasks;
import retrofit.http.GET;


public interface TasksBackendInterface {
    @GET("/tasks")
    Tasks tasks();
}