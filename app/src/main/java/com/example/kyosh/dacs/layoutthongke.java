package com.example.kyosh.dacs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import java.util.ArrayList;
import java.util.Calendar;

import DatabaseAdapter.DatabaseAdapter;
import adapter.Adapterstatistic;
import model.thongke;

public class layoutthongke extends AppCompatActivity {
    ArrayList<thongke>dsthongke;
    Adapterstatistic Adaptertk;
    ListView listDate;
    TextView txtmonth,txtyear,txtien,txtsokhach;
    ImageButton btncalendar;
    Calendar calendar=Calendar.getInstance();
    final String DATABASE_NAME="jadecsdl.sqlite";
    SQLiteDatabase database;


    SimpleDateFormat spdyear=new SimpleDateFormat("yyyy");
    SimpleDateFormat spdmonth=new SimpleDateFormat("MM");
    SimpleDateFormat spddate=new SimpleDateFormat("dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutthongke);
        addControl();
        addEvent();
        showCurrent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarthongke);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void showCurrent() {
        txtmonth.setText("Tháng "+(spdmonth.format(calendar.getTime())));
        txtyear.setText(spdyear.format(calendar.getTime()));

        String month=spdmonth.format(calendar.getTime());
        String year=spdyear.format(calendar.getTime());

        String chuoi1=month+"/"+year;

        String mx="";
        try{
            database= DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT sum(h.MONEY) FROM Hoadon h WHERE h.ID and h.DATE like"+ "'"+"%"+chuoi1+"%"+"'" ,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx= formatNumber(cursor.getInt(0));
                }
            //  cursor.close();
            txtien.setText(mx+" vnđ");
        }
        catch(Exception e){
            txtien.setText("0 vnđ");
        }


        String mx1="";
        try{
            database= DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT count(BILLNUMBER) FROM Hoadon h WHERE h.DATE like"+ "'"+"%"+chuoi1+"%"+"'" ,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx1= cursor.getString(0);
                }
            //  cursor.close();
            txtsokhach.setText("Tổng số hóa đơn :"+mx1);
        }
        catch(Exception e){
            txtsokhach.setText("Tổng số hóa đơn : 0");

        }




        database=DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT h.DATE,Count(DATE) as sohoadon FROM Hoadon h WHERE h.DATE like "+"'"+"%"+chuoi1+"%"+"'"+" GROUP by DATE" ,null);
        Adaptertk.clear();
        while (cursor.moveToNext()){
            String date=cursor.getString(cursor.getColumnIndex("DATE"));
            String total=cursor.getString(cursor.getColumnIndex("sohoadon"));
            thongke thongke=new thongke();
            thongke.setDate(date);
            thongke.setCustome_count("Tổng số hóa đơn: "+total);
            Adaptertk.add(thongke);
        }
        Adaptertk.notifyDataSetChanged();

    }
    ArrayList<String> ten;
    ArrayAdapter<String>adapter;
    ListView listtk;
    private void addEvent() {


        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();

            }
        });
    }
    String ngay="";
    String thang="";
    String nam="";
    public void datePicker(){
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DATE,dayOfMonth);
                txtmonth.setText("Thág "+(spdmonth.format(calendar.getTime())));
                txtyear.setText(spdyear.format(calendar.getTime()));

                ngay=spddate.format(calendar.getTime());
                thang=spdmonth.format(calendar.getTime());
                nam=spdyear.format(calendar.getTime());

                txtien.setText(tongtien()+" vnđ");
                txtsokhach.setText("Tổng số hóa đơn: "+tongbill());
                showdate();


            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                layoutthongke.this,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private String tongtien(){
        String chuoimonthyear=thang+"/"+nam;
        String mx="";
        try{
            database= DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT sum(h.MONEY) FROM Hoadon h WHERE h.ID and h.DATE like"+ "'"+"%"+chuoimonthyear+"%"+"'" ,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx= formatNumber(cursor.getInt(0));
                }
            //  cursor.close();
            return mx;
        }
        catch(Exception e){

            return "";
        }
    }
    private String tongbill(){
        String chuoimonthyear=thang+"/"+nam;
        String mx="";
        try{
            database= DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT count(BILLNUMBER) FROM Hoadon h WHERE h.DATE like"+ "'"+"%"+chuoimonthyear+"%"+"'" ,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx= cursor.getString(0);
                }
            //  cursor.close();
            return mx;
        }
        catch(Exception e){

            return "";
        }
    }

    public static String formatNumber(int number) {
        if (number==1000) {
            return String.valueOf(number);
        }
        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp;
        } catch (Exception e) {
            return "";
        }
    }
    private void showdate(){
        String chuoi1=thang+"/"+nam;
        String chuoi2=ngay+"/"+thang+"/"+nam;
        database=DatabaseAdapter.initDatabase(layoutthongke.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT h.DATE,Count(DATE) as sohoadon FROM Hoadon h WHERE h.DATE like "+"'"+"%"+chuoi1+"%"+"'"+" GROUP by DATE" ,null);
        Adaptertk.clear();
        while (cursor.moveToNext()){
            String date=cursor.getString(cursor.getColumnIndex("DATE"));
            String total=cursor.getString(cursor.getColumnIndex("sohoadon"));
            thongke thongke=new thongke();
            thongke.setDate(date);
            thongke.setCustome_count("Tổng số hóa đơn: "+total);
            Adaptertk.add(thongke);
        }
        Adaptertk.notifyDataSetChanged();

    }
    private void addControl() {
        txtmonth= (TextView) findViewById(R.id.txtmonth);
        txtyear= (TextView) findViewById(R.id.txtyear);
        txtien= (TextView) findViewById(R.id.txtmoney);
        txtsokhach= (TextView) findViewById(R.id.txttonghoadon);
        btncalendar= (ImageButton) findViewById(R.id.btncalendar);

        listDate= (ListView) findViewById(R.id.list_thongke);
        dsthongke=new ArrayList<>();
        Adaptertk=new Adapterstatistic(layoutthongke.this,R.layout.item_thongke,dsthongke);
        listDate.setAdapter(Adaptertk);


    }
}
