package adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kyosh.dacs.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

import model.item;

/**
 * Created by kyosh on 4/19/2017.
 */

public class Adapteritem extends ArrayAdapter<item> {

    @NonNull Activity context;
    @LayoutRes int resource;
    @NonNull List<item> objects;
    public Adapteritem(@NonNull Activity context, @LayoutRes int resource, @NonNull List<item> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public item getItem(int position) {
        return super.getItem(getCount()-position-1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);


        LinearLayout frameLayout= (LinearLayout) row.findViewById(R.id.framcolor);
        ImageView imageprofile= (ImageView) row.findViewById(R.id.profile_image);
        TextView txtten= (TextView) row.findViewById(R.id.txttenkh);
        TextView txtdiemden= (TextView) row.findViewById(R.id.txtdiemden);
        TextView txtdiemdi= (TextView) row.findViewById(R.id.txtdiemdi);
        TextView txtdate= (TextView) row.findViewById(R.id.txtngay);
        TextView txttime= (TextView) row.findViewById(R.id.txttime);
        TextView txtmoney= (TextView) row.findViewById(R.id.txtmoney);


        item item=this.objects.get(position);
        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(item.avata, 0, item.avata.length);
            imageprofile.setImageBitmap(bmp);
        }catch (Exception ex){

        }


        txtten.setText(item.getFirstname()+" "+item.getLastname());
        txtdiemden.setText(item.getFinalpoint());
        txtdiemdi.setText(item.getOriginpoint());
        txtdate.setText(item.getDate());
        txttime.setText(item.getTime());
        txtmoney.setText(item.getMoney());
        return row;
    }
}
