package com.example.shubhambakshi.parseapp3;
/**
 * Created by Shubham Bakshi on 13-08-2015.
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MapFragment extends android.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstances){
        return inflater.inflate(R.layout.activity_maps,parent,false);


    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        getFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
