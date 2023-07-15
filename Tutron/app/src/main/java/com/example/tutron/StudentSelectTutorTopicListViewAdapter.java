package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class StudentSelectTutorTopicListViewAdapter extends ArrayAdapter<Topic> {

    private Activity context;

    List<Topic> topics;

    public StudentSelectTutorTopicListViewAdapter(Activity context, List<Topic> topics) {
        super(context, R.layout.layout_student_select_tutor_topic, topics);
        this.context = context;
        this.topics = topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_student_select_tutor_topic, null, true);
        TextView textViewTopicName = listViewItem.findViewById(R.id.topicNameTextView);
        RatingBar ratingBar = listViewItem.findViewById(R.id.reviewRatingBar);

        Topic topic = topics.get(position);
        textViewTopicName.setText(Character.toUpperCase(topic.getName().charAt(0)) + topic.getName().substring(1));
        ratingBar.setRating(topic.getRating());
        return listViewItem;
    }

}
