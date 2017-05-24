package com.example.kyosh.dacs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.EntityIterator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import DatabaseAdapter.DatabaseAdapter;

import static android.R.attr.data;

public class addKhachhang extends AppCompatActivity {
    EditText edtID,edtbill,edtfirstname,edtlastname,edtaddress,edtphone,edtdate,edttime,edtorigin,edtfinal,edtmoney,edtype,edtnote;
    Button btnadd,btnexit;
    ImageButton btncamera,btncalendar,btntime;
    ImageView imageprofile;
    Bitmap photo;
    byte[] bytearray;
    String imgDecodableString;
    FileOutputStream fileoutputStream=null;

    final String DATABASE_NAME="jadecsdl.sqlite";
    SQLiteDatabase database;
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat spddate=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat spdtime=new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khachhang);
        addcontrol();
        addevent();
        String id=getIntent().getStringExtra("maxID");
        String sdt=getIntent().getStringExtra("sdt");
        edtphone.setText(sdt);
        edtID.setText(id);
        edtfirstname.requestFocus();
        edtbill.setText("1");
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
    /// hàm lấy hình
    public byte[] getByteArrBitmap(Bitmap bm)
    {
        ByteArrayOutputStream bstr=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,bstr);
        byte[] b=bstr.toByteArray();
        return b;
    }

    private void addevent() {


        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera,1888);
            }
        });
       btnadd.setOnClickListener(   new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.N)
           @Override
           public void onClick(View v) {
               if( edtID.getText().toString().equalsIgnoreCase("")||edtbill.getText().toString().equalsIgnoreCase("")
               ||edtfirstname.getText().toString().equalsIgnoreCase("")||edtlastname.getText().toString().equalsIgnoreCase("")||
                       edtaddress.getText().toString().equalsIgnoreCase("")||edtphone.getText().toString().equalsIgnoreCase("")||
                       edtdate.getText().toString().equalsIgnoreCase("")||edttime.getText().toString().equalsIgnoreCase("")||
                       edtorigin.getText().toString().equalsIgnoreCase("")||edtfinal.getText().toString().equalsIgnoreCase("")||
                       edtmoney.getText().toString().equalsIgnoreCase("")||edtype.getText().toString().equalsIgnoreCase("")||
                       edtnote.getText().toString().equalsIgnoreCase("")){
                   final Dialog diaerrie=new Dialog(addKhachhang.this);
                   diaerrie.setContentView(R.layout.dialogthongbao);
                   diaerrie.show();
                   TextView txttile= (TextView) diaerrie.findViewById(R.id.txttitle);
                   Button btnright= (Button) diaerrie.findViewById(R.id.btndialogright);
                   Button btnleft= (Button) diaerrie.findViewById(R.id.btndialogleft);
                   txttile.setText("Bạn chưa nhập đủ dữ liệu");
                   btnright.setText("Cancel");
                   btnleft.setText("");
                   btnright.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           diaerrie.dismiss();
                       }
                   });
               }else {

                   database=DatabaseAdapter.initDatabase(addKhachhang.this,DATABASE_NAME);
                   Intent intent=getIntent();
                   //lấy hình
                   BitmapDrawable bmd=(BitmapDrawable) imageprofile.getDrawable();
                   Bitmap bm=bmd.getBitmap();
                   byte[] b=getByteArrBitmap(bm);

                   intent.putExtra("image",b);
                   intent.putExtra("ID",edtID.getText().toString());
                   intent.putExtra("billnumber",edtbill.getText().toString());
                   intent.putExtra("firstname",edtfirstname.getText().toString());
                   intent.putExtra("lastname",edtlastname.getText().toString());
                   intent.putExtra("address",edtaddress.getText().toString());
                   intent.putExtra("phone",edtphone.getText().toString());
                   intent.putExtra("date",edtdate.getText().toString());
                   intent.putExtra("time",edttime.getText().toString());
                   intent.putExtra("originpoint","điểm đi:"+edtorigin.getText().toString());
                   intent.putExtra("finalpoint","điểm đến:"+edtfinal.getText().toString());

                   //int money= Integer.parseInt(edtmoney.getText().toString());
                   // String rs = formatNumber(money);
                   intent.putExtra("money",edtmoney.getText().toString());
                   intent.putExtra("type",edtype.getText().toString());
                   intent.putExtra("note",edtnote.getText().toString());
                   setResult(3,intent);

                   final Dialog thogbao=new Dialog(addKhachhang.this);
                   thogbao.setContentView(R.layout.dialogthongbao);
                   thogbao.show();
                   TextView title= (TextView) thogbao.findViewById(R.id.txttitle);
                   Button btnright= (Button) thogbao.findViewById(R.id.btndialogright);
                   title.setText("Đã thêm khách hàng !");
                   btnright.setText("OK");
                   btnright.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           thogbao.dismiss();
                           finish();
                       }
                   });
               }



          }
       });
        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });
        btntime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });

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
        TimePickerDialog timePickerDialog=new TimePickerDialog(addKhachhang.this,
                callback,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
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
                addKhachhang.this,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==1888){

                photo= (Bitmap) data.getExtras().get("data");
                imageprofile.setImageBitmap(photo);
            }

    }

    private void addcontrol() {
        btnexit= (Button) findViewById(R.id.btncancle);
        imageprofile= (ImageView) findViewById(R.id.profile_image);
        btncamera= (ImageButton) findViewById(R.id.btneditcamera);
        edtID= (EditText) findViewById(R.id.edtID);
        edtbill= (EditText) findViewById(R.id.edtbillnum);
        edtfirstname= (EditText) findViewById(R.id.edtfirsname);
        edtlastname= (EditText) findViewById(R.id.edtlastname);
        edtaddress= (EditText) findViewById(R.id.edtaddress);
        edtphone= (EditText) findViewById(R.id.edtphone);
        edtdate= (EditText) findViewById(R.id.edtdate);
        edttime= (EditText) findViewById(R.id.edttime);
        edtorigin= (EditText) findViewById(R.id.edtorigin);
        edtfinal= (EditText) findViewById(R.id.edtfinal);
        edtmoney= (EditText) findViewById(R.id.edtmoney);
        edtype= (EditText) findViewById(R.id.edttype);
        edtnote= (EditText) findViewById(R.id.edtnote);
        btnadd= (Button) findViewById(R.id.btnsave);
        btncalendar= (ImageButton) findViewById(R.id.imgcalendar);
        btntime= (ImageButton) findViewById(R.id.imgtime);
        calendar=Calendar.getInstance();

    }

    @Override
    public void onBackPressed() {

    }
}
