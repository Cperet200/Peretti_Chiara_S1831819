package org.me.gcu.Peretti_Chiara_S1831819;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private List<Roadworks> roadworksList;
    private Context ct;


    public recyclerAdapter(Context ct, List<Roadworks> roadworksList){
        this.roadworksList = roadworksList;
        this.ct = ct;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameText;
        ConstraintLayout roadworkLayout;

        public MyViewHolder(final View view){
            super(view);
            nameText = view.findViewById(R.id.textView2);
            roadworkLayout = view.findViewById(R.id.roadworkLayout);
            }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        String title = roadworksList.get(position).getTitle();
        String description = roadworksList.get(position).getDescription();
        String point = roadworksList.get(position).getGeorss();
        holder.nameText.setText(title);
        holder.roadworkLayout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ct,  RoadworkActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("point", point);
                ct.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return roadworksList.size();
    }
}
