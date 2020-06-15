package com.example.digime.http;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.RelativeLayout;

import java.io.*;


public class RetriveandSaveJSONdatafromfile {

    static String TAG = RetriveandSaveJSONdatafromfile.class.getSimpleName();

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }


    public static String objectToFile(Object object, Context context) throws IOException {
        String path = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator;

        Log.d(TAG, path);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        path += "ResponseJson.text";//_날짜
        File data = new File(path);
        if (!data.createNewFile()) {
            data.delete();
            data.createNewFile();
        }
        OutputStreamWriter objectOutputStream = new OutputStreamWriter(new FileOutputStream(data),"UTF-8");
        objectOutputStream.write(object.toString());
        objectOutputStream.close();
        return path;
    }

    public static Object objectFromFile(String path) throws IOException, ClassNotFoundException {
        Object object = null;
        File data = new File(path);
        if(data.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(data));
            object = objectInputStream.readObject();
            objectInputStream.close();
        }
        return object;
    }
}