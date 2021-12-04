package com.example.paidstatapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button AddBtn;
    private ListView ListView;
    public DataBaseHelper dataBaseHelper;
    private TextView CountWorkers;
    private List<WorkerClass> work;
    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        AddBtn = findViewById(R.id.addBtn);
        ListView = findViewById(R.id.listView);
        //CountWorkers = findViewById(R.id.countWorkers);
        dataBaseHelper = new DataBaseHelper(this);
        context = this;
        work = new ArrayList<>();

        int count = dataBaseHelper.CountWorkers();
        //CountWorkers.setText("There are " +count + " workers");


        addWorker();
        ViewList();
        ItemClickList();

    }
    //go to work details activity
    public void addWorker(){
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WorkDetailsActivity.class));
            }
        });
    }

    //view the all workers
    public void ViewList(){

        work = dataBaseHelper.getEveryone();
        WorkAdapter workAdapter = new WorkAdapter(context,R.layout.workers_main,work);
        ListView.setAdapter(workAdapter);

    }

    //click the item of list
    public void ItemClickList(){

        work = dataBaseHelper.getEveryone();
        Dialog dialog;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custome_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        Button stat = dialog.findViewById(R.id.statDialogBtn);
        Button paid = dialog.findViewById(R.id.paidDialogBtn);
        Button update = dialog.findViewById(R.id.updateDialogBtn);
        Button delete = dialog.findViewById(R.id.deleteDialogBtn);
        TextView name = dialog.findViewById(R.id.txtDialogName);

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WorkerClass workerClass = work.get(position);// get items
                name.setText(workerClass.getName());
                dialog.show();
                //all payments of clicking item(worker)
                stat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,StatisticsActivity.class);
                        intent.putExtra("Id",String.valueOf(workerClass.getId()));
                        intent.putExtra("Name",workerClass.getName());
                        startActivity(intent);
                    }
                });
                //to do payment
                paid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,PaymentActivity.class);
                        intent.putExtra("Id",String.valueOf(workerClass.getId()));
                        startActivity(intent);
                    }
                });
                //delete worker
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        AlertDialog.Builder build = new AlertDialog.Builder(context);
                        build.setTitle("Are you sure want to delete this worker?");
                        build.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBaseHelper.DeleteItem(workerClass.getId());
                                System.out.println(workerClass.getId());
                                Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context,MainActivity.class));
                            }
                        });
                        build.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(context,MainActivity.class));
                            }
                        });
                        build.show();
                    }
                });
                //update worker
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, UpdateActivity.class);
                        intent.putExtra("Id", String.valueOf(workerClass.getId()));
                        startActivity(intent);
                    }
                });

            }
        });

    }
    //when press back button of phone close the app
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}