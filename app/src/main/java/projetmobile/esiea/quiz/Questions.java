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
    private String correctDownloadToast;
    private String incorrectDownloadToast;
    private int toastDuration = android.widget.Toast.LENGTH_SHORT;
    Button A1;
    Button A2;
    Button A3;
    Button A4;
    Intent mainMenu = null;
    Random rand;
    Boolean beerPoke = true;
    public String hey;
    Boolean show;

    public static final String POKE_IMAGE_UPADTE= "projetmobile.esiea.quiz.POKE_UPDATE";

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

    public class ImageUpadte extends BroadcastReceiver {



        @Override
        public void onReceive(Context context, Intent intent) {
            ImageView iv = findViewById(R.id.image_view_question);
            boolean downloaded = intent.getBooleanExtra("VALUE", false);
            if (downloaded) {
                Bitmap bm = BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png");
                iv.setImageBitmap(Bitmap.createScaledBitmap(bm,500,500,false));
                iv.setVisibility(View.VISIBLE);

                A1.setEnabled(true);
                A2.setEnabled(true);
                A3.setEnabled(true);
                A4.setEnabled(true);
            }
            else{

                if(show){
                    Toast toast = Toast.makeText(context, incorrectDownloadToast, toastDuration);

                    toast.show();
                    show = false;
                }


            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mainMenu = new Intent(this, SecondActivity.class);

        show = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        correctDownloadToast = getString(R.string.Downloadsuccess);
        incorrectDownloadToast = getString(R.string.DownloadFailed);

        rand = new Random();

        int scrollViewHeight = (int)Toolbox.ScHgt(this)/4;
        ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollViewDescriptionBeer);
        scrollView.getLayoutParams().height = scrollViewHeight;


        IntentFilter intentFilter = new IntentFilter(POKE_IMAGE_UPADTE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new Questions.ImageUpadte(),intentFilter);

        ImageView imv = findViewById(R.id.image_view_question);
        imv.setVisibility(ImageView.INVISIBLE);

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
        if(typequizz == QuestionsType.pokemonorbeer.ordinal())
        {
            A3.setVisibility(View.GONE);
            A4.setVisibility(View.GONE);
        }
        if(typequizz== QuestionsType.pokemonorbeer.ordinal()){
        A1.setText(getString(R.string.Beer));
        A2.setText(getString(R.string.Pokemon));
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
            GetPokeService.startActionGetAllPok(Questions.this);

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


                tv.setText(Toolbox.getRandomElementName(listBeer, getBaseContext()));
            }
            else{
                tv.setText(Toolbox.getRandomElementName(listPoke, getBaseContext()));
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
        builder.setMessage(getString(R.string.stopquizz));

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(quitToMainAct);
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
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
            CharSequence text = getString(R.string.correctanswer);
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
            String text = getString(R.string.correctanswerwas)+goodAnswer;
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
                String str = Toolbox.getRandomElementName(listBeer, getBaseContext());
                String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                tv.setText(cap);
            }
            else{
                String str = Toolbox.getRandomElementName(listPoke, getBaseContext());
                String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                tv.setText(cap);
            }
            if (tv.getText().equals(getString(R.string.placeholder)))
            {
                changeAnswerList(null, null);
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
                ((TextView) findViewById(R.id.QuizzQuestionText)).setText(getString(R.string.beerdescription) + aL.objList[aL.correct - 1].getString(JSONValueQestion));
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

    }
}


