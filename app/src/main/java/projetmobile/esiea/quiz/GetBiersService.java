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
public class GetBiersService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ALL_BIERS = "projetmobile.esiea.quiz.action.BIERS";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "projetmobile.esiea.quiz.extra.PARAM1";

    public GetBiersService() {
        super("GetBiersService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetAllBiers(Context context, String param1) {
        Intent intent = new Intent(context, GetBiersService.class);
        intent.setAction(ACTION_GET_ALL_BIERS);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_BIERS.equals(action)) {
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
            url = new URL("http://binouze.fabrigli.fr/bieres.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (HttpURLConnection.HTTP_OK == con.getResponseCode()){
                copyInputStreamToFile(con.getInputStream(), new File(getCacheDir(), "bieres.json"));
                Log.d("Biers", "Downloaded");
                downloaded = true;
            }
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
        if (downloaded == true) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BiersList.BIERS_UPDATE));
        }
        else{

        }
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
