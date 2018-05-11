package projetmobile.esiea.quiz;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BiersList extends AppCompatActivity {

    RecyclerView rv;;


    public static final String BIERS_UPDATE = "projetmobile.esiea.quiz.BIERS_UPDATE";
    public class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            rv = findViewById(R.id.rv_biers);
            ((BiersAdapter)rv.getAdapter()).setNewBiers(getBiersFromFile());
            Log.d("Download","finished"); }}



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biers_list);




        GetBiersService.startActionGetAllBiers(BiersList.this, null);
            rv = findViewById(R.id.rv_biers);

        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new BiersAdapter(getBiersFromFile()));

            IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
            LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
    }

    public JSONArray getBiersFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
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
}
