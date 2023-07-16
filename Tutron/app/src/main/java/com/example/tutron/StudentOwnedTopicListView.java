package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentOwnedTopicListView extends ArrayAdapter<Topic> {

    private Activity context;

    List<Topic> topics;

    public StudentOwnedTopicListView(Activity context, List<Topic> topics) {
        super(context, R.layout.layout_tutor_topic, topics);
        this.context = context;
        this.topics = topics;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_tutor_topic, null, true);

        TextView textViewTopicName = listViewItem.findViewById(R.id.textViewTopicName);
        TextView textViewYearsOfExperience = listViewItem.findViewById(R.id.textViewYearsOfExperience);
        TextView textViewBriefDescription = listViewItem.findViewById(R.id.textViewBriefDescription);

        Topic topic = topics.get(position);
        //Recycling layout_tutor_topic to use it with student owned topics
        textViewTopicName.setText(topic.getName());
        textViewYearsOfExperience.setText(topic.getTutorEmail());
        textViewBriefDescription.setText(topic.getDescription());
        return listViewItem;
    }

}
