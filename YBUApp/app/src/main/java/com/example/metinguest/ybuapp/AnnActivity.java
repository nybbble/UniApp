package com.example.metinguest.ybuapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by metinguest on 5.05.2018.
 */

public class AnnActivity extends Fragment {

    public ListView announceListView;
    public ArrayList<String> announceArrList;
    public ArrayList<String> annLinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.ann_tab3, container, false);
        announceListView =(ListView) view.findViewById(R.id.annList);

        new AnnounceFunction().execute();

        return view;
    }

    // Description AsyncTask
    private class AnnounceFunction extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                announceArrList = new ArrayList<String>();
                annLinks = new ArrayList<String>();
                Document document = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Element masthead = document.select("div.caContent").first();
                Iterator<Element> ite = masthead.select("div.cncItem").iterator();
                //ite.next();

                while(ite.hasNext()){
                    Element div =ite.next();
                    announceArrList.add(div.text());
                    System.out.println("Value 1: " + div.select("a").attr("href"));
                    annLinks.add(div.select("a").attr("href"));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ArrayAdapter a=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, announceArrList);
            announceListView.setAdapter(a);
            announceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String[] a = new String[10000];
                    for(int i = 0; i< annLinks.size(); i++){
                        a[i]="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+ annLinks.get(i).toString();
                    }

                    Uri uri = Uri.parse(a[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });

        }
    }

}
