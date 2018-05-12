package projetmobile.esiea.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Intent mainMenu = new Intent(this, MainActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        GetBiersService.startActionGetAllBiers(Questions.this, null);

        Button loadButton = (Button) findViewById(R.id.ButtonLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray list = getBiersFromFile();

                aL = AnswerList.getInstance(list);

                try {
                    ((TextView)findViewById(R.id.QuizzQuestion)).setText("Description de la bière "+aL.objList[aL.correct-1].getString("description"));
                    ((Button)findViewById(R.id.Answer1)).setText(aL.objList[0].getString("name"));
                    ((Button)findViewById(R.id.Answer2)).setText(aL.objList[1].getString("name"));
                    ((Button)findViewById(R.id.Answer3)).setText(aL.objList[2].getString("name"));
                    ((Button)findViewById(R.id.Answer4)).setText(aL.objList[3].getString("name"));

                } catch (JSONException e) {
                    e.printStackTrace();
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
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());

            }
        });
        Button A2 = (Button)findViewById(R.id.Answer2);
        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==2)
                {
                    score++;
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());


            }
        });
        Button A3 = (Button)findViewById(R.id.Answer3);
        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==3)
                {
                    score++;
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());


            }
        });
        Button A4 = (Button)findViewById(R.id.Answer4);
        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aL.correct ==4)
                {
                    score++;
                }
                actualScore++;
                ((TextView)findViewById(R.id.Score)).setText(((Integer)score).toString());
                ((TextView)findViewById(R.id.ActualScore)).setText(((Integer)actualScore).toString());

            }
        });
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


