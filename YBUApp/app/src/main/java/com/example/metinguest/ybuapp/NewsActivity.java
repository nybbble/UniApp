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

public class NewsActivity extends Fragment {

    public ListView newsListView;
    public ArrayList<String> newsArrList;
    public ArrayList<String> newsLinks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.news_tab2, container, false);
        newsListView =(ListView) view.findViewById(R.id.newsList);
        new NewsFunction().execute();
        return view;
    }

    // Description AsyncTask
    private class NewsFunction extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                newsArrList = new ArrayList<String>();
                newsLinks = new ArrayList<String>();
                Document document = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Element parentHead = document.select("div.cnContent").first();
                Iterator<Element> iterator = parentHead.select("div.cncItem").iterator();

                while(iterator.hasNext()){
                    Element e =iterator.next();
                    newsArrList.add(e.text());
                    System.out.println("Value 1: " + e.select("a").attr("href"));
                    newsLinks.add(e.select("a").attr("href"));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, newsArrList);
            newsListView.setAdapter(adapter);
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {


                    String[] str = new String[10000];
                    for(int i = 0; i< newsLinks.size(); i++){
                        str[i]="http://www.ybu.edu.tr/muhendislik/bilgisayar/"+ newsLinks.get(i).toString();
                    }

                    Uri uri = Uri.parse(str[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

        }
    }

}
