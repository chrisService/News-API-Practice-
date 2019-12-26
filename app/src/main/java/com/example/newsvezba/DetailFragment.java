package com.example.newsvezba;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.newsvezba.MainFragment.convertIsToString;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    View rootView;

    public DetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        }

        RecyclerView rvSection = rootView.findViewById(R.id.rvSection);

        DetailAsyncTask detailAsyncTask = new DetailAsyncTask(getArguments().getString("SectionId"), rvSection);

        rvSection.setLayoutManager(new LinearLayoutManager(getContext()));

        detailAsyncTask.execute();



        return rootView;

    }


    static class DetailAsyncTask extends AsyncTask<Void, Void, ArrayList<Section>> {

        private String sectionId;

        private RecyclerView recyclerView;

        public DetailAsyncTask(String sectionId, RecyclerView recyclerView) {
            this.sectionId = sectionId;
            this.recyclerView = recyclerView;
        }

        @Override
        protected ArrayList<Section> doInBackground(Void... voids) {

            ArrayList<Section> sectionsResults = new ArrayList<>();

            Uri uri = Uri.parse("https://content.guardianapis.com/search").buildUpon()
                    .appendQueryParameter("api-key", MainFragment.APIKEY)
                    .appendQueryParameter("section", sectionId)
                    .build();

            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = convertIsToString(inputStream);

                    JSONObject jsonResponseObject = new JSONObject(results);

                    JSONObject jsonResultsObject = jsonResponseObject.getJSONObject("response");

                    JSONArray jsonArray = jsonResultsObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);



                        String sectionId = (String) jsonObject.get("sectionId");



                        String webTitle = (String) jsonObject.get("webTitle");


                        sectionsResults.add(new Section(sectionId, webTitle));


                    }
                }
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

            SectionAdapter sectionAdapter = new SectionAdapter(sections);

            recyclerView.setAdapter(sectionAdapter);

        }
    }
}
