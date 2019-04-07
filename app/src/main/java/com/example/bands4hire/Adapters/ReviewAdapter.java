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
import com.example.bands4hire.DataModels.Review;
import com.example.bands4hire.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public ReviewAdapter(Context context, ArrayList<Review> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review reviewObject = mData.get(position);

        if(reviewObject.getReview().equals("No reviews have been left for this band")){
            holder.review.setText(reviewObject.getReview());
            holder.leftBy.setVisibility(View.GONE);
        }else {
            holder.review.setText(reviewObject.getReview());
            holder.leftBy.setText(" - " +reviewObject.getLeftBy());
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView review, leftBy;

        ViewHolder(View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.review);
            leftBy = itemView.findViewById(R.id.leftBy);
        }
    }

    // convenience method for getting data at click position
    public Review getItem(int id) {
        return mData.get(id);
    }

}
