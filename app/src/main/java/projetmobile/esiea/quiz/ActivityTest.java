package projetmobile.esiea.quiz;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

public class ActivityTest extends AppCompatActivity {
    String name;
    JSONArray ja;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        String keyword= "bullbasaure";
        intent.putExtra(SearchManager.QUERY, keyword);
        startActivity(intent);
        /*GetPokeService.startActionGetAllPok(this);
        ja = Toolbox.getJSONArrayFromFilePoke(this, "poke.json");
        Random rand = new Random();
        name = null;
        try {
            name = ja.getJSONObject(rand.nextInt()%10).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GetImagePokeService.startActionGetImagePoke(this, name);

        final Intent intent = new Intent(this, ActivityTest.class);


        Button bouton = (Button)findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextImage(intent);
            }
        });

    }
    private void loadNextImage(Intent intent){
        try {
            Random rand = new Random();
            name = ja.getJSONObject(rand.nextInt()%10).getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        Log.d("hihihi",String.valueOf(file[0].getTotalSpace()));
        //imageView.setImageBitmap(BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png"));


                ImageView image = (ImageView) findViewById(R.id.iv);
        Log.d("hihihi1",String.valueOf(file[0].getTotalSpace()));

        Bitmap bm = BitmapFactory.decodeFile(getCacheDir() + "/" + "pokeImage.png");
        image.setImageBitmap(Bitmap.createScaledBitmap(bm,500,500,false));

        Log.d("hihihi2",String.valueOf(file[0].getTotalSpace()));



    }*/}
}
