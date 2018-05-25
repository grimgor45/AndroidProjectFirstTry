package projetmobile.esiea.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SecondActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent questionsIntent= new Intent(this, Questions.class);

        ImageButton pokeBouton = (ImageButton) findViewById(R.id.Theme1);
        pokeBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsIntent.putExtra("TYPEQUIZZ", QuestionsType.PokemonSprits.ordinal());
                startActivity(questionsIntent);

            }
        });
        ImageButton beerButton = (ImageButton) findViewById(R.id.Theme2);
        beerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsIntent.putExtra("TYPEQUIZZ", QuestionsType.beerDescription.ordinal());
                startActivity(questionsIntent);
            }
        });

    }
}
