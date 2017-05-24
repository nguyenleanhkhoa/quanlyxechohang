package com.example.kyosh.dacs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import DatabaseAdapter.DatabaseAdapter;

public class themhoadon extends AppCompatActivity {

    EditText edthoadon,edtdate,edtphone,edttime,edtdiemdi,edtdiemden,edttype,edtnote,edtmoney,txtid,txtten;
    ImageButton imgbtndate,imgbtntime;
    Button btnthemhd,btnexit;
    Intent intenthd;
    final String DATABASE_NAME="jadecsdl.sqlite";
    SQLiteDatabase database;
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat spddate=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat spdtime=new SimpleDateFormat("HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themhoadon);
        addcontrol();
        addevent();
        show();
    }

    private void show() {
        intenthd=getIntent();
        edtphone.setText(intenthd.getStringExtra("givephone"));
        database=DatabaseAdapter.initDatabase(themhoadon.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT ID,PHONE,(FIRSTNAME||' '||LASTNAME) as TEN FROM Khachhang WHERE PHONE like"+"'"+"%"+edtphone.getText().toString()+"%"+"'" ,null);
        while(cursor.moveToNext()){
            txtid.setText(cursor.getString(cursor.getColumnIndex("ID")));
            txtten.setText(cursor.getString(cursor.getColumnIndex("TEN")));
        }
        int maxbill=getMaxBill()+1;
        edthoadon.setText(String.valueOf(maxbill));



        edtdate.requestFocus();
    }
    public static String formatNumber(int number) {
        if (number== 1000) {
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
    private int getMaxBill(){
        String getid=txtid.getText().toString();
        int mx=-1;
        try{
            database= DatabaseAdapter.initDatabase(themhoadon.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT max(BILLNUMBER) from hoadon h WHERE h.ID ="+getid,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx= cursor.getInt(0);
                }
            //  cursor.close();
            return mx;
        }
        catch(Exception e){

            return -1;
        }
    }
    private void addevent() {

        imgbtndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }

            private void datePicker() {
                DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DATE,dayOfMonth);
                        edtdate.setText(spddate.format(calendar.getTime()));


                    }
                };
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        themhoadon.this,
                        callback,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE));
                datePickerDialog.show();
            }
        });
        imgbtntime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }

            private void timePicker() {
                TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        edttime.setText(spdtime.format(calendar.getTime()));
                    }
                };
                TimePickerDialog timePickerDialog=new TimePickerDialog(themhoadon.this,
                        callback,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);
                timePickerDialog.show();
            }
        });



    }

    public void btnthemhd(View v){
        if(txtid.getText().toString().equalsIgnoreCase("") || edthoadon.getText().toString().equalsIgnoreCase("")||edtdate.getText().toString().equalsIgnoreCase("")
                || edttime.getText().toString().equalsIgnoreCase("")||edtdiemdi.getText().toString().equalsIgnoreCase("")||edtdiemden.getText().toString().equalsIgnoreCase("")
                || edttype.getText().toString().equalsIgnoreCase("")||edtnote.getText().toString().equalsIgnoreCase("")||edtmoney.getText().toString().equalsIgnoreCase("")){
            final Dialog diaerr=new Dialog(themhoadon.this);
            diaerr.setContentView(R.layout.dialogthongbao);
            diaerr.show();
            TextView title= (TextView) diaerr.findViewById(R.id.txttitle);
            Button btnright= (Button) diaerr.findViewById(R.id.btndialogright);
            Button btnleft= (Button) diaerr.findViewById(R.id.btndialogleft);
            title.setText("Bạn chưa nhập đủ dữ liệu !");
            btnright.setText("Cancel");
            btnleft.setText("");
            btnright.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diaerr.dismiss();
                }
            });
        }
        else{
            ContentValues cvhd=new ContentValues();
            cvhd.put("ID",txtid.getText().toString());
            cvhd.put("BILLNUMBER",edthoadon.getText().toString());
            cvhd.put("DATE",edtdate.getText().toString());
            cvhd.put("TIME",edttime.getText().toString());
            cvhd.put("ORIGIN",edtdiemdi.getText().toString());
            cvhd.put("FINAL",edtdiemden.getText().toString());
            cvhd.put("TYPE",edttype.getText().toString());
            cvhd.put("NOTE",edtnote.getText().toString());
            int money= Integer.parseInt(edtmoney.getText().toString());
            String rs = formatNumber(money);
            cvhd.put("MONEY",rs+"đ");
            database.insert("Hoadon",null,cvhd);
            Toast.makeText(themhoadon.this, "success", Toast.LENGTH_SHORT).show();
        }


    }
    public void btnexit(View v){
        Dialog diaexit=new Dialog(themhoadon.this);
        diaexit.setContentView(R.layout.dialogthongbao);
        diaexit.show();
        TextView titleexit= (TextView) diaexit.findViewById(R.id.txttitle);
        Button btnrightexit= (Button) diaexit.findViewById(R.id.btndialogright);
        Button btnleftexit= (Button) diaexit.findViewById(R.id.btndialogleft);
        titleexit.setText("Bạn có muốn thoát !");
        btnrightexit.setText("Thoát");
        btnleftexit.setText("");
        btnrightexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void addcontrol() {
        txtid= (EditText) findViewById(R.id.txtIDaddhoadon);
        txtten= (EditText) findViewById(R.id.txtTenaddhoadon);
        edthoadon= (EditText) findViewById(R.id.edthdaddhoadon);
        edtphone= (EditText) findViewById(R.id.txtphoneaddhoadon);
        edtdate= (EditText) findViewById(R.id.edtdateaddhoadon);
        edttime= (EditText) findViewById(R.id.edttimeaddhoadon);
        edtdiemdi= (EditText) findViewById(R.id.edtoringinaddhodon);
        edtdiemden= (EditText) findViewById(R.id.edtfinaladdhoadon);
        edttype= (EditText) findViewById(R.id.edtnoteaddhoadon);
        edtnote= (EditText) findViewById(R.id.edtnoteaddhoadon);
        edtmoney= (EditText) findViewById(R.id.edttienaddhoadon);
        imgbtndate= (ImageButton) findViewById(R.id.imgbtnaddcal);
        imgbtntime= (ImageButton) findViewById(R.id.imgbtnaddtime);
        btnthemhd= (Button) findViewById(R.id.btnaddhoadonnew);
        btnexit= (Button) findViewById(R.id.btnthoataddhoadon);
    }
}
