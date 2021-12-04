package com.example.paidstatapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class UpdateActivity extends AppCompatActivity {
    private Button updateBtn, updateClearBtn,backBtn;
    private EditText updateName,updatePhone,updateAddress;
    private Switch updateMason,updateCarpenter,updatePainter;
    private DataBaseHelper dataBaseHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Objects.requireNonNull(getSupportActionBar()).hide();


        context = this;
        updateBtn = findViewById(R.id.btnUpdate);
        updateClearBtn = findViewById(R.id.btnUpdateClear);
        updateName = findViewById(R.id.updateName);
        updatePhone = findViewById(R.id.updatePhone);
        updateAddress = findViewById(R.id.updateAddress);
        updateMason = findViewById(R.id.updateSwtchMason);
        updateCarpenter = findViewById(R.id.updateSwtchCarpenter);
        updatePainter = findViewById(R.id.updateSwtchPainter);
        backBtn = findViewById(R.id.btnUpdateBack);
        dataBaseHelper = new DataBaseHelper(this);
        final String id = getIntent().getStringExtra("Id");


        getInfo();
        back();
        UpdateData();

    }
//update data of worker
    private void UpdateData() {
        final String id = getIntent().getStringExtra("Id");

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure want to update this worker ? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {




                        String updateTxtName = updateName.getText().toString();
                        int updateTxtPhone = Integer.parseInt(updatePhone.getText().toString());
                        String updateTxtAddress = updateAddress.getText().toString();
                        boolean updateIsMason = updateMason.isChecked();
                        boolean updateIsCarpenter = updateCarpenter.isChecked();
                        boolean updateIsPainter = updatePainter.isChecked();

                        if(updateTxtName.isEmpty()){
                            Toast.makeText(context,"Name Required.",Toast.LENGTH_LONG).show();
                        }else if(String.valueOf(updateTxtPhone).isEmpty()) {
                            Toast.makeText(context, "Phone Number Required.", Toast.LENGTH_LONG).show();
                        }else if(updatePhone.getText().toString().length() < 9 || updatePhone.getText().toString().length() > 10 ){
                            Toast.makeText(context,"Please enter valid phone Number.",Toast.LENGTH_LONG).show();
                        }
                        else if(updateTxtAddress.isEmpty()){
                            Toast.makeText(context,"Address Required.",Toast.LENGTH_LONG).show();
                        }else if(updateIsMason == false && updateIsCarpenter == false && updateIsPainter == false){
                            Toast.makeText(context,"Category Required.",Toast.LENGTH_LONG).show();
                        }else{
                            WorkerClass workerClass = new WorkerClass(Integer.parseInt(id),updateTxtName,updateTxtPhone,updateTxtAddress,updateIsMason,updateIsCarpenter,updateIsPainter);
                            int state = dataBaseHelper.updateDetails(workerClass);
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
    //get data of worker
    public void getInfo(){
        final String id = getIntent().getStringExtra("Id");
        WorkerClass workerClass = dataBaseHelper.getValue(Integer.parseInt(id));

        updateName.setText(workerClass.getName());
        updatePhone.setText(String.valueOf(workerClass.getPhoneNumber()));
        updateAddress.setText(workerClass.getAddress());
        updateMason.setChecked(workerClass.isMason());
        updateCarpenter.setChecked(workerClass.isCarpenter());
        updatePainter.setChecked(workerClass.isPainter());


    }
    //back button of update activity
    public void back(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });
    }
    //when press back button of phone then go to main activity
    @Override
    public void onBackPressed() {
        startActivity(new Intent(context,MainActivity.class));
    }
}