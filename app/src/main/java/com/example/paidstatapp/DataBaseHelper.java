package com.example.paidstatapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    //worker table
    public static final String WORKER_TABLE = "WORKER_TABLE";
    public static final String COLUMN_WORKER_ID = "WORKER_ID";
    public static final String COLUMN_WORKER_NAME = "WORKER_NAME";
    public static final String COLUMN_WORKER_PHONENUM = "WORKER_PHONENUM";
    public static final String COLUMN_WORKER_ADDRESS = "WORKER_ADDRESS";
    public static final String COLUMN_IS_MASON = "IS_MASON";
    public static final String COLUMN_IS_PAINTER = "COLUMN_IS_PAINTER";
    public static final String COLUMN_IS_CARPENTER = "IS_CARPENTER";

    //payment table
    public static final String PAYMENT_TABLE = "PAYMENT_TABLE";
    public static final String COLUMN_PAYMENT_ID = "PAYMENT_ID";
    public static final String COLUMN_PAYMENT_DATE = "COLUMN_PAYMENT_DATE";
    public static final String COLUMN_PAYMENT_PAY = "COLUMN_PAYMENT_PAY";
    public static final String COLUMN_PAYMENT_DESCRIPTION = "COLUMN_PAYMENT_DESCRIPTION";
    public static final String COLUMN_WORKER_PAYMENT_ID = "COLUMN_WORKER_PAYMENT_ID";


    //Create Database
    public DataBaseHelper(@Nullable Context context) {
        super(context, "Worker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        WorkerClass workerClass = new WorkerClass();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PAYMENT_TABLE, workerClass.getName());

        String CreateWorkerTableStatement = "CREATE TABLE " + WORKER_TABLE + " (" + COLUMN_WORKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WORKER_NAME + " TEXT, " + COLUMN_WORKER_PHONENUM + " INT, " + COLUMN_WORKER_ADDRESS + " TEXT, " + COLUMN_IS_MASON + " BOOL," + COLUMN_IS_CARPENTER + " BOOL, " + COLUMN_IS_PAINTER + " BOOL)";
        String CreatePaymentTableStatement = "CREATE TABLE " + PAYMENT_TABLE + " (" + COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PAYMENT_DATE + " TEXT," + COLUMN_PAYMENT_DESCRIPTION + " TEXT, " + COLUMN_PAYMENT_PAY + " DECIMAL(7,2)," + COLUMN_WORKER_PAYMENT_ID + " INT, FOREIGN KEY(COLUMN_WORKER_PAYMENT_ID) REFERENCES WORKER_TABLE (COLUMN_WORKER_ID) )";
        db.execSQL(CreateWorkerTableStatement);
        db.execSQL(CreatePaymentTableStatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //Insert data to worker table
    public boolean addOne(WorkerClass workerClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORKER_NAME, workerClass.getName());
        cv.put(COLUMN_WORKER_PHONENUM, workerClass.getPhoneNumber());
        cv.put(COLUMN_WORKER_ADDRESS, workerClass.getAddress());
        cv.put(COLUMN_IS_MASON, workerClass.isMason());
        cv.put(COLUMN_IS_CARPENTER, workerClass.isCarpenter());
        cv.put(COLUMN_IS_PAINTER, workerClass.isPainter());

        long insert = db.insert(WORKER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Get all value from worker table for list view
    public List<WorkerClass> getEveryone() {
        List<WorkerClass> returnList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT * FROM " + WORKER_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);
        if ((cursor.moveToFirst())) {
            do {
                WorkerClass newWorker = new WorkerClass();
                newWorker.setId(cursor.getInt(0));
                newWorker.setName(cursor.getString(1));
                newWorker.setPhoneNumber(cursor.getInt(2));
                newWorker.setAddress(cursor.getString(3));
                newWorker.setMason(cursor.getInt(4) == 1 ? true : false);
                newWorker.setCarpenter(cursor.getInt(5) == 1 ? true : false);
                newWorker.setPainter(cursor.getInt(6) == 1 ? true : false);
                returnList.add(newWorker);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
    // get number of workers(rows) of worker table
    public int CountWorkers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + WORKER_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);
        return cursor.getCount();
    }
    //Delete data from worker table
    public void DeleteItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(WORKER_TABLE, COLUMN_WORKER_ID + " =?", new String[]{String.valueOf(id)});
        db.close();
    }

    //Get all value from worker table for update activity
    public WorkerClass getValue(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(WORKER_TABLE, new String[]{COLUMN_WORKER_ID, COLUMN_WORKER_NAME, COLUMN_WORKER_PHONENUM, COLUMN_WORKER_ADDRESS, COLUMN_IS_MASON, COLUMN_IS_CARPENTER, COLUMN_IS_PAINTER}, COLUMN_WORKER_ID + " =?", new String[]{String.valueOf(id)}, null, null, null);
        WorkerClass workerClass;
        if (cursor != null) {
            cursor.moveToFirst();
            workerClass = new WorkerClass(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1 ? true : false,
                    cursor.getInt(5) == 1 ? true : false,
                    cursor.getInt(6) == 1 ? true : false

            );
            return workerClass;

        }
        return null;
    }

    //update worker table
    public int updateDetails(WorkerClass workerClass) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WORKER_NAME, workerClass.getName());
        cv.put(COLUMN_WORKER_PHONENUM, workerClass.getPhoneNumber());
        cv.put(COLUMN_WORKER_ADDRESS, workerClass.getAddress());
        cv.put(COLUMN_IS_MASON, workerClass.isMason());
        cv.put(COLUMN_IS_CARPENTER, workerClass.isCarpenter());
        cv.put(COLUMN_IS_PAINTER, workerClass.isPainter());

        int status = db.update(WORKER_TABLE,cv, COLUMN_WORKER_ID + " =?", new String[]{String.valueOf(workerClass.getId())});
        db.close();
        return status;
    }

    //Insert data to payment table
    public boolean paymentOne(PaymentClass paymentClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PAYMENT_DATE, paymentClass.getPaymentDate());
        cv.put(COLUMN_PAYMENT_DESCRIPTION, paymentClass.getPaymentDescription());
        cv.put(COLUMN_PAYMENT_PAY, paymentClass.getPayment());
        cv.put(COLUMN_WORKER_PAYMENT_ID, paymentClass.getWorkerId());


        long insert = db.insert(PAYMENT_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }
    //Get all value from payment table for list view
    public List<PaymentClass> getAllPayments(int id) {

        List<PaymentClass> returnPaymentList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String queryString = "SELECT * FROM " + PAYMENT_TABLE + " WHERE " + COLUMN_WORKER_PAYMENT_ID + "=" + id;
        Cursor cursor = db.rawQuery(queryString, null);

        if ((cursor.moveToFirst())) {
            do {
                PaymentClass newPayment = new PaymentClass();
                newPayment.setPaymentId(cursor.getInt(0));
                newPayment.setPaymentDate(cursor.getString(1));
                newPayment.setPaymentDescription(cursor.getString(2));
                newPayment.setPayment(cursor.getFloat(3));
                newPayment.setWorkerId(cursor.getInt(4));

                returnPaymentList.add(newPayment);
            } while (cursor.moveToNext());
        } else {

        }

        cursor.close();
        db.close();
        return returnPaymentList;

    }

    // get sum of all payments
    public float getSumPayment(int id) {
        float results = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sumQuery = "SELECT SUM (" + COLUMN_PAYMENT_PAY + ") FROM " + PAYMENT_TABLE + " WHERE " + COLUMN_WORKER_PAYMENT_ID + "=" + id;
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst()) {
            results = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return results;

    }

    //Get all value from payment table for update activity
    public PaymentClass getPaymentValue(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(PAYMENT_TABLE, new String[]{COLUMN_PAYMENT_ID, COLUMN_PAYMENT_DATE, COLUMN_PAYMENT_DESCRIPTION, COLUMN_PAYMENT_PAY, COLUMN_WORKER_PAYMENT_ID}, COLUMN_PAYMENT_ID + " =?", new String[]{String.valueOf(id)}, null, null, null);
        PaymentClass paymentClass;
        if (cursor != null) {
            cursor.moveToFirst();
            paymentClass = new PaymentClass(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)

            );
            return paymentClass;

        }
        return null;
    }

    //update payment table
    public int updatePaymentDetails(PaymentClass paymentClass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PAYMENT_DATE, paymentClass.getPaymentDate());
        cv.put(COLUMN_PAYMENT_DESCRIPTION, paymentClass.getPaymentDescription());
        cv.put(COLUMN_PAYMENT_PAY, paymentClass.getPayment());

        int status = db.update(PAYMENT_TABLE, cv, COLUMN_PAYMENT_ID + " =?", new String[]{String.valueOf(paymentClass.getPaymentId())});
        db.close();
        return status;
    }

    //Delete data from payment table
    public void DeletePayment(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(PAYMENT_TABLE,COLUMN_PAYMENT_ID + " =?",new String[]{String.valueOf(id)});
        db.close();
    }
}
