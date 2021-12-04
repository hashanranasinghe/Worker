package com.example.paidstatapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {

    private Button clearPayBtn, payBtn;
    private EditText txtDate,txtPay,txtDescription;
    Context context;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView selectDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Objects.requireNonNull(getSupportActionBar()).hide();
        context = this;
        clearPayBtn = findViewById(R.id.clearPayBtn);
        payBtn = findViewById(R.id.btnPay);
        txtDate = findViewById(R.id.editTxtDate);
        txtPay = findViewById(R.id.editTxtPay);
        txtDescription = findViewById(R.id.editTxtDescription);
        selectDate = findViewById(R.id.selectDate);



        datePicker();
        addPayment();
        clear();


    }

    //add a payment
    private void addPayment(){
        final String id = getIntent().getStringExtra("Id");
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentClass paymentClass = new PaymentClass();
                if(txtDate.getText().toString().isEmpty()){
                    Toast.makeText(context,"Date required.",Toast.LENGTH_LONG).show();
                }else if(txtPay.getText().toString().isEmpty()){
                    Toast.makeText(context,"Payment required.",Toast.LENGTH_LONG).show();
                }else{
                    try {
                        paymentClass = new PaymentClass(-1, txtDate.getText().toString(),txtDescription.getText().toString() ,Float.parseFloat(txtPay.getText().toString()), Integer.parseInt(id));
                        //Toast.makeText(context, paymentClass.toString(), Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(context,"Please Enter the Details".toString(),Toast.LENGTH_SHORT).show();
                        //paymentClass = new PaymentClass(-1,"Error","Error",0,0);
                    }
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                    dataBaseHelper.paymentOne(paymentClass);

                    startActivity(new Intent(context,MainActivity.class));
                }




            }
        });
    }
//clear all data of payment activity that enter
    private void clear(){

        clearPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate.setText("");
                txtPay.setText("");
                txtDescription.setText("");


                Toast.makeText(context,"Cleared",Toast.LENGTH_SHORT).show();
            }
        });
    }
//to get date
    public void datePicker(){
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;

                    String date = month + "." + dayOfMonth + "." + year;
                    txtDate.setText(date);

            }
        };
    }
    //when press back button of phone then go to main activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }

}