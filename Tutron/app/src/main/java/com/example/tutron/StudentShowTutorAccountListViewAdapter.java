package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class StudentShowTutorAccountListViewAdapter extends ArrayAdapter<TutorAccount> {

    private Activity context;

    List<TutorAccount> tutors;

    public StudentShowTutorAccountListViewAdapter(Activity context, List<TutorAccount> tutors) {
        super(context, R.layout.layout_student_search_tutor_result, tutors);
        this.context = context;
        this.tutors = tutors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_student_search_tutor_result, null, true);

        TextView textViewTutorName = listViewItem.findViewById(R.id.textViewTutorName);
        TextView textViewNativeLanguage = listViewItem.findViewById(R.id.textViewNativeLanguage);
        //TODO: hourly rate
        TextView textViewHourlyRate = listViewItem.findViewById(R.id.textViewHourlyRate);
        TextView numberOfLessonsGiven = listViewItem.findViewById(R.id.numberOfLessonsGiven);

        RatingBar ratingBar = listViewItem.findViewById(R.id.ratingBar);

        TutorAccount tutor = tutors.get(position);
        textViewTutorName.setText(tutor.getFirstName());
        textViewNativeLanguage.append(tutor.getNativeLanguage());
        numberOfLessonsGiven.append(Integer.toString(tutor.getNumberOfLessonsGiven()));
        ratingBar.setRating(tutor.getAverageRating());

        return listViewItem;
    }

}
