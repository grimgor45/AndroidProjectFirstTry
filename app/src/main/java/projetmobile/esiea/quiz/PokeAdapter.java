package projetmobile.esiea.quiz;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.PokeHolder> {

    private JSONArray poke;
    private  Context context;


    public PokeAdapter(JSONArray list, Context context){
        poke = list;
        this.context = context;
    }

    public void setNewPoke(JSONArray poke)
    {
        this.poke=poke;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.rv_poke_element, parent, false);
        return new PokeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeHolder holder, int position) {

        try{
            JSONObject jo = poke.getJSONObject(position);

            final String name = jo.getString("name");
            GetImagePokService.startActionGetImagePok(context, name);

            holder.name.setText(name);

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    String keyword= name;
                    intent.putExtra(SearchManager.QUERY, keyword+" pokemon");
                    v.getContext().startActivity(intent);
                }
            });
            String path = context.getCacheDir().toString() + "/" + name+".png";
            File file = new File(path);
            if(file.exists()){
                Bitmap bm = BitmapFactory.decodeFile(path);
                holder.iv.setImageBitmap(Bitmap.createScaledBitmap(bm,100,100,false));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poke.length();
    }

    class PokeHolder extends RecyclerView.ViewHolder{

        public Button name;
        public ImageView iv;

        public PokeHolder(View itemView) {
            super(itemView);
            name = ((Button)itemView.findViewById(R.id.rv_poke_element_name));
            iv = (ImageView) itemView.findViewById(R.id.rv_iv);
        }
    }

}
