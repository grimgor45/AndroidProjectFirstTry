package projetmobile.esiea.quiz;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

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

    private static final String ACTION_GET_IMAGE_POKE_1 = "projetmobile.esiea.quiz.action.FOO1";
    private static final String paramName = "pokeName";

    public GetImagePokService() {
        super("GetImagePokService");
    }


    public static void startActionGetImagePok(Context context, String pokeName) {
        Intent intent = new Intent(context, GetImagePokService.class);
        intent.setAction(ACTION_GET_IMAGE_POKE_1);
        intent.putExtra(paramName, pokeName);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_IMAGE_POKE_1.equals(action)) {
                final String param1 = intent.getStringExtra(paramName);

                handleActionFoo(param1);
            }
        }
    }


    private void handleActionFoo(String paramPoke) {
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
                int a=0;
                while (keys.hasNext() && a==0){
                    String key = (String) keys.next();
                    if (spritesurl.getString(key) != "null")
                    {
                        imageSafe = new URL(spritesurl.getString(key));
                        if(rand.nextBoolean()){

                            a=1;

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

                        File file = new File(getCacheDir(),  paramPoke+".png");

                        copyInputStreamToFile(conn.getInputStream(), file);

                        downloaded = true;


                    }

                }}
            else{
                downloaded = false;

            }
            //Intent broadcastedIntent=new Intent(Questions.POKE_IMAGE_UPADTE);
            //broadcastedIntent.putExtra("VALUE", downloaded);
            //LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastedIntent);

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