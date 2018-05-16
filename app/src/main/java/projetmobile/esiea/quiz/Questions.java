package projetmobile.esiea.quiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Questions extends AppCompatActivity {

    public AnswerList aL;
    public int score;
    public int actualScore;
    JSONArray list;
    private final String JSONARRAY_NAME =  "bieres.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Intent mainMenu = new Intent(this, MainActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        GetBiersService.startActionGetAllBiers(Questions.this, null);

        list = Toolbox.getJSONArrayFromFile(this, JSONARRAY_NAME);

        if (list.length()>=4) {
            changeAnswerList(list);
        }

        Button loadButton = (Button) findViewById(R.id.ButtonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray list = Toolbox.getJSONArrayFromFile(Questions.this, JSONARRAY_NAME);

                if(list.length()>=4){
                changeAnswerList(list);
                }
            }
        });

        Button A1 = (Button)findViewById(R.id.Answer1);
        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==1)
                {
                    score++;
                    Context context = getApplicationContext();
                    CharSequence text = "Correct Answer";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
                changeAnswerList(list);



            }
        });
        Button A2 = (Button)findViewById(R.id.Answer2);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==2)
                {
                    score++;
                    Context context = getApplicationContext();
                    CharSequence text = "Correct Answer";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
                changeAnswerList(list);


            }
        });
        Button A3 = (Button)findViewById(R.id.Answer3);
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==3)
                {
                    score++;
                    Context context = getApplicationContext();
                    CharSequence text = "Correct Answer";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
                changeAnswerList(list);


            }
        });
        Button A4 = (Button)findViewById(R.id.Answer4);
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==4)
                {
                    score++;
                    Context context = getApplicationContext();
                    CharSequence text = "Correct Answer";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());
                changeAnswerList(list);

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
        Context context = getApplicationContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setMessage("This will end the activity");

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        return builder;

    }


    private void changeAnswerList(JSONArray list){
        aL = AnswerList.getInstance(list);

        try {
            ((TextView)findViewById(R.id.QuizzQuestionText)).setText("Description de la bière "+aL.objList[aL.correct-1].getString("description"));
            ((Button)findViewById(R.id.Answer1)).setText(aL.objList[0].getString("name"));
            ((Button)findViewById(R.id.Answer2)).setText(aL.objList[1].getString("name"));
            ((Button)findViewById(R.id.Answer3)).setText(aL.objList[2].getString("name"));
            ((Button)findViewById(R.id.Answer4)).setText(aL.objList[3].getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


