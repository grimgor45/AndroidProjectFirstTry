package projetmobile.esiea.quiz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int a = 121;
    String b = ((Integer)a).toString();
    public static String CHANNEL_ID = "projetmobile.esiea.quiz.NOTIFICATION";
    public static int notificationId = 42;
    static public boolean freng = true;


    //Channel
    //code taken from https://developer.android.com/training/notify-user/build-notification
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "dlchannel";
            String description = "Channel for download";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ScrollView myScrollView = (ScrollView) findViewById(R.id.mainMenuScrollView);
        myScrollView.getLayoutParams().height = ((int) Toolbox.ScHgt(this)-250);

        GetBiersService.startActionGetAllBiers(MainActivity.this);

        final Intent secAct = new Intent(this, SecondActivity.class);

        Button btnSecAct = (Button)findViewById(R.id.ButtonSecondActivity);
        btnSecAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(secAct);
            }
        });

        final Intent aQuizz = new Intent(this, Questions.class);


        final Intent BiersList = new Intent(this, BiersList.class);

        Button BiersListButton = (Button)findViewById(R.id.BiersList);
        BiersListButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(BiersList);
            }
        });

        final Intent PokeList = new Intent(this, PokeList.class);

        Button PokeListButton = (Button)findViewById(R.id.PokeList);
        PokeListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(PokeList);
            }
        });
    }
}
