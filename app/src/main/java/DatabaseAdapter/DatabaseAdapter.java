package DatabaseAdapter;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kyosh on 4/13/2017.
 */

public class DatabaseAdapter {
    public static SQLiteDatabase initDatabase(Activity activity, String databasename)
    {
        try{
            String OutFileName=activity.getApplicationInfo().dataDir+"/databases/"+databasename;
            File f=new File(OutFileName);
            if(!f.exists()){
                InputStream e=activity.getAssets().open(databasename);
                File folder=new File(activity.getApplicationInfo().dataDir+"/databases/");
                if(!folder.exists()){
                    folder.mkdir();
                }
                FileOutputStream myoutput=new FileOutputStream(OutFileName);
                byte[] buffer=new byte[1024];
                int length;
                while((length=e.read(buffer))>0){
                    myoutput.write(buffer,0,length);
                }
                myoutput.flush();
                myoutput.close();
                e.close();
            }
        }catch(IOException e){
            Log.e("error",e.toString());
        }
        return activity.openOrCreateDatabase(databasename, Context.MODE_PRIVATE,null);
    }

}
