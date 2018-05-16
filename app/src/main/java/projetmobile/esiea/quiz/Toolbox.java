package projetmobile.esiea.quiz;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Toolbox {
    public static double ScHgt(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = context.getResources().getDisplayMetrics();

        int height = display.heightPixels;
        return height;
    }

    public static JSONArray getJSONArrayFromFile(Context context, String string){
        try{
            InputStream is = new FileInputStream(context.getCacheDir()+"/"+string);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String (buffer, "UTF-8"));
        }catch (IOException e){
            return new JSONArray();
        }catch (JSONException e){
            return new JSONArray();
        }
    }

    //Code taken from https://stackoverflow.com/questions/8326852/how-to-delete-cache-folder-of-app
    protected void destroyCache(Context context) {

        try {
            trimCache(context);
            // Toast.makeText(this,"onDestroy " ,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
