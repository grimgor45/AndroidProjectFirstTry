package projetmobile.esiea.quiz;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokeAdapter extends RecyclerView.Adapter<PokeAdapter.PokeHolder> {

    private JSONArray poke;



    public PokeAdapter(JSONArray list){
        poke = list;
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
            String name = jo.getString("name")+" height : "+jo.getString("height");
            holder.name.setText(name);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        Log.d("pokeLength ", String.valueOf(poke.length()));
        return poke.length();
    }

    class PokeHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public PokeHolder(View itemView) {
            super(itemView);
            name = ((TextView)itemView.findViewById(R.id.rv_poke_element_name));
        }
    }

}
