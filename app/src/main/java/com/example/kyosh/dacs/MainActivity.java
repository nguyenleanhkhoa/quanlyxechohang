package com.example.kyosh.dacs;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import DatabaseAdapter.DatabaseAdapter;
import adapter.AdapterCus;
import adapter.Adapteritem;
import model.item;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<item>dskhachhang;
    Adapteritem adapteritem;

    ListView listcustome;
    ArrayList<item>dscustome;
    AdapterCus adapterCus;
    SearchView searchview1;
    SearchView searchviewcus;
    TabHost tabHost;

    ListView listitem;
    final String DATABASE_NAME="jadecsdl.sqlite";
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addcontrol();
        addEventClickitem();
        ShowDatabase();
        showCus();
        clickid();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentadd();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setTabColor(tabHost);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_thongke) {
            Intent intentthongke=new Intent(MainActivity.this,layoutthongke.class);
            startActivity(intentthongke);
        }else if(id==R.id.menu_Info){
            Intent inteninf=new Intent(MainActivity.this,information.class);
            startActivity(inteninf);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String getValue(String mx){

        try{
            database= DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT * FROM Hoadon h WHERE h.ID= "+mx,new String [] {});
            if (cursor != null)
                if(cursor.moveToFirst())
                {
                    mx= cursor.getString(cursor.getColumnIndex("BILLNUMBER"));
                }
            //  cursor.close();
            return mx;
        }
        catch(Exception e){

            return "";
        }
    }

    private void searchdataOnCus(String Cus){
        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT k.ID,k.IMAGE,k.FIRSTNAME,k.LASTNAME,k.ADDRESS,k.PHONE ,(k.ID||' '||k.FIRSTNAME||' '||k.LASTNAME||' '||k.ADDRESS||' '||k.PHONE) as data FROM Khachhang k WHERE data like+ "+"'"+"%"+Cus+"%"+"'",null);
        adapterCus.clear();
        while (cursor.moveToNext()){
            byte[] IMAGE=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            String lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            String Address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone=cursor.getString(cursor.getColumnIndex("PHONE"));

            item kh=new item();
            kh.setAvata(IMAGE);
            kh.setID(Integer.parseInt(ID));
            kh.setFirstname(firstname);
            kh.setLastname(lastname);
            kh.setAddress(Address);
            kh.setPhone(String.valueOf(Long.parseLong(Phone)));

//sort
            dscustome.add(0,kh);

        }
        adapterCus.notifyDataSetChanged();

    }
    private void searchdata(String data){

        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT k.FIRSTNAME ,k.LASTNAME,k.IMAGE,k.ID,k.ADDRESS,k.PHONE,h.BILLNUMBER,h.DATE,h.TIME,h.ORIGIN,h.FINAL,h.TYPE,h.NOTE,h.MONEY,(k.FIRSTNAME  ||' '||k.LASTNAME||' '||k.ID ||' '||k.ADDRESS ||' '||k.PHONE ||' '||h.BILLNUMBER ||' '||h.DATE ||' '||h.TIME ||' '||h.ORIGIN ||' '||h.FINAL ||' '||h.TYPE ||' '||h.NOTE ||' '||h.MONEY ||' '|| k.FIRSTNAME ||' '||k.LASTNAME) as data FROM Khachhang k ,Hoadon h WHERE k.ID=h.ID and data like"+ "'"+"%"+data+"%"+"'" ,null);
        adapteritem.clear();
        adapterCus.clear();
        while (cursor.moveToNext()){
            byte[] IMAGE=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            String lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            String Address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone=cursor.getString(cursor.getColumnIndex("PHONE"));
            String date=cursor.getString(cursor.getColumnIndex("DATE"));
            String time=cursor.getString(cursor.getColumnIndex("TIME"));
            String origin=cursor.getString(cursor.getColumnIndex("ORIGIN"));
            String Final=cursor.getString(cursor.getColumnIndex("FINAL"));
            String Type=cursor.getString(cursor.getColumnIndex("TYPE"));
            String note=cursor.getString(cursor.getColumnIndex("NOTE"));
            String money=formatNumber(cursor.getInt(cursor.getColumnIndex("MONEY")));
            String bill1=cursor.getString(cursor.getColumnIndex("BILLNUMBER"));


            item kh=new item();
            kh.setAvata(IMAGE);
            kh.setID(Integer.parseInt(ID));
            kh.setFirstname(firstname);
            kh.setLastname(lastname);
            kh.setAddress(Address);
            kh.setPhone(String.valueOf(Long.parseLong(Phone)));
            kh.setBillnum(Long.parseLong(bill1));
            kh.setDate(date);
            kh.setTime(time);
            kh.setOriginpoint("Điểm đi: "+origin);
            kh.setFinalpoint("Điểm đến: "+Final);
            kh.setType(Type);
            kh.setNote(note);
            kh.setMoney(money+"đ");
//sort
            dskhachhang.add(0,kh);
        }
        adapteritem.notifyDataSetChanged();
    }


    private void searchphone(String phone){
        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT k.IMAGE,k.ID,k.FIRSTNAME,k.LASTNAME,k.ADDRESS,k.PHONE,h.BILLNUMBER,h.DATE,h.TIME,h.ORIGIN,h.FINAL,h.TYPE,h.NOTE,h.MONEY FROM Khachhang k ,Hoadon h WHERE k.ID=h.ID and k.PHONE like +" +"'"+phone+"'" ,null);
        adapteritem.clear();
        while (cursor.moveToNext()){
            String IMAGE=cursor.getString(cursor.getColumnIndex("IMAGE"));
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            String lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            String Address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone=cursor.getString(cursor.getColumnIndex("PHONE"));
            String date=cursor.getString(cursor.getColumnIndex("DATE"));
            String time=cursor.getString(cursor.getColumnIndex("TIME"));
            String origin=cursor.getString(cursor.getColumnIndex("ORIGIN"));
            String Final=cursor.getString(cursor.getColumnIndex("FINAL"));
            String Type=cursor.getString(cursor.getColumnIndex("TYPE"));
            String note=cursor.getString(cursor.getColumnIndex("NOTE"));
            String money=cursor.getString(cursor.getColumnIndex("MONEY"));
            String bill1=cursor.getString(cursor.getColumnIndex("BILLNUMBER"));


            item kh=new item();
            kh.setID(Integer.parseInt(ID));
            kh.setFirstname(firstname);
            kh.setLastname(lastname);
            kh.setAddress(Address);
            kh.setPhone(String.valueOf(Long.parseLong(Phone)));
            kh.setBillnum(Long.parseLong(bill1));
            kh.setDate(date);
            kh.setTime(time);
            kh.setOriginpoint(origin);
            kh.setFinalpoint(Final);
            kh.setType(Type);
            kh.setNote(note);
            kh.setMoney(money);
//sort
            dskhachhang.add(0,kh);
        }
        adapteritem.notifyDataSetChanged();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        if(requestCode == 9 && resultCode == 3){


            ContentValues cvkh=new ContentValues();
            cvkh.put("IMAGE",data.getByteArrayExtra("image"));
            cvkh.put("ID",data.getStringExtra("ID"));
            cvkh.put("FIRSTNAME",data.getStringExtra("firstname"));
            cvkh.put("LASTNAME",data.getStringExtra("lastname"));
            cvkh.put("ADDRESS",data.getStringExtra("address"));
            cvkh.put("PHONE",data.getStringExtra("phone"));
            database.insert("Khachhang",null,cvkh);
            ContentValues cvbill=new ContentValues();


            cvbill.put("ID",data.getStringExtra("ID"));
            cvbill.put("BILLNUMBER",data.getStringExtra("billnumber"));
            cvbill.put("DATE",data.getStringExtra("date"));
            cvbill.put("TIME",data.getStringExtra("time"));
            cvbill.put("FINAL",data.getStringExtra("finalpoint"));
            cvbill.put("ORIGIN",data.getStringExtra("originpoint"));
            cvbill.put("TYPE",data.getStringExtra("type"));
            cvbill.put("NOTE",data.getStringExtra("note"));
            cvbill.put("MONEY",data.getStringExtra("money"));

            database.insert("Hoadon",null,cvbill);
            adapteritem.clear();
            ShowDatabase();

        }
        if(resultCode==50){
            showCus();
        }



    }
    private int getMaxID(){
        int mx=-1;
        try{
            database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("SELECT max(ID) from Khachhang ",new String [] {});
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

    private void intentadd() {

        final Dialog diaten=new Dialog(MainActivity.this);
        diaten.setContentView(R.layout.addhoadon);
        diaten.show();
        final EditText txtauto= (EditText) diaten.findViewById(R.id.txtAutocomplete);

        Button btnthem= (Button) diaten.findViewById(R.id.btnaddnew);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaten.dismiss();
                database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
                Cursor cursor=database.rawQuery("SELECT * FROM Khachhang ",null);
                final String txtphone= txtauto.getText().toString();
                while(cursor.moveToNext()){
                    try{
                        if(txtphone.equals(cursor.getString(5))){

                            final Dialog diaerror=new Dialog(MainActivity.this);
                            diaerror.setContentView(R.layout.dialogthongbao);
                            diaerror.show();

                            TextView txttile= (TextView) diaerror.findViewById(R.id.txttitle);
                            final Button btnright= (Button) diaerror.findViewById(R.id.btndialogright);
                            Button btnleft= (Button) diaerror.findViewById(R.id.btndialogleft);

                            txttile.setText("Số điện thoại đã tồn tại");
                            btnright.setText("Thêm hóa đơn");
                            btnleft.setText("thoát");
                            btnright.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnright.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent=new Intent(MainActivity.this,themhoadon.class);
                                            intent.putExtra("givephone",txtphone);
                                            startActivity(intent);
                                            diaerror.dismiss();
                                        }
                                    });
                                }
                            });
                            btnleft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    diaerror.dismiss();
                                }
                            });
                            break;

                        }
                        if(!txtphone.equals(cursor.getString(5))){
                            int max=getMaxID()+1;
                            String maxi= String.valueOf(max);
                            Intent intent=new Intent(MainActivity.this,addKhachhang.class);
                            intent.putExtra("sdt",txtphone);
                            intent.putExtra("maxID",maxi);
                            startActivityForResult(intent,9);
                            diaten.dismiss();
                            break;
                        }

                    }catch (Exception ex){

                    }
                }
            }
        });
    }

    private String[] AdapterforAuto() {
        String nx="";
        try{
            // ArrayList<String>ds=new ArrayList<>();
            database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
            Cursor cursor=database.rawQuery("Select k.PHONE from Khachhang k",null);
            while(cursor.moveToNext()){
                nx=cursor.getString(cursor.getColumnIndex("PHONE"));
            }
            return new String[]{nx};
        }catch (Exception ex){

        }
        return new String[]{nx = ""};

    }

    String Idposition="";
    private void clickid() {
        listitem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item item=dskhachhang.get(position);
                Idposition=String.valueOf(item.getID());
                return false;
            }
        });
    }

    public void addEventClickitem() {
        searchview1.setQueryHint("Nhập từ khóa .....");
        searchview1.onActionViewExpanded();
        searchview1.setIconified(false);
        searchview1.clearFocus();
        searchview1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equalsIgnoreCase("")){
                    ShowDatabase();
                }else{
                    searchdata(newText);
                }
                return false;
            }
        });
        searchviewcus.setQueryHint("Nhập từ khóa .....");
        searchviewcus.onActionViewExpanded();
        searchviewcus.setIconified(false);
        searchviewcus.clearFocus();
        searchviewcus.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equalsIgnoreCase("")){
                    showCus();
                }else{
                    searchdataOnCus(newText);
                }

                return false;
            }
        });



        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final item item=dskhachhang.get(position);

                database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);

                final Dialog dia=new Dialog(MainActivity.this);
                dia.setContentView(R.layout.menu_item);
                dia.show();
                Button btninf= (Button) dia.findViewById(R.id.btninf);
                Button btndel= (Button) dia.findViewById(R.id.btndel);
                Button btnbill= (Button) dia.findViewById(R.id.btnbill);

                btnbill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog diabill=new Dialog(MainActivity.this);
                        diabill.setContentView(R.layout.layout_bill);
                        diabill.show();

                        TextView txtbillnum= (TextView) diabill.findViewById(R.id.txtbillnumberbill);
                        TextView txtdateTime_bill= (TextView) diabill.findViewById(R.id.txtDate);
                        TextView txtorigin_bill= (TextView) diabill.findViewById(R.id.txtorigin);
                        TextView txtfinal_bill= (TextView) diabill.findViewById(R.id.txtfinalpoint);
                        TextView txttype_bill= (TextView) diabill.findViewById(R.id.txttype);
                        TextView txtnote_bill=(TextView) diabill.findViewById(R.id.txtnote);
                        TextView txtmoney_bill= (TextView) diabill.findViewById(R.id.txtmoney);
                        Button btnthoat= (Button) diabill.findViewById(R.id.btnthoat);

                        txtbillnum.setText(String.valueOf(item.getBillnum()));
                        txtdateTime_bill.setText("Hóa đơn ngày "+item.getDate()+" - "+item.getTime());
                        txtorigin_bill.setText(item.getOriginpoint());
                        txtfinal_bill.setText(item.getFinalpoint());
                        txttype_bill.setText(item.getType());
                        txtnote_bill.setText(item.getNote());
                        txtmoney_bill.setText(item.getMoney());
                        btnthoat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                diabill.dismiss();
                            }
                        });
                        dia.dismiss();
                    }
                });
                btninf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog diainf=new Dialog(MainActivity.this);
                        diainf.setContentView(R.layout.profile);
                        diainf.show();
                        LinearLayout linearLayoutpro= (LinearLayout) diainf.findViewById(R.id.linearprofile);
                        ImageView imageViewpr= (ImageView) diainf.findViewById(R.id.profile_imagedia);
                        TextView txtIDpro= (TextView) diainf.findViewById(R.id.txtIDpro);
                        TextView txtfirstnamepro= (TextView) diainf.findViewById(R.id.txtfirsnamepro);
                        TextView txtlastnamepro= (TextView) diainf.findViewById(R.id.txtlastnamepro);
                        TextView txtaddresspro= (TextView) diainf.findViewById(R.id.txtaddress);
                        TextView txtphonepro= (TextView) diainf.findViewById(R.id.txtphone);
                        Button btnthoatpro= (Button) diainf.findViewById(R.id.btnthoatpro);



                        try {
                            Bitmap bmp = BitmapFactory.decodeByteArray(item.avata, 0, item.avata.length);
                            imageViewpr.setImageBitmap(bmp);

                        }catch (Exception ex){

                        }

                        txtIDpro.setText("id: "+String.valueOf(item.getID()));
                        txtfirstnamepro.setText(item.getFirstname());
                        txtlastnamepro.setText(item.getLastname());
                        txtaddresspro.setText(item.getAddress());
                        txtphonepro.setText(item.getPhone());

                        btnthoatpro.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                diainf.dismiss();
                            }
                        });

                        dia.dismiss();
                    }
                });
                btndel.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dia.dismiss();
                        final String ID= String.valueOf(item.getID());
                        final String bill=String.valueOf(item.getBillnum());
                        final Dialog dianoice=new Dialog(MainActivity.this);
                        dianoice.setContentView(R.layout.thongbaoxoa);
                        dianoice.show();
                        TextView txt= (TextView) dianoice.findViewById(R.id.txtnotce);
                        Button btnok= (Button) dianoice.findViewById(R.id.btnnotiok);
                        Button btncancel= (Button) dianoice.findViewById(R.id.btnnoticancel);
                        txt.setText("bạn có muốn xóa hóa đơn này!");
                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.delete("Hoadon","ID=? and BILLNUMBER=?",new String[]{ID,bill});
                                adapteritem.clear();
                                ShowDatabase();
                                dianoice.dismiss();
                            }
                        });
                        btncancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dianoice.dismiss();
                            }
                        });
                    }
                });
            }
        });

        listcustome.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final item gid=dscustome.get(position);
                final String getLastname=gid.getLastname();
                final String getFirstname=gid.getFirstname();
                final String getphone=gid.getPhone();
                final String getAddress=gid.getAddress();
                final Dialog diaaddhd=new Dialog(MainActivity.this);
                diaaddhd.setContentView(R.layout.menuaddhoadon);
                diaaddhd.show();

                Button btnaddhd= (Button) diaaddhd.findViewById(R.id.btnaddhd);
                Button btneditkh= (Button) diaaddhd.findViewById(R.id.btnaddeditkh);
                btnaddhd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent Intentthemhd=new Intent(MainActivity.this,themhoadon.class);
                        Intentthemhd.putExtra("givephone",getphone);
                        startActivity(Intentthemhd);
                        diaaddhd.dismiss();
                    }
                });
                btneditkh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final item gid=dscustome.get(position);
                        final String getid= String.valueOf(gid.getID());
                        final String getLastname=gid.getLastname();
                        final String getFirstname=gid.getFirstname();
                        final String getphone=gid.getPhone();
                        final String getAddress=gid.getAddress();
                        Intent intentupdate=new Intent(MainActivity.this,Editkhachhang.class);
                        intentupdate.putExtra("updateID",getid);
                        intentupdate.putExtra("updatefirstname",getFirstname);
                        intentupdate.putExtra("updatelastname",getLastname);
                        intentupdate.putExtra("uodatephone",getphone);
                        intentupdate.putExtra("updateaddress",getAddress);
                        startActivity(intentupdate);
                        diaaddhd.dismiss();
                        finish();
                    }
                });

                return false;
            }
        });
    }
    private void showCus(){
        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT k.ID,k.IMAGE,k.FIRSTNAME,k.LASTNAME,k.ADDRESS,k.PHONE FROM Khachhang k",null);
        adapterCus.clear();
        while (cursor.moveToNext()){
            byte[] IMAGE=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            String lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            String Address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone=cursor.getString(cursor.getColumnIndex("PHONE"));

            item kh=new item();
            kh.setAvata(IMAGE);
            kh.setID(Integer.parseInt(ID));
            kh.setFirstname(firstname);
            kh.setLastname(lastname);
            kh.setAddress(Address);
            kh.setPhone("0"+String.valueOf(Long.parseLong(Phone)));

//sort
            dscustome.add(0,kh);

        }
        adapterCus.notifyDataSetChanged();
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
    private void ShowDatabase() {
        database=DatabaseAdapter.initDatabase(MainActivity.this,DATABASE_NAME);
        Cursor cursor=database.rawQuery("SELECT k.IMAGE,k.ID,k.FIRSTNAME,k.LASTNAME,k.ADDRESS,k.PHONE,h.BILLNUMBER,h.DATE,h.TIME,h.ORIGIN,h.FINAL,h.TYPE,h.NOTE,h.MONEY FROM Khachhang k ,Hoadon h WHERE k.ID=h.ID ",null);
        adapteritem.clear();
        while (cursor.moveToNext()){
            byte[] IMAGE=cursor.getBlob(cursor.getColumnIndex("IMAGE"));
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String firstname=cursor.getString(cursor.getColumnIndex("FIRSTNAME"));
            String lastname=cursor.getString(cursor.getColumnIndex("LASTNAME"));
            String Address=cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone=cursor.getString(cursor.getColumnIndex("PHONE"));
            String date=cursor.getString(cursor.getColumnIndex("DATE"));
            String time=cursor.getString(cursor.getColumnIndex("TIME"));
            String origin=cursor.getString(cursor.getColumnIndex("ORIGIN"));
            String Final=cursor.getString(cursor.getColumnIndex("FINAL"));
            String Type=cursor.getString(cursor.getColumnIndex("TYPE"));
            String note=cursor.getString(cursor.getColumnIndex("NOTE"));


            String rs = formatNumber(cursor.getInt(cursor.getColumnIndex("MONEY")));
            String money=rs;
            String bill1=cursor.getString(cursor.getColumnIndex("BILLNUMBER"));


            item kh=new item();
            kh.setAvata(IMAGE);
            kh.setID(Integer.parseInt(ID));
            kh.setFirstname(firstname);
            kh.setLastname(lastname);
            kh.setAddress(Address);
            kh.setPhone(String.valueOf(Long.parseLong(Phone)));
            kh.setBillnum(Long.parseLong(bill1));
            kh.setDate(date);
            kh.setTime(time);
            kh.setOriginpoint("Điểm đi: "+origin);
            kh.setFinalpoint("Điểm đến: "+Final);
            kh.setType(Type);
            kh.setNote(note);
            kh.setMoney(money+"đ");
//sort
            dskhachhang.add(0,kh);
            dscustome.add(0,kh);

        }
        adapterCus.notifyDataSetChanged();
        adapteritem.notifyDataSetChanged();


    }

    public void addcontrol() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab = tabHost.newTabSpec("t1");
        tab.setContent(R.id.tab1);
        tab.setIndicator("Hóa đơn");
        tabHost.addTab(tab);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Khách hàng");
        tabHost.addTab(tab2);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabColor(tabHost);
            }
        });

        listcustome = (ListView) findViewById(R.id.listviewkh);
        dscustome = new ArrayList<>();
        adapterCus = new AdapterCus(MainActivity.this, R.layout.item_kh, dscustome);
        listcustome.setAdapter(adapterCus);

        searchview1 = (SearchView) findViewById(R.id.search_viewall);
        searchviewcus = (SearchView) findViewById(R.id.search_viewcus);

        listitem = (ListView) findViewById(R.id.listKH);
        dskhachhang = new ArrayList<>();
        adapteritem = new Adapteritem(MainActivity.this, R.layout.item, dskhachhang);
        listitem.setAdapter(adapteritem);



    }
    public void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++) {
            tabhost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.color.white); // unselected
        }
        tabhost.getTabWidget().setCurrentTab(0);
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab())
                .setBackgroundResource(R.color.greenseleted); // selected
        // //have
        // to
        // change
    }


}
