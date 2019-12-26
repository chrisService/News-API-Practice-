package com.example.newsvezba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {

    private List<Section> newsList;

    public SectionAdapter(List<Section> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);
        SectionViewHolder sectionViewHolder= new SectionViewHolder(view);

        return sectionViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {


        final Section section = newsList.get(holder.getAdapterPosition());


        holder.tvSectionId.setText(section.getSectionId());


        holder.tvWebTitle.setText(section.getWebTitle());



    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView tvType, tvSectionId, tvSectionName, tvWebTitle;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tvType);

            tvSectionId = itemView.findViewById(R.id.tvSevtionId);

            tvSectionName = itemView.findViewById(R.id.tvSectionName);

            tvWebTitle = itemView.findViewById(R.id.tvWebTitle);




        }
    }
}
