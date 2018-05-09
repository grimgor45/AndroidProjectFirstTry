package projetmobile.esiea.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int a = 121;
    String b = ((Integer)a).toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Intent secAct = new Intent(this, SecondActivity.class);

        Button btnSecAct = (Button)findViewById(R.id.ButtonSecondActivity);
        btnSecAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(secAct);
            }
        });

        final Intent aQuizz = new Intent(this, Questions.class);

        Button Quizz = (Button)findViewById(R.id.Quizz);
        Quizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(aQuizz);
            }
        });
    }
}
