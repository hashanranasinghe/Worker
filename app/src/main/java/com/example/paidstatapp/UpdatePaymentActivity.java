package com.example.paidstatapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Objects;

public class UpdatePaymentActivity extends AppCompatActivity {

    private Button paymentBtn, paymentClearBtn,paymentBackBtn;
    private EditText paymentDate,paymentDescription,updatePayment;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DataBaseHelper dataBaseHelper;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadate_payment);
        Objects.requireNonNull(getSupportActionBar()).hide();
        context = this;
        dataBaseHelper = new DataBaseHelper(this);
        paymentBtn = findViewById(R.id.updateBtnPay);
        paymentClearBtn = findViewById(R.id.updateClearPayBtn);
        paymentBackBtn = findViewById(R.id.updateBackPayBtn);
        paymentDate = findViewById(R.id.updateTxtDate);
        paymentDescription  = findViewById(R.id.updateTxtDescription);
        updatePayment = findViewById(R.id.updateTxtPay);
        final String id = getIntent().getStringExtra("ID");
        datePicker();
        UpdatePayment(Integer.parseInt(id));
        GetPaymentInfo();
        UpdatePaymentBack();

    }

    public void GetPaymentInfo(){
        final String id = getIntent().getStringExtra("ID");
        PaymentClass paymentClass = dataBaseHelper.getPaymentValue(Integer.parseInt(id));

        paymentDate.setText(paymentClass.getPaymentDate());
        paymentDescription.setText(paymentClass.getPaymentDescription());
        updatePayment.setText(String.valueOf(paymentClass.getPayment()));

    }

    public void UpdatePaymentBack(){
        paymentBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }
//update data of payment
    public void UpdatePayment(int id){

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure update this? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateTxtDate = paymentDate.getText().toString();
                        String updateTxtDescription = paymentDescription.getText().toString();
                        float updateTxtPayment = Float.parseFloat(updatePayment.getText().toString());

                        if(updateTxtDate.isEmpty()){
                            Toast.makeText(context,"Date required.",Toast.LENGTH_LONG).show();
                        }else if(String.valueOf(updateTxtPayment).isEmpty()){
                            Toast.makeText(context,"Payment required.",Toast.LENGTH_LONG).show();
                        }else{
                            PaymentClass paymentClass = new PaymentClass(id,updateTxtDate,updateTxtDescription,updateTxtPayment);
                            int state = dataBaseHelper.updatePaymentDetails(paymentClass);
                            Toast.makeText(context,"Updated",Toast.LENGTH_SHORT);

                            startActivity(new Intent(context,MainActivity.class));
                        }


                    }
                });
                builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(context,MainActivity.class));
                    }
                });
                builder.show();
            }
        });

    }
    //get date
    public void datePicker(){
        paymentDate.setOnClickListener(new View.OnClickListener() {
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
                paymentDate.setText(date);

            }
        };
    }
    //when press back button of phone then go to main activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }
}