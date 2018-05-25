package projetmobile.esiea.quiz;

import android.app.SearchManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.content.ContextCompat.startActivity;


public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

    private JSONArray biers;



    public BiersAdapter(JSONArray list){
        biers = list;
    }

    public void setNewBiers(JSONArray biers)
    {
        this.biers=biers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.rv_bier_element, parent, false);
        return new BierHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BierHolder holder, int position) {

        try{
            JSONObject jo = biers.getJSONObject(position);
            final String na =jo.getString("name");
            String name = na+" Description : "+jo.getString("description");

            holder.name.setText(name);
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    String keyword= na;
                    intent.putExtra(SearchManager.QUERY, keyword+" beer");
                    v.getContext().startActivity(intent);
                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return biers.length();
    }

    class BierHolder extends RecyclerView.ViewHolder{

        public Button name;

        public BierHolder(View itemView) {
            super(itemView);
            name = ((Button)itemView.findViewById(R.id.rv_bier_element_name));
        }
    }

}
