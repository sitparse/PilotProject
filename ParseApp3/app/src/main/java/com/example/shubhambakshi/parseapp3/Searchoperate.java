package com.example.shubhambakshi.parseapp3;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import java.util.Iterator;
import java.util.List;
import com.parse.ParseQuery;
import java.util.ArrayList;
import com.parse.ParseGeoPoint;
import com.daimajia.numberprogressbar.NumberProgressBar;
import android.view.Menu;
import android.widget.Button;
import android.content.Intent;
import android.app.ListActivity;
import android.widget.LinearLayout;
import android.os.AsyncTask;

import org.w3c.dom.Text;

/**
 * Created by Shubham Bakshi on 07-08-2015.
 */
public class Searchoperate extends ListActivity {
    static List<ParseObject> validList = new ArrayList<ParseObject>();
    ListView contactListView1;
    float longitude, latitude;
    private TextView textView;
    private NumberProgressBar numprogressbar;
    private Button mapButton;
    LinearLayout linlaHeaderProgress;
    ArrayAdapter adapter;
    @Override
    public void onCreate(final Bundle savedInstances) {
        super.onCreate(savedInstances);

        setContentView(R.layout.contactlistview);
        linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        contactListView1 = (ListView)findViewById(R.id.list);
        textView = (TextView)findViewById(R.id.textView);
        mapButton = (Button)findViewById(R.id.mapButton);
        addActionListenerToButton();

        final ParseObject testObject = new ParseObject("PARSE");
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("PARSE");

        //  query.whereNear("latitude", userLocation);
        query.whereMatches(MainActivity.searchParam, "[A-Za-z0-9]");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> testObjectlist, ParseException e) {
                if (e == null) {
                    validList = checkAndCreateList(testObjectlist, MainActivity.searchString);

                    if (validList.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No Match found", Toast.LENGTH_LONG).show();
                        return;

                    }
                }
                ParseGeoPoint userLocation = (ParseGeoPoint) new ParseGeoPoint(13.329236, 77.09808);
                ParseQuery<ParseObject> newquery = ParseQuery.getQuery("PARSE");
                newquery.whereNear("latitude", userLocation);

                newquery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        validList = checkAndCreateList(list, MainActivity.searchString);
                        populateList();
                        new Task().execute();
                        // MapMarkerset(validList);
                    }
                });

            }
        });

        addListenerOnButton();
    }

    private List<ParseObject> checkAndCreateList(List<ParseObject> list, String pattern) {
        List<ParseObject> validList = new ArrayList<>();
        Iterator<ParseObject> itr = list.iterator();
        while (itr.hasNext()) {
            ParseObject testObject = itr.next();
            if (!MainActivity.extrasearchParam.isEmpty()) {
                if (isSubstring(testObject.get(MainActivity.searchParam.toString()).toString(), pattern) || isSubstring(testObject.get(MainActivity.extrasearchParam.toString()).toString(), pattern)) {
                    validList.add(testObject);
                }
            } else {
                if (isSubstring(testObject.get(MainActivity.searchParam.toString()).toString(), pattern)) {
                    validList.add(testObject);
                }
            }
        }
        return validList;
    }

    private boolean isSubstring(String text, String pattern) {
        if (text.toLowerCase().contains(pattern.toLowerCase())) {
            return true;
        }
        return false;
    }

    public void populateList() {
        try {
            adapter = new contactListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, validList);
            //          setContentView(R.layout.contactlistview);
         //   setContentView(R.layout.contactlistview);
         //   contactListView1 = getListView();
            contactListView1 = (ListView) findViewById(android.R.id.list);
            contactListView1.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }


    public class contactListAdapter extends ArrayAdapter {
        public contactListAdapter(Context context, int id, List validlist) {
            super(context, id, validlist);
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            //  view = super.getView(position, view, parent);

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_items, parent, false);
            Iterator<ParseObject> itr = validList.iterator();
            ParseObject currentContact = validList.get(position);

            //   setContentView(R.layout.listview_items);

            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView email = (TextView) view.findViewById(R.id.tv_email);
            TextView phone = (TextView) view.findViewById(R.id.tv_phone);
            TextView id = (TextView) view.findViewById(R.id.tv_id);

            //  setContentView(R.layout.listview_items);
            name.setText("FirstName: " + currentContact.get("FirstName").toString());
            email.setText("Email: " + currentContact.get("Email").toString());
            phone.setText("PhoneNumber: " + currentContact.get("PhoneNumber").toString());
            id.setText("ID: " + currentContact.get("ID").toString());

            Button mapButton = (Button) findViewById(R.id.mapButton);
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public ParseObject getItem(int position) {
            return validList.get(position);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

    // ###################################################################################################################################
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    private void addListenerOnButton() {
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            linlaHeaderProgress.setVisibility(View.VISIBLE);
            contactListView1.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            mapButton.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            linlaHeaderProgress.setVisibility(View.GONE);
            contactListView1.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            mapButton.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }
    public void addActionListenerToButton(){
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }
}




