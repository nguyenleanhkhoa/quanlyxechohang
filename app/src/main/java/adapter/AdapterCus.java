package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyosh.dacs.R;

import java.util.List;

import model.item;

/**
 * Created by kyosh on 4/27/2017.
 */

public class AdapterCus extends ArrayAdapter<item> {
    @NonNull Activity context;
    @LayoutRes int resource;
    @NonNull List<item> objects;
    public AdapterCus(@NonNull Activity context, @LayoutRes int resource, @NonNull List<item> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.resource,null);
        ImageView imagepro= (ImageView) row.findViewById(R.id.profile_customer);
        TextView txtname= (TextView) row.findViewById(R.id.txtcusname);
        TextView txtphone= (TextView) row.findViewById(R.id.txtcusphone);
        TextView txtid= (TextView) row.findViewById(R.id.txtcusid);

        item item=this.objects.get(position);
        try {
            Bitmap bmp = BitmapFactory.decodeByteArray(item.avata, 0, item.avata.length);
            imagepro.setImageBitmap(bmp);
        }catch (Exception ex){

        }
        txtname.setText("Tên:"+item.getFirstname()+" "+item.getLastname());
        txtphone.setText("Phone:"+item.getPhone());
        txtid.setText("ID:"+String.valueOf(item.getID()));
        return row;
    }
}
