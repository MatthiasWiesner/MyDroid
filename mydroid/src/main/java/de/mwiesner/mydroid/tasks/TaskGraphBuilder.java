package de.mwiesner.mydroid.tasks;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

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
import de.mwiesner.mydroid.tasks.model.Serie;
import de.mwiesner.mydroid.tasks.model.Tasks;


public class TaskGraphBuilder {

    private Activity activity;
    private SimpleDateFormat formatter;

    public TaskGraphBuilder(Activity activity){
        this.activity = activity;
        formatter = new SimpleDateFormat("hh:mm");
    }

    public View build(Tasks tasks) {

        XYSeries taskOpenSeries = new XYSeries("Open");
        XYSeries taskExecutedSeries = new XYSeries("Executed");

        for (Serie s : tasks.series) {
            String name = s.name;
            ArrayList<ArrayList<Long>> points = s.data;
            List<ArrayList<Long>> last10 = points.subList(points.size() - 11, points.size());

            long count = last10.get(last10.size() - 1).get(1);
            long time = last10.get(last10.size() - 1).get(0);
            Date date = new Date(time);

            TextView timestampView = (TextView) activity.findViewById(R.id.timestamp);
            timestampView.setText(formatter.format(date));

            if (name.equals("task_open")) {
                TextView view = (TextView) activity.findViewById(R.id.task_open);
                view.setText("Open: " + count);

                for (ArrayList<Long> p : last10) {
                    taskOpenSeries.add(p.get(0), p.get(1));
                }
            }

            if (name.equals("task_executed")) {
                TextView view = (TextView) activity.findViewById(R.id.task_executed);
                view.setText("Executed: " + count);

                for (ArrayList<Long> p : last10) {
                    taskExecutedSeries.add(p.get(0), p.get(1));
                }
            }
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding Income Series to the dataset
        dataset.addSeries(taskOpenSeries);
        // Adding Expense Series to dataset
        dataset.addSeries(taskExecutedSeries);

        // Creating XYSeriesRenderer to customize incomeSeries
        XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
        incomeRenderer.setColor(Color.rgb(130, 130, 230));
        incomeRenderer.setFillPoints(true);
        incomeRenderer.setLineWidth(2);
        incomeRenderer.setDisplayChartValues(true);

        // Creating XYSeriesRenderer to customize expenseSeries
        XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
        expenseRenderer.setColor(Color.rgb(220, 80, 80));
        expenseRenderer.setFillPoints(true);
        expenseRenderer.setLineWidth(2);
        expenseRenderer.setDisplayChartValues(true);
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR);
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Task Open/Executed");
        multiRenderer.setXTitle("Open");
        multiRenderer.setYTitle("Executed");
        multiRenderer.setZoomButtonsVisible(true);
        //for(int i=0; i< 10;i++){
        //    multiRenderer.addXTextLabel(i, mMonth[i]);
        //}

        // Adding incomeRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(incomeRenderer);
        multiRenderer.addSeriesRenderer(expenseRenderer);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        GraphicalView view = ChartFactory.getBarChartView(activity, dataset, multiRenderer, BarChart.Type.DEFAULT);
        return view;
    }
}

