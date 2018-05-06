package com.example.metinguest.ybuapp;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by metinguest on 5.05.2018.
 */

public class FoodActivity extends Fragment {

    public TextView foodtxtV;
    public ArrayList<String> foodArrList ;

    String foodStr="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.food_tab1, container, false);

        foodtxtV =(TextView)view.findViewById(R.id.foodTxt);


        new FoodFunc().execute();

        return view;
    }


    // Description AsyncTask
    private class FoodFunc extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Void doInBackground(Void... params) {
            foodArrList = new ArrayList<String>();

            try {

                Document document = Jsoup.connect("http://ybu.edu.tr/sks/").get();
                Elements foodElement = document.select("table p");
                Elements date= document.select("h3");
                foodArrList.add(date.text());
                for (int a = 0; a < foodElement.size(); a++) {

                    foodArrList.add(foodElement.get(a).text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            for(int i = 0; i< foodArrList.size(); i++){
                foodStr=foodStr+"\n\n"+ foodArrList.get(i).toString();
            }
            foodtxtV.setText(foodStr);

        }
    }
}
