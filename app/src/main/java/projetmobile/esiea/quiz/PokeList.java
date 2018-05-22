package projetmobile.esiea.quiz;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PokeList extends AppCompatActivity {

    RecyclerView rv;;

    private final String JSONARRAY_NAME =  "poke.json";

    public static final String POKE_UPDATE= "projetmobile.esiea.quiz.POKE_UPDATE";

    private String correctDownloadToast = "Correctly downloaded";
    private String incorrectDownloadToast = "Download Failed please enable wifi";
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;

    public class PokeUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            rv = findViewById(R.id.rv_poke);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                ((PokeAdapter) rv.getAdapter()).setNewPoke(Toolbox.getJSONArrayFromFile(context, JSONARRAY_NAME));
                Toast toast = Toast.makeText(context, correctDownloadToast, toastDuration);
                toast.show();
                Log.d("Download", "finished");

                Toolbox.createShowNotificationDownload(getApplicationContext());
            }
            else{
                Toast toast = Toast.makeText(context, incorrectDownloadToast, toastDuration);
                toast.show();
                Log.d("Download", "failed");

            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_list);

        GetPokeService.startActionGetAllPok(PokeList.this);

        rv = findViewById(R.id.rv_poke);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new PokeAdapter(Toolbox.getJSONArrayFromFile(this, JSONARRAY_NAME)));

        IntentFilter intentFilter = new IntentFilter(POKE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new PokeUpdate(),intentFilter);
    }



}
