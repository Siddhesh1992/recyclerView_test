package com.example.recyclerview_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerview_test.models.Plans;

import java.util.List;

public class PlansAdapter extends
        RecyclerView.Adapter<PlansAdapter.ViewHolder> {
    private List<Plans> mPlans;

    //added this line
    private final OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Plans item, int position);
    }
    //added this line ends

    // Pass in the contact array into the constructor
    public PlansAdapter(List<Plans> plans, OnItemClickListener listener) { ////added OnItemClickListener listener this line
        mPlans = plans;
        this.listener = listener; //added this line
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_plan, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plans plan = mPlans.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(plan.getProduct_feature_name());
        TextView isCoveredTextView = holder.isCoveredTextView;
        Button addPremiumbtn = holder.addPremiumbtn;
        if(plan.isCovered()) {
            isCoveredTextView.setText("Covered");
            addPremiumbtn.setEnabled(true);
        }
        else {
            isCoveredTextView.setText("Not Covered");
            addPremiumbtn.setEnabled(false);
        }


        //added this line
        holder.bind(mPlans.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView isCoveredTextView;
        public Button addPremiumbtn;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.plan_name);
            isCoveredTextView = (TextView) itemView.findViewById(R.id.isCovered);
            addPremiumbtn = (Button) itemView.findViewById(R.id.addPremiumbtn);
        }

        //added this line
        public void bind(final Plans item, int position, final OnItemClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    listener.onItemClick(item, position);
//                }
//            });

            addPremiumbtn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item, position);
                }
            });
        }
    }
}
