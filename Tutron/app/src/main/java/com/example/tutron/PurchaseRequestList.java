package com.example.tutron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PurchaseRequestList extends ArrayAdapter<PurchaseRequest> {
    private Activity context;

    List<PurchaseRequest> purchaseRequests;

    public PurchaseRequestList(Activity context, List<PurchaseRequest> purchaseRequests){
        super(context, R.layout.layout_student_purchase_request,purchaseRequests);
        this.context = context;
        this.purchaseRequests = purchaseRequests;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_student_purchase_request, null, true);

        TextView textViewStudentName = listViewItem.findViewById(R.id.purchase_student_name);
        TextView textViewTopicName = listViewItem.findViewById(R.id.purchase_topic_name);

        PurchaseRequest purchaseRequest = purchaseRequests.get(position);
        textViewStudentName.setText(purchaseRequest.getStudentEmail());
        textViewTopicName.setText(purchaseRequest.getTopic());

        return listViewItem;
    }
}
