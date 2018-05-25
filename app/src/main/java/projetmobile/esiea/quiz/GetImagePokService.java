package projetmobile.esiea.quiz;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetImagePokService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_IMAGE_POKE = "projetmobile.esiea.quiz.action.FOO";
    private static final String paramName = "pokeName";

    public GetImagePokService() {
        super("GetImagePokeService");
    }


    public static void startActionGetImagePoke(Context context, String pokeName) {
        Intent intent = new Intent(context, GetImagePokeService.class);
        intent.setAction(ACTION_GET_IMAGE_POKE);
        intent.putExtra(paramName, pokeName);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("downloading1","poke");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_IMAGE_POKE.equals(action)) {
                final String param1 = intent.getStringExtra(paramName);

                handleActionFoo(param1);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String paramPoke) {
        // TODO: Handle action Foo
        Log.d("MyService", "Thread service name : "+ Thread.currentThread().getName());
        URL urlAPI = null;
        URL image = null;
        URL imageSafe = null;
        boolean downloaded = false;
        try{
            Random rand = new Random();
            urlAPI = new URL("https://pokeapi.co/api/v2/pokemon/"+paramPoke+"/");
            HttpURLConnection con = (HttpURLConnection) urlAPI.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), paramPoke+"json"));
                JSONObject jo = Toolbox.getJSONObjectFromFile(this, paramPoke+"json");

                JSONObject spritesurl = jo.getJSONObject("sprites");
                Log.d("yahou1", String.valueOf(spritesurl.length()));
                Iterator<?> keys = spritesurl.keys();
                while (keys.hasNext()){
                    String key = (String) keys.next();
                    if (spritesurl.getString(key) != "null")
                    {
                        imageSafe = new URL(spritesurl.getString(key));
                        if(rand.nextBoolean()){
                            Log.d("yahou1.5", key);

                            Log.d("yahou2", spritesurl.getString(key));

                            image = new URL(spritesurl.getString(key));

                        }
                    }
                    if (!keys.hasNext() && image == null){
                        image = imageSafe;
                    }
                }
                if(image==null)
                {
                    downloaded = false;
                    Log.d("downloading12","failed");
                }
                else{
                    Log.d("yipi", image.toString());
                    HttpURLConnection conn = (HttpURLConnection) image.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                        //copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "pokeImage.png"));
                        Log.d("yahalo", getCacheDir().toString());
                        File file = new File(getCacheDir(), "pokeImage.png");

                        copyInputStreamToFile(conn.getInputStream(), file);

                        Log.d("yipi1", "copyseemstowork");
                        downloaded = true;


                    }

                }}
            else{
                downloaded = false;
                Log.d("downloading","failed");

            }
            Intent broadcastedIntent=new Intent(Questions.POKE_IMAGE_UPADTE);
            broadcastedIntent.putExtra("VALUE", downloaded);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastedIntent);

        }
        catch(MalformedURLException e){

            e.printStackTrace();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void copyInputStreamToFile(InputStream is, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] bit = new byte [1024*4];
            int len;
            while ((len=is.read(bit))>0){
                out.write(bit,0,len);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}