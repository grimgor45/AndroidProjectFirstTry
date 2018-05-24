package projetmobile.esiea.quiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Intent mainMenu = new Intent(this, MainActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        int scrollViewHeight = (int)Toolbox.ScHgt(this)/4;
        ScrollView scrollView = (ScrollView) findViewById(R.id.ScrollViewDescriptionBeer);
        scrollView.getLayoutParams().height = scrollViewHeight;


        typequizz = getIntent().getIntExtra("TYPEQUIZZ",0);
        if(typequizz == 0) {
            GetBiersService.startActionGetAllBiers(Questions.this);


            listBeer = Toolbox.getJSONArrayFromFileBeer(this, JSONARRAY_NAME_BEER);
            if (listBeer.length()>=4) {
                changeAnswerList(listBeer, "description");
            }
        }
        if(typequizz == 1) {
            Log.d("hey", "alizjdoiqshdjkskjjs");
            GetPokeService.startActionGetAllPok(Questions.this);

            Log.d("IT", "works");
            listPoke = Toolbox.getJSONArrayFromFilePoke(this, JSONARRAY_NAME_POKE);
            if (listPoke.length()>=4) {
                changeAnswerList(listPoke, "description");
            }
        }




        Button loadButton = (Button) findViewById(R.id.ButtonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typequizz == 0) {
                    if (listBeer.length() >= 4) {
                        changeAnswerList(listBeer, "description");
                    }
                }
                if (typequizz == 1){
                    if(listPoke.length()>=4){
                        changeAnswerList(listPoke, valueQuestion);
                    }
                }
            }
        });

        Button A1 = (Button)findViewById(R.id.Answer1);
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(1);
            }
        });
        Button A2 = (Button)findViewById(R.id.Answer2);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(2);
            }
        });
        Button A3 = (Button)findViewById(R.id.Answer3);
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(3);
            }
        });
        Button A4 = (Button)findViewById(R.id.Answer4);
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageAnswerButton(4);
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
            String text = "Wrong answer"+" right was "+goodAnswer;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        actualScore++;
        ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
        ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
        if (typequizz == 0) {
            changeAnswerList(listBeer, "description");
        }
        if(typequizz == 1){
            changeAnswerList(listPoke, valueQuestion);
        }
    }

    private void changeAnswerList(JSONArray list, String JSONValueQestion){
        aL = AnswerList.getInstance(list);

        try {
            ((TextView)findViewById(R.id.QuizzQuestionText)).setText("Description de la bière "+aL.objList[aL.correct-1].getString(JSONValueQestion));
            ((Button)findViewById(R.id.Answer1)).setText(aL.objList[0].getString("name"));
            ((Button)findViewById(R.id.Answer2)).setText(aL.objList[1].getString("name"));
            ((Button)findViewById(R.id.Answer3)).setText(aL.objList[2].getString("name"));
            ((Button)findViewById(R.id.Answer4)).setText(aL.objList[3].getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


