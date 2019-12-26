package com.example.newsvezba;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    public static final String BASE_URL = "https://content.guardianapis.com/sections";
    public static final String APIKEY = "1d4886cc-a21e-4052-b569-15ad0371ebdf";



    View rootView;

    public MainFragment() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
        }

        NewsAsyncTask newsAsyncTask;

        RecyclerView recyclerView;

        recyclerView = rootView.findViewById(R.id.recyclerView);

        newsAsyncTask = new NewsAsyncTask( getFragmentManager(),  recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsAsyncTask.execute();


        return rootView;
    }


    static class NewsAsyncTask extends AsyncTask<Void, Void, ArrayList<Section>>{

        FragmentManager fragmentManager;
        RecyclerView recyclerView;

        public NewsAsyncTask(FragmentManager fragmentManager, RecyclerView recyclerView) {
            this.fragmentManager = fragmentManager;
            this.recyclerView = recyclerView;
        }

        @Override
        protected ArrayList<Section> doInBackground(Void... voids) {

            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("api-key", APIKEY)
                    .build();

            ArrayList<Section> sectionsResults = new ArrayList<>();

            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == 200){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = convertIsToString(inputStream);

                    JSONObject jsonResponseObject = new JSONObject(results);

                    JSONObject jsonResultsObject = jsonResponseObject.getJSONObject("response");

                    JSONArray jsonArray = jsonResultsObject.getJSONArray("results");

                    for(int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);



                        String sectionId = (String) jsonObject.get("id");



                        String webTitle = (String) jsonObject.get("webTitle");

                        sectionsResults.add(new Section( sectionId, webTitle));



                    }
                }




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return sectionsResults;
        }


        @Override
        protected void onPostExecute(ArrayList<Section> sections) {
            super.onPostExecute(sections);

            NewsAdapter adapter = new NewsAdapter(sections, fragmentManager);

            recyclerView.setAdapter(adapter);

        }
    }



    public static String convertIsToString(InputStream inputStream) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
    }




}
