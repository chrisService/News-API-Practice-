package com.example.newsvezba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.NewsViewHolder> {


    private List<Section> sectionList;

    private FragmentManager fragmentManager;

    public NewsAdapter(List<Section> sectionList, FragmentManager fragmentManager) {
        this.sectionList = sectionList;

        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        final Section section = sectionList.get(holder.getAdapterPosition());

        holder.tvSection.setText(section.getSectionId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DetailFragment detailFragment = new DetailFragment();

                Bundle bundle = new Bundle();
                bundle.putString("SectionId", section.getSectionId());

                detailFragment.setArguments(bundle);

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, detailFragment)
                        .addToBackStack("Back")
                        .commit();
            }
        });


    }




    @Override
    public int getItemCount() {
        return sectionList.size();
    }





    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvSection;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSection = itemView.findViewById(R.id.tvSection);




        }
    }
}
