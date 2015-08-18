package com.example.shubhambakshi.parseapp3;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.parse.Parse;
import com.parse.ParseObject;
import android.content.Intent;
import android.view.Menu;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.widget.ProgressBar;
import android.view.MenuItem;
/**
 * Created by User on 07-08-2015.
 */
public class MainActivity extends Activity {
    Button button;
    static TextView editText;
    static String searchString;
    static String searchParam;
    ListView contactListView1;
    List<ParseObject> validList;
    public static ProgressBar spinner;
    static SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "UScatQxieE3MNKjYZlfH5WvkuRQxz1rmcheGHtNL", "tOQY1uuBcfXbKbbDGZgnxAPg8YQMsrDfBEGcatsQ");
        }catch(Exception e){
            Toast.makeText(getApplication(),"Problem connecting to the database",Toast.LENGTH_LONG).show();
            return;
        }
     //   editText = (TextView)findViewById(R.id.editText2);
     //   if(editText.isEnabled()){
     //       editText.setEnabled(false);

   //     }
        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setFocusable(true);
        addListenerOnButton();
        addListenerOnButton1();
    }

    private void addListenerOnButton() {
        final RadioButton radio_name = (RadioButton) findViewById(R.id.fname);
        final RadioButton radio_phone = (RadioButton) findViewById(R.id.contact);
        final RadioButton radio_email = (RadioButton) findViewById(R.id.email);
        radio_name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_phone.isChecked()) radio_phone.setChecked(false);
                if (radio_email.isChecked()) radio_email.setChecked(false);
                workWithRadio(radio_name, "FirstName");

            }
        });

        radio_email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_phone.isChecked()) radio_phone.setChecked(false);
                if (radio_name.isChecked()) radio_name.setChecked(false);
                workWithRadio(radio_email, "Email");

            }
        });

        radio_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_email.isChecked()) radio_email.setChecked(false);
                if (radio_name.isChecked()) radio_name.setChecked(false);
                workWithRadio(radio_phone, "PhoneNumber");

            }
        });

    }

    private void workWithRadio(RadioButton radio,final String param1) {
        if (!searchView.isEnabled()) {
            enableSearchView();
        }

            searchParam = param1.toString();
     //   validateSearchParam(searchParam);
    }

    private void enableSearchView(){
        searchView.setEnabled(true);
        searchView.setFocusable(true);

    }

    private boolean validateSearchParam(String param){

        switch(param) {
            case "FirstName":  Pattern pattern = Pattern.compile("[A-Za-z]*");
            Matcher m = pattern.matcher(searchString);
            if (!m.matches()) {
                Toast.makeText(getApplicationContext(), "Invalid input for Name", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        //    break;

            case "PhoneNumber": Pattern pattern1 = Pattern.compile("[0-9]*");
                Matcher m1 = pattern1.matcher(searchString);
                if(!m1.matches()){
                    Toast.makeText(getApplicationContext(),"Invalid input for Phone Number",Toast.LENGTH_LONG).show();
                    return false;
                }
                if(countchars(searchString)>11){
                    Toast.makeText(getApplicationContext(),"Range exceeds in phone Number",Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
      //      break;
            case "Email" : return true;

        }
       return false;
    }

    private int countchars(String str){
        int count;
        count = str.toString().toCharArray().length;
        return count;
    }


    private void addListenerOnButton1(){

        Button button = (Button)findViewById(R.id.findbutton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchParam==null){
                    Toast.makeText(getApplicationContext(),"Select a search Parameter",Toast.LENGTH_SHORT).show();
                    return;
                }
                searchView=(android.widget.SearchView)findViewById(R.id.searchView);
                searchString = searchView.getQuery().toString();
                if(searchString.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Search field empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!validateSearchParam(searchParam)){
                    return;
               }
             //   spinner.setVisibility(View.VISIBLE);
               Intent intent = new Intent(getApplicationContext(),Searchoperate.class);
                startActivity(intent);

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



}
