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

public class PaymentAdapter extends ArrayAdapter<PaymentClass> {

    private Context context;
    private  int resource;
    List<PaymentClass> payment;
    PaymentAdapter(Context context, int resource, List<PaymentClass> payment) {
        super(context, resource, payment);
        this.context = context;
        this.resource = resource;
        this.payment = payment;

    }

    //adding values to payment_main.xml
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View row = inflater.inflate(resource,parent,false);

        TextView date = row.findViewById(R.id.txtDate);
        TextView pay = row.findViewById(R.id.txtPay);

        PaymentClass paid = payment.get(position);
        date.setText(paid.getPaymentDate());
        String payments = String.format("%.2f",paid.getPayment());
        pay.setText(payments);

        return row;
    }
}
