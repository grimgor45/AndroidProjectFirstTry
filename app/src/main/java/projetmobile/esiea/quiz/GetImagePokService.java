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


public class GetImagePokService extends IntentService {

    private static final String ACTION_GET_IMAGE_POKE = "projetmobile.esiea.quiz.action.FOO";
    private static final String paramName = "pokeName";

    public GetImagePokService() {
        super("GetImagePokeService");
    }


    public static void startActionGetImagePoke(Context context, String pokeName) {
        Log.d("estcequecamarche", "123300");
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


    private void handleActionFoo(String paramPoke) {
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
                copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "pokeImage.json"));
                JSONObject jo = Toolbox.getJSONObjectFromFile(this, "pokeImage.json");

                JSONObject spritesurl = jo.getJSONObject("sprites");
                Log.d("yahou1", String.valueOf(spritesurl.length()));
                Iterator<?> keys = spritesurl.keys();
                int a=0;
                while (keys.hasNext() && a==0){
                    String key = (String) keys.next();
                    if (spritesurl.getString(key) != "null")
                    {
                        imageSafe = new URL(spritesurl.getString(key));
                        if(rand.nextBoolean()){
                            Log.d("yahou1.5", key);

                            //Log.d("yahou2", spritesurl.getString(key));
                            a=1;
                            Log.d("yahou3", "skdf");

                            image = new URL(spritesurl.getString(key));

                        }
                    }
                    if (!keys.hasNext() && image == null){
                        image = imageSafe;
                    }
                }
                Log.d("while qui merde","failed");

                if(image==null)
                {
                    downloaded = false;
                    Log.d("downloading12","failed");
                }
                else{
                    Log.d("poiuyt","cxopy");

                    HttpURLConnection conn = (HttpURLConnection) image.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                        //copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "pokeImage.png"));
                        Log.d("poiuyt","cxopy");

                        File file = new File(getCacheDir(),  paramPoke+".png");
                        Log.d("poiuyt1","cxopy");
                        copyInputStreamToFile(conn.getInputStream(), file);

                        Log.d("sqdqsdsd", "copyseems tospikhjfik");
                        downloaded = true;


                    }

                }}
            else{
                downloaded = false;
                Log.d("downloading","failed");

            }
            //Intent broadcastedIntent=new Intent(Questions.POKE_IMAGE_UPADTE);
            //broadcastedIntent.putExtra("VALUE", downloaded);
            //LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastedIntent);

        }
        catch(MalformedURLException e){

            e.printStackTrace();

        }
        catch (IOException e){
            Log.d("laoucamarchepas", "ajh");
            e.printStackTrace();
        }
        catch (JSONException e) {
            Log.d("laoucamarchepas", "ajh");

            e.printStackTrace();
        }

    }

    private void copyInputStreamToFile(InputStream is, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] bit = new byte [1024*2];
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