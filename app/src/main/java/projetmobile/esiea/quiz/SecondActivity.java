package projetmobile.esiea.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SecondActivity extends AppCompatActivity {
    Intent mainMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        setContentView(R.layout.activity_second);
        mainMenu = new Intent(this, MainActivity.class);
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

        ImageButton pokebeerButton = (ImageButton) findViewById(R.id.Theme3);
        pokebeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsIntent.putExtra("TYPEQUIZZ", QuestionsType.pokemonorbeer.ordinal());
                startActivity(questionsIntent);
            }
        });
    }
}
