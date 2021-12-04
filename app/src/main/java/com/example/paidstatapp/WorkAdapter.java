package com.example.paidstatapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WorkAdapter extends ArrayAdapter<WorkerClass> {
    private Context context;
    private  int resource;
    List<WorkerClass> work;
    WorkAdapter(Context context, int resource, List<WorkerClass> work){
    super(context,resource,work);
    this.context = context;
    this.resource = resource;
    this.work = work;
}

//view data on payment_main.xml
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View row = inflater.inflate(resource,parent,false);

        TextView name = row.findViewById(R.id.txtName);
        TextView phoneNum = row.findViewById(R.id.txtPhoneNum);
        TextView address = row.findViewById(R.id.txtAddress);
        TextView category = row.findViewById(R.id.category);


        WorkerClass worker = work.get(position);
        name.setText(worker.getName());
        phoneNum.setText(String.valueOf(worker.getPhoneNumber()));
        address.setText(worker.getAddress());


        if((worker.isMason())){
            category.setText("Mason");
        }else if(worker.isCarpenter()){
            category.setText("Carpenter");
        }else if(worker.isPainter()){
            category.setText("Painter");
        }


        return row;
    }
}



