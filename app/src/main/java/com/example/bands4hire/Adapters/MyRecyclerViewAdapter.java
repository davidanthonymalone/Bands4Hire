package com.example.bands4hire.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.bands4hire.DataModels.BandAdvert;
import com.example.bands4hire.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
                                    implements Filterable {

    private ArrayList<BandAdvert> mData;
    private ArrayList<BandAdvert> mDataFiltered;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<BandAdvert> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataFiltered = data;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BandAdvert advert = mDataFiltered.get(position);
        holder.rvBand.setText(advert.getBandName());
        holder.rvDate.setText(advert.getDateAvailable());
        holder.rvPrice.setText(" -  €"+advert.getPrice()+" per hour");
        holder.rvGenre.setText(advert.getGenre());
        holder.rvLocation.setText(" - " +advert.getLocation());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();

                if(charString.isEmpty()){
                    mDataFiltered = mData;
                }else {
                    ArrayList<BandAdvert> filterdList = new ArrayList<>();

                    for(BandAdvert bandAdvert : mData){
                        if(bandAdvert.getBandName().toLowerCase().contains(charString.toLowerCase())){
                            filterdList.add(bandAdvert);
                        }
                    }
                    mDataFiltered = filterdList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (ArrayList<BandAdvert>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rvBand, rvDate, rvGenre, rvPrice, rvLocation;

        ViewHolder(View itemView) {
            super(itemView);
            rvBand = itemView.findViewById(R.id.rvBandName);
            rvGenre = itemView.findViewById(R.id.rvGenre);
            rvPrice = itemView.findViewById(R.id.rvPrice);
            rvDate = itemView.findViewById(R.id.rvDate);
            rvLocation = itemView.findViewById(R.id.rvLocation);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public BandAdvert getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}