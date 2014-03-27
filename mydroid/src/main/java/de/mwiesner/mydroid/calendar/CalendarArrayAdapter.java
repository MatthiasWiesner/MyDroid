package de.mwiesner.mydroid.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mwiesner.mydroid.R;
import de.mwiesner.mydroid.model.Week;
import com.squareup.picasso.Picasso;


public class CalendarArrayAdapter extends ArrayAdapter<Week> {
    private final Context context;
    private final ArrayList<Week> weeks;

    public CalendarArrayAdapter(Context context, ArrayList<Week> weeks) {
        super(context, R.id.calendarItem, weeks);
        this.context = context;
        this.weeks = weeks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View weekView = convertView;

        if(weekView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            weekView = inflater.inflate(R.layout.fragment_calendaritem, parent, false);
        }

        TextView firstline = (TextView) weekView.findViewById(R.id.firstLine);
        TextView secondline = (TextView) weekView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) weekView.findViewById(R.id.icon);

        Week week = weeks.get(position);

        firstline.setText(week.person.name);
        secondline.setText(week.week_nr + " " + week.start_date + " - " + week.end_date);
        Picasso.with(context)
                .load(week.person.picture)
                .fit()
                .into(imageView);

        return weekView;
    }
}