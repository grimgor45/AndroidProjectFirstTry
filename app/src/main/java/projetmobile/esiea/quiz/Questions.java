package projetmobile.esiea.quiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;
import java.util.Random;

public class Questions extends AppCompatActivity {

    public AnswerList aL;
    public int score;
    public int actualScore;
    JSONArray listBeer;
    JSONArray listPoke;
    private final String JSONARRAY_NAME_BEER =  "bieres.json";
    private final String JSONARRAY_NAME_POKE =  "poke.json";
    public int typequizz;
    public String valueQuestion = "name";
    private String correctDownloadToast = "Correctly downloaded";
    private String incorrectDownloadToast = "Download Failed please enable wifi";
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;
    Button A1;
    Button A2;
    Button A3;
    Button A4;
    Intent mainMenu = null;
    Random rand;
    Boolean beerPoke = true;
    public String hey;


    public static final String POKE_IMAGE_UPADTE= "projetmobile.esiea.quiz.POKE_UPDATE";

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
            case R.id.action_language:
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                Locale locale = new Locale(pref.getString("lang_code","fr"));
                Locale.setDefault(locale);
                Configuration conf = getBaseContext().getResources().getConfiguration();
                conf.locale= locale;
                getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public class ImageUpadte extends BroadcastReceiver {



        @Override
        public void onReceive(Context context, Intent intent) {
            ImageView iv = findViewById(R.id.image_view_question);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                Bitmap bm = BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png");
                iv.setImageBitmap(Bitmap.createScaledBitmap(bm,500,500,false));
                Toast toast = Toast.makeText(context, correctDownloadToast, toastDuration);
                toast.show();
                Log.d("lelol", "finished");

                Toolbox.createShowNotificationDownload(getApplicationContext());
                A1.setEnabled(true);
                A2.setEnabled(true);
                A3.setEnabled(true);
                A4.setEnabled(true);
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

        mainMenu = new Intent(this, MainActivity.class);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        rand = new Random();

        int scrollViewHeight = (int)Toolbox.ScHgt(this)/4;
        ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollViewDescriptionBeer);
        scrollView.getLayoutParams().height = scrollViewHeight;


        IntentFilter intentFilter = new IntentFilter(POKE_IMAGE_UPADTE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new Questions.ImageUpadte(),intentFilter);

        A1 = (Button)findViewById(R.id.Answer1);
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(1);
            }
        });
        A2 = (Button)findViewById(R.id.Answer2);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(2);
            }
        });
        typequizz = getIntent().getIntExtra("TYPEQUIZZ",QuestionsType.beerDescription.ordinal());



        A3 = (Button)findViewById(R.id.Answer3);
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(3);
            }
        });
        A4 = (Button)findViewById(R.id.Answer4);
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(4);
            }
        });
        if(typequizz== QuestionsType.pokemonorbeer.ordinal()){
            A1.setText("beer");
            A2.setText("poke");
            ImageView iv = (ImageView) findViewById(R.id.image_view_question);
            iv.setVisibility(ImageView.INVISIBLE);
        }

        if(typequizz == QuestionsType.beerDescription.ordinal()) {
            GetBiersService.startActionGetAllBiers(Questions.this);


            listBeer = Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME_BEER);
            if (listBeer.length()>=4) {
                changeAnswerList(listBeer, "description");
            }
        }
        if(typequizz == QuestionsType.PokemonSprits.ordinal()) {
            Log.d("hey", "alizjdoiqshdjkskjjs");
            GetPokeService.startActionGetAllPok(Questions.this);

            Log.d("IT", "works");
            listPoke = Toolbox.getJSONArrayFromFilePoke(this, JSONARRAY_NAME_POKE);
            if (listPoke.length()>=4) {
                changeAnswerList(listPoke, "id");
            }
        }
        if(typequizz == QuestionsType.pokemonorbeer.ordinal())
        {
            GetBiersService.startActionGetAllBiers(Questions.this);
            GetPokeService.startActionGetAllPok(Questions.this);
            listBeer = Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME_BEER);
            listPoke = Toolbox.getJSONArrayFromFilePoke(this, JSONARRAY_NAME_POKE);
            beerPoke = rand.nextBoolean();
            TextView tv = (TextView) findViewById(R.id.QuizzQuestionText);
            if (beerPoke)
            {


                tv.setText(Toolbox.getRandomElementName(listBeer));
            }
            else{
                tv.setText(Toolbox.getRandomElementName(listPoke));
            }


        }




        Button loadButton = (Button) findViewById(R.id.ButtonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typequizz == QuestionsType.beerDescription.ordinal()) {
                    if (listBeer.length() >= 4) {
                        changeAnswerList(listBeer, "description");
                    }
                }
                if (typequizz == QuestionsType.PokemonSprits.ordinal()){
                    if(listPoke.length()>=4){
                        try {
                            loadNextImage(aL.objList[aL.correct-1].getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(typequizz == QuestionsType.pokemonorbeer.ordinal()){
                    GetBiersService.startActionGetAllBiers(Questions.this);
                    GetPokeService.startActionGetAllPok(Questions.this);
                }
            }
        });




        Button quit = (Button)findViewById(R.id.Buttonquit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = TrulyExit();
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }

    private AlertDialog.Builder TrulyExit(){
        final Intent quitToMainAct = new Intent(this, MainActivity.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setMessage("This will end the activity");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(quitToMainAct);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder;

    }


    private void manageAnswerButton(int id){
        if(typequizz == QuestionsType.pokemonorbeer.ordinal())
        {
            if (id == 1 &&  beerPoke){
                score++;
            }
            if (id == 1 && !beerPoke){

            }
            if(id == 2 && beerPoke){

            }
            if(id == 2 && !beerPoke){
                score++;
            }
            actualScore++;
            ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
            ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
            changeAnswerList(null, null);
        }
        else{
            if (aL.correct ==id)
        {
            score++;
            Context context = getApplicationContext();
            CharSequence text = "Correct Answer";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            Context context = getApplicationContext();
            String goodAnswer = "";
            try {
                goodAnswer = aL.objList[aL.correct-1].getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String text = "Wrong answer "+",the right one was "+goodAnswer;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        actualScore++;
        ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
        ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
        if (typequizz == QuestionsType.beerDescription.ordinal()) {
            changeAnswerList(listBeer, "description");
        }
        if(typequizz == QuestionsType.PokemonSprits.ordinal()){
            changeAnswerList(listPoke, valueQuestion);
        }}
    }

    private void changeAnswerList(JSONArray list, String JSONValueQestion){
        if(typequizz == QuestionsType.pokemonorbeer.ordinal())
        {

            listBeer = Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME_BEER);
            listPoke = Toolbox.getJSONArrayFromFilePoke(this, JSONARRAY_NAME_POKE);
            beerPoke = rand.nextBoolean();
            TextView tv = (TextView) findViewById(R.id.QuizzQuestionText);
            if (beerPoke)
            {
                tv.setText(Toolbox.getRandomElementName(listBeer));
            }
            else{
                tv.setText(Toolbox.getRandomElementName(listPoke));
            }
        }
        else{
            aL = AnswerList.getInstance(list);

        }

        if (typequizz == QuestionsType.PokemonSprits.ordinal()) {
            try {
                A1.setEnabled(false);
                A2.setEnabled(false);
                A3.setEnabled(false);
                A4.setEnabled(false);
                TextView tv = (TextView) findViewById(R.id.QuizzQuestionText);
                tv.setVisibility(TextView.INVISIBLE);
                loadNextImage(aL.objList[aL.correct-1].getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(typequizz == QuestionsType.beerDescription.ordinal())
        {
            try {
                ImageView iv = (ImageView) findViewById(R.id.image_view_question);
                iv.setVisibility(ImageView.INVISIBLE);
                ((TextView) findViewById(R.id.QuizzQuestionText)).setText("Description de la bière " + aL.objList[aL.correct - 1].getString(JSONValueQestion));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(typequizz != QuestionsType.pokemonorbeer.ordinal()){
        try {
            ((Button)findViewById(R.id.Answer1)).setText(aL.objList[0].getString("name"));
            ((Button)findViewById(R.id.Answer2)).setText(aL.objList[1].getString("name"));
            ((Button)findViewById(R.id.Answer3)).setText(aL.objList[2].getString("name"));
            ((Button)findViewById(R.id.Answer4)).setText(aL.objList[3].getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }}
    }

    private void loadNextImage( String name){

        GetImagePokeService.startActionGetImagePoke(this, name);

        File[] file = getCacheDir().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.equals("pokeImage.png")){
                    return true;
                }
                return false;

            }
        });
        //imageView.setImageBitmap(BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png"));


        ImageView image = (ImageView) findViewById(R.id.image_view_question);

        //Bitmap bm = BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png");
        //image.setImageBitmap(Bitmap.createScaledBitmap(bm,500,500,false));

        }
}


