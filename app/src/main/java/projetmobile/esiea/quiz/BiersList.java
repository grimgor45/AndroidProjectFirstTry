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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BiersList extends AppCompatActivity {

    RecyclerView rv;;

    private final String JSONARRAY_NAME =  "bieres.json";

    public static final String BIERS_UPDATE = "projetmobile.esiea.quiz.BIERS_UPDATE";

    private String correctDownloadToast = "Correctly downloaded";
    private String incorrectDownloadToast = "Download Failed please enable wifi";
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;
    Intent mainMenu = null;

    public class BierUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            rv = findViewById(R.id.rv_biers);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                ((BiersAdapter) rv.getAdapter()).setNewBiers(Toolbox.getJSONArrayFromFileBeer(context, JSONARRAY_NAME));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumain, menu);
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


        GetBiersService.startActionGetAllBiers(BiersList.this);

        rv = findViewById(R.id.rv_biers);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new BiersAdapter(Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME)));

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);


    }



}
