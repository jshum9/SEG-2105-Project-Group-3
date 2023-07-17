package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewList extends ArrayAdapter<Review> {

    private Activity context;

    List<Review> reviews;


    public ReviewList(Activity context, List<Review> reviews){

        super(context, R.layout.layout_review,reviews);
        this.context = context;
        this.reviews = reviews;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_review,null,true);
        TextView textViewStudentName = listViewItem.findViewById(R.id.student_name_textView);
        TextView textViewReview = listViewItem.findViewById(R.id.textViewReview);

        Review review = reviews.get(position);

        textViewStudentName.setText(review.getStudentEmail());
        textViewReview.setText(review.getReview());

        return listViewItem;
    }
}
