package projetmobile.esiea.quiz;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetPokeService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ALL_POKE = "projetmobile.esiea.quiz.action.FOO";


    public GetPokeService() {
        super("GetPokeService");
    }


    public static void startActionGetAllPok(Context context) {
        Intent intent = new Intent(context, GetPokeService.class);
        intent.setAction(ACTION_GET_ALL_POKE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_POKE.equals(action)) {
                handleActionFoo(null);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1) {
        // TODO: Handle action Foo
        Log.d("MyService", "Thread service name : "+ Thread.currentThread().getName());
        URL url = null;
        boolean downloaded = false;
        try{
            url = new URL("https://pokeapi.co/api/v2/pokemon.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            Log.d("downloading","poke");
            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "poke.json"));
                Log.d("Poke", "Downloaded");
                downloaded = true;
            }
            else{
                downloaded = false;
                Log.d("downloading","failed");

            }

        }
        catch(MalformedURLException e){

            e.printStackTrace();

        }
        catch (IOException e){

            e.printStackTrace();
        }
        Intent broadcastedIntent=new Intent(PokeList.POKE_UPDATE);
        broadcastedIntent.putExtra("VALUE", downloaded);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastedIntent);
    }

    private void copyInputStreamToFile(InputStream is, File file){
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] bit = new byte [1024];
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
