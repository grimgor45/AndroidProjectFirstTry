package projetmobile.esiea.quiz;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class Toolbox {
    public static double ScHgt(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = context.getResources().getDisplayMetrics();

        int height = display.heightPixels;
        return height;
    }

    public static String getRandomElementName(JSONArray ja)
    {
        Random rand = new Random();
        String name = null;
        try {
            name = ja.getJSONObject(rand.nextInt()%ja.length()).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static JSONArray getJSONArrayFromFilePoke(Context context, String string) {
        try {
            InputStream is = new FileInputStream(context.getCacheDir() + "/" + string);
            byte[] buffer = new byte[is.available()];


            is.read(buffer);

            is.close();

            String lol = new String(buffer, "UTF-8");

            JSONObject jo = new JSONObject(lol);

            JSONArray ja = jo.getJSONArray("results");


            return ja;
        } catch (IOException e) {
            return new JSONArray();
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static JSONObject getJSONObjectFromFile(Context context, String string) {
        try {
            InputStream is = new FileInputStream(context.getCacheDir() + "/" + string);
            byte[] buffer = new byte[is.available()];
            Log.d("hey", String.valueOf(is.available()));
            is.read(buffer);
            is.close();
            JSONObject ja = new JSONObject(new String(buffer, "UTF-8"));

            return ja;
        } catch (IOException e) {
            return new JSONObject();
        } catch (JSONException e) {
            return new JSONObject();
        }
    }
    public static JSONArray getJSONArrayFromFileBeer(Context context, String string) {
        try {
            InputStream is = new FileInputStream(context.getCacheDir() + "/" + string);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            JSONArray ja = new JSONArray(new String(buffer, "UTF-8"));

            return ja;
        } catch (IOException e) {
            return new JSONArray();
        } catch (JSONException e) {
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

    //Notification
    public static void createShowNotificationDownload(Context context) {

        Intent intent = new Intent(context, BiersList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_arrow_down)
                .setContentTitle("le DL est finit")
                .setContentText("show downloaded list")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(MainActivity.notificationId, mBuilder.build());
    }


}
