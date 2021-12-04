package com.example.paidstatapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;


public class WorkDetailsActivity extends AppCompatActivity {

    private Button ClearBtn,SaveBtn;
    private EditText txtName,txtAddress,txtPhone;
    private Switch mason,carpenter,painter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ClearBtn = findViewById(R.id.btnClear);
        txtName = findViewById(R.id.editTxtName);
        txtAddress = findViewById(R.id.editTxtAddress);
        txtPhone = findViewById(R.id.editTxtPhone);
        mason = findViewById(R.id.swtchMason);
        carpenter = findViewById(R.id.swtchCarpenter);
        painter = findViewById(R.id.swtchPainter);
        SaveBtn = findViewById(R.id.btnSave);
        context = WorkDetailsActivity.this;

        
        clear();
        saveData();

    }
//save data of worker
    private void saveData(){

        SaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WorkerClass workerClass = new WorkerClass();
                if(txtName.getText().toString().isEmpty()){
                    Toast.makeText(WorkDetailsActivity.this,"Name Required.",Toast.LENGTH_LONG).show();

                }else if(txtPhone.getText().toString().isEmpty()) {
                    Toast.makeText(WorkDetailsActivity.this, "Phone Number Required.", Toast.LENGTH_LONG).show();
                }else if(txtPhone.getText().toString().length() < 9 || txtPhone.getText().toString().length() > 10){
                    Toast.makeText(WorkDetailsActivity.this,"Please enter valid phone Number.",Toast.LENGTH_LONG).show();
                }
                else if(txtAddress.getText().toString().isEmpty()){
                    Toast.makeText(WorkDetailsActivity.this,"Address Required.",Toast.LENGTH_LONG).show();
                }else if(mason.isChecked() == false && carpenter.isChecked() == false && painter.isChecked() == false){
                    Toast.makeText(WorkDetailsActivity.this,"Category Required.",Toast.LENGTH_LONG).show();
                }else{
                    try {


                        workerClass = new WorkerClass(-1, txtName.getText().toString(), Integer.parseInt(txtPhone.getText().toString()), txtAddress.getText().toString(), mason.isChecked(), carpenter.isChecked(), painter.isChecked());
                        //Toast.makeText(WorkDetailsActivity.this, workerClass.toString(), Toast.LENGTH_LONG).show();


                    }catch (Exception e){

                        Toast.makeText(WorkDetailsActivity.this,"Please Enter the Details".toString(),Toast.LENGTH_SHORT).show();
//                        System.out.println(e);
//                        workerClass = new WorkerClass(-1,"Error",0,"Error",false,false,false);
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(WorkDetailsActivity.this);
                    boolean success = dataBaseHelper.addOne(workerClass);

                    Toast.makeText(WorkDetailsActivity.this,"Success" +success,Toast.LENGTH_SHORT);

                    startActivity(new Intent(context,MainActivity.class));
                }
            }
        });
    }

    //clear all data
    private void clear(){

        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setText("");
                txtAddress.setText("");
                txtPhone.setText("");
                mason.setChecked(false);
                carpenter.setChecked(false);
                painter.setChecked(false);

                Toast.makeText(WorkDetailsActivity.this,"Cleared",Toast.LENGTH_SHORT).show();
            }
        });
    }
    //when press back button of phone then go to main activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }
}