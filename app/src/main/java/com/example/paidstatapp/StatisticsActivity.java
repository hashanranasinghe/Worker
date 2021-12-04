package com.example.paidstatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatisticsActivity extends AppCompatActivity {

    private ListView ListView;
    private List<PaymentClass> pay;
    private Context context;
    public DataBaseHelper dataBaseHelper;
    private Button back;
    private TextView txtSum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ListView = findViewById(R.id.statId);
        back = findViewById(R.id.statBackBtn);
        txtSum = findViewById(R.id.txtSum);
        context = this;
        pay = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(context);

        final String id = getIntent().getStringExtra("Id");
        final String name = getIntent().getStringExtra("Name");


        viewPaymentList(Integer.parseInt(id));
        sum(Integer.parseInt(id));
        PaymentItemClickList(name);
        back();
    }

//show all payments of worker
    public void viewPaymentList(int id){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        pay = dataBaseHelper.getAllPayments(id);
        PaymentAdapter paymentAdapter = new PaymentAdapter(context,R.layout.payment_main,pay);
        ListView.setAdapter(paymentAdapter);

    }

//show sum of payments
    public void sum(int id){

        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        @SuppressLint("DefaultLocale") String sum = String.format("%.2f",dataBaseHelper.getSumPayment(id));
        txtSum.setText(sum);

    }
//back button of Stat activiy
    public void back(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }

    //click the item of list
    public void PaymentItemClickList(String name){
        String Name = name;
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PaymentClass paymentClass = pay.get(position);//get items
                AlertDialog.Builder builder= new AlertDialog.Builder(context);

                builder.setTitle(String.valueOf(Name));
                builder.setMessage(paymentClass.getPaymentDescription());
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, UpdatePaymentActivity.class);
                        intent.putExtra("ID", String.valueOf(paymentClass.getPaymentId()));
                        startActivity(intent);
                    }
                });
                //delete payment
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder build = new AlertDialog.Builder(context);
                        build.setTitle("Are you sure want to delete this ?");
                        build.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBaseHelper.DeletePayment(paymentClass.getPaymentId());
                                Toast.makeText(StatisticsActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context,MainActivity.class));
                            }
                        });
                        build.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(context,StatisticsActivity.class));
                            }
                        });
                        build.show();
                    }
                });
                builder.show();
            }
        });
    }
    //when press back button of phone then go to main activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }

}