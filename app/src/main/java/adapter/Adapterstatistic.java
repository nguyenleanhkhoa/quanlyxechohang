package adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kyosh.dacs.R;

import java.util.ArrayList;
import java.util.List;

import model.item;
import model.thongke;

/**
 * Created by kyosh on 5/2/2017.
 */

public class Adapterstatistic extends ArrayAdapter<thongke> {

    @NonNull Activity context;
    @LayoutRes int resource;
    @NonNull ArrayList<thongke> objects;
    public Adapterstatistic(@NonNull Activity context, @LayoutRes int resource, @NonNull ArrayList<thongke> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView txtdate= (TextView) row.findViewById(R.id.txtdatetk);
        TextView txtsokhach= (TextView) row.findViewById(R.id.txtsokhachtk);

        thongke thongke=this.objects.get(position);
        txtdate.setText(thongke.getDate());
        txtsokhach.setText(thongke.getCustome_count());
        return row;
    }
}
