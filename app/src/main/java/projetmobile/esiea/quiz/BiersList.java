package projetmobile.esiea.quiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class BiersList extends AppCompatActivity {

    RecyclerView rv;;

    private final String JSONARRAY_NAME =  "bieres.json";

    public static final String BIERS_UPDATE = "projetmobile.esiea.quiz.BIERS_UPDATE";

    private String correctDownloadToast;
    private String incorrectDownloadToast ;
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;
    Intent mainMenu = null;
    Toast toast;
    boolean show = true;
    boolean down = true;

    public class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            rv = findViewById(R.id.rv_biers);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                ((BiersAdapter) rv.getAdapter()).setNewBiers(Toolbox.getJSONArrayFromFileBeer(context, JSONARRAY_NAME));

                Log.d("Download", "finished");

                if (down) {
                    down = false;
                    Toolbox.createShowNotificationDownload(getApplicationContext());
                }}
            else{

                if(show) {
                    show =false;
                    toast.show();
                }
                Log.d("Download", "failed");

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumainact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_return:
                startActivity(mainMenu);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biers_list);
        mainMenu = new Intent(this, MainActivity.class);
        correctDownloadToast = getString(R.string.Downloadsuccess);
        incorrectDownloadToast = getString(R.string.DownloadFailed);
        toast = Toast.makeText(this, incorrectDownloadToast, toastDuration);

        GetBiersService.startActionGetAllBiers(BiersList.this);

        rv = findViewById(R.id.rv_biers);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new BiersAdapter(Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME)));

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);


    }



}
