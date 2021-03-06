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


public class GetImagePokeService extends IntentService {

    private static final String ACTION_GET_IMAGE_POKE = "projetmobile.esiea.quiz.action.FOO";
    private static final String paramName = "pokeName";

    public GetImagePokeService() {
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

        if (intent != null) {
            boolean downloaded = false;
            final String action = intent.getAction();
            if (ACTION_GET_IMAGE_POKE.equals(action)) {
                final String param1 = intent.getStringExtra(paramName);
                 handleActionFoo(param1);
            }
        }
    }

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

                copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "pokeImage.json"));
                JSONObject jo = Toolbox.getJSONObjectFromFile(this, "pokeImage.json");

                JSONObject spritesurl = jo.getJSONObject("sprites");
                Iterator<?> keys = spritesurl.keys();
                while (keys.hasNext()){
                    String key = (String) keys.next();
                    if (spritesurl.getString(key) != "null")
                    {
                        imageSafe = new URL(spritesurl.getString(key));
                        if(rand.nextBoolean()){


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
                }
                else{
                HttpURLConnection conn = (HttpURLConnection) image.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    //copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "pokeImage.png"));
                    File file = new File(getCacheDir(), "pokeImage.png");

                    copyInputStreamToFile(conn.getInputStream(), file);

                    downloaded = true;


                }

            }}
            else{
                downloaded = false;

            }


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
        Intent broadcastedIntent = new Intent(Questions.POKE_IMAGE_UPADTE);
        broadcastedIntent.putExtra("VALUE", downloaded);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastedIntent);

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