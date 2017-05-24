package com.example.kyosh.dacs;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import DatabaseAdapter.DatabaseAdapter;

public class Editkhachhang extends AppCompatActivity {
    EditText txtid,txtfirstname,txtlastname,txtphone,txtaddress;
    Button btnupdate,btnexit;
    ImageButton btncamera;
    final String DATABASE_NAME="jadecsdl.sqlite";
    SQLiteDatabase database;
    Bitmap photo;
    ImageView imgVedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editkhachhang);
        addControl();
        addEvent();
        show();
    }

    private void show() {
        String getid=getIntent().getStringExtra("updateID");
        String getfn=getIntent().getStringExtra("updatefirstname");
        String getln=getIntent().getStringExtra("updatelastname");
        String getph=getIntent().getStringExtra("uodatephone");
        String getadd=getIntent().getStringExtra("updateaddress");
        txtid.setText(getid);
        txtfirstname.setText(getfn);
        txtlastname.setText(getln);
        txtphone.setText(getph);
        txtaddress.setText(getadd);

    }
    public byte[] getByteArrBitmap(Bitmap bm)
    {
        ByteArrayOutputStream bstr=new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,bstr);
        byte[] b=bstr.toByteArray();
        return b;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1888){

            photo= (Bitmap) data.getExtras().get("data");
            imgVedit.setImageBitmap(photo);
        }

    }
    private void addEvent() {
        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcamera,1888);
            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback=new Intent(Editkhachhang.this,MainActivity.class);
                startActivity(intentback);
                finish();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database=DatabaseAdapter.initDatabase(Editkhachhang.this,DATABASE_NAME);
                final ContentValues cvupdate=new ContentValues();
                BitmapDrawable bmd=(BitmapDrawable) imgVedit.getDrawable();
                Bitmap bm=bmd.getBitmap();
                byte[] b=getByteArrBitmap(bm);

                cvupdate.put("IMAGE",b);
                cvupdate.put("FIRSTNAME",txtfirstname.getText().toString());
                cvupdate.put("LASTNAME",txtlastname.getText().toString());
                cvupdate.put("PHONE",txtphone.getText().toString());
                cvupdate.put("ADDRESS",txtaddress.getText().toString());


                final Dialog thongbao=new Dialog(Editkhachhang.this);
                thongbao.setContentView(R.layout.dialogthongbao);
                thongbao.show();
                TextView title= (TextView) thongbao.findViewById(R.id.txttitle);
                Button btnleft= (Button) thongbao.findViewById(R.id.btndialogleft);
                Button btnright= (Button) thongbao.findViewById(R.id.btndialogright);
                title.setText("Cập nhật dữ liệu cho khách hàng này !");
                btnleft.setText("Thoát");
                btnright.setText("cập nhật");
                btnleft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thongbao.dismiss();
                    }
                });
                btnright.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thongbao.dismiss();
                        database.update("Khachhang",cvupdate,"ID=?",new String[]{txtid.getText().toString()});
                        final Dialog thogbao=new Dialog(Editkhachhang.this);
                        thogbao.setContentView(R.layout.dialogthongbao);
                        thogbao.show();
                        TextView title= (TextView) thogbao.findViewById(R.id.txttitle);
                        Button btnright= (Button) thogbao.findViewById(R.id.btndialogright);
                        title.setText("Đã cập nhật !");
                        btnright.setText("OK");
                        btnright.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                thogbao.dismiss();
                                finish();
                                Intent intentback=new Intent(Editkhachhang.this,MainActivity.class);
                                startActivityForResult(intentback,50);
                            }
                        });

                    }
                });

            }
        });
    }

    private void addControl() {
        imgVedit= (ImageView) findViewById(R.id.profile_imageedit);
        btncamera= (ImageButton) findViewById(R.id.btneditcamera);
        txtid= (EditText) findViewById(R.id.edteditID);
        txtfirstname= (EditText) findViewById(R.id.edteditfirsname);
        txtlastname= (EditText) findViewById(R.id.edteditlastname);
        txtphone= (EditText) findViewById(R.id.edteditphone);
        txtaddress= (EditText) findViewById(R.id.edteditaddress);
        btnupdate= (Button) findViewById(R.id.btneditupdate);
        btnexit= (Button) findViewById(R.id.btneditexit);
    }

    @Override
    public void onBackPressed() {

    }
}
