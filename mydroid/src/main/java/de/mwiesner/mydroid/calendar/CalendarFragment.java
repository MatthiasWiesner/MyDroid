package de.mwiesner.mydroid.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import de.mwiesner.mydroid.EventBus;
import de.mwiesner.mydroid.R;
import de.mwiesner.mydroid.model.Week;
import de.mwiesner.mydroid.model.Weeks;
import de.mwiesner.mydroid.tasks.backend.LoadCalendarAsyncTask;


public class CalendarFragment extends Fragment {
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
    public static CalendarFragment newInstance(int sectionNumber) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarFragment() {
        EventBus.getEventBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
         Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        new LoadCalendarAsyncTask().execute();
        return view;
    }

    @Subscribe
    public void displayCalendar(Weeks weeks){
        if(isAdded()){
            activity = getActivity();
            ListView listView = (ListView) activity.findViewById(R.id.calendarList);
            CalendarArrayAdapter adapter = new CalendarArrayAdapter(activity, weeks.weeks);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                Week week = (Week) parent.getItemAtPosition(position);
                final CharSequence[] items = {week.person.phone, week.person.email};

                AlertDialog.Builder ad = new AlertDialog.Builder(activity);
                ad.setTitle(week.person.name);
                ad.setItems(items, new DialogInterface.OnClickListener(){
                    public void onClick (DialogInterface dialog,int id){
                    if (id == 0){
                        // Phone
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + (String) items[id]));
                        activity.startActivity(callIntent);
                    }
                    if (id == 1){
                        // Email
                        String address = (String)items[id];
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", address, null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mail from opsdroid");
                        activity.startActivity(Intent.createChooser(emailIntent, "Send email to " + address));
                    }
                    }
                });
                ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog,int id){
                    dialog.cancel();
                }});
                ad.show();
                }
            });
        }
    }
}
