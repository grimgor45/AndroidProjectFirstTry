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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PokeList extends AppCompatActivity {

    RecyclerView rv;;

    private final String JSONARRAY_NAME =  "poke.json";

    public static final String POKE_UPDATE= "projetmobile.esiea.quiz.POKE_UPDATE";

    private String correctDownloadToast;
    private String incorrectDownloadToast;
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;
    Intent mainMenu = null;
    Toast toast ;
    boolean show;


    public class PokeUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            rv = findViewById(R.id.rv_poke);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                ((PokeAdapter) rv.getAdapter()).setNewPoke(Toolbox.getJSONArrayFromFilePoke(context, JSONARRAY_NAME));

                Log.d("Download", "finished");
                }
            else{if (show){
                toast.show();
                show = false;
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
        Log.d("estcequecamarcheqsdqsd1", "surementckhqgsdkujhiqsdjhk;gv");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_list);



        correctDownloadToast = getString(R.string.Downloadsuccess);
        incorrectDownloadToast = getString(R.string.DownloadFailed);
        toast = Toast.makeText(this, incorrectDownloadToast, toastDuration);

        mainMenu = new Intent(this, MainActivity.class);
        Log.d("estcequecamarcheqsdqsd1", "surementckhqgsdkujhiqsdjhk;gv");

        GetPokeService.startActionGetAllPok(PokeList.this);

        rv = findViewById(R.id.rv_poke);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(new PokeAdapter(Toolbox.getJSONArrayFromFilePoke(this, JSONARRAY_NAME), getBaseContext()));

        IntentFilter intentFilter = new IntentFilter(POKE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new PokeUpdate(),intentFilter);


    }



}
