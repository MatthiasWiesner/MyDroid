package de.mwiesner.mydroid.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.mwiesner.mydroid.R;
import de.mwiesner.mydroid.events.EventBus;
import de.mwiesner.mydroid.tasks.model.Serie;
import de.mwiesner.mydroid.tasks.model.Tasks;


public class TasksFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Activity activity;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TasksFragment newInstance(int sectionNumber) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        (new LoadTasksAsyncTask()).execute();
        return view;
    }

    @Subscribe
    public void displayCalendar(Tasks tasks){
        if(isAdded()){
            activity = getActivity();
            LinearLayout layout = (LinearLayout) activity.findViewById(R.id.graph);
            layout.removeAllViews();
            layout.addView(new TaskGraphBuilder(activity).build(tasks));
        }
    }
}
