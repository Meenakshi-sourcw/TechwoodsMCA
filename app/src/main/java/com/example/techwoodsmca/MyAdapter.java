package com.example.techwoodsmca;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    static List<Listitem> listitems;
    private Context context;

    public MyAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v= LayoutInflater.from(parent.getContext())
              .inflate(R.layout.list_item,parent, false);
      return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listitem listItem = listitems.get(position);

        holder.textViewHead.setText(listItem.getHead());
//        holder.textViewDesc.setText(listItem.getDesc());
//        holder.textViewPart.setText(listItem.getParticipants());
//        holder.textViewDatef.setText(listItem.getDate());
//        holder.textViewVenuef.setText(listItem.getVenue());
        holder.textViewCategoryf.setText(listItem.getCategory());
//        holder.textViewInchargef.setText(listItem.getIncharge());
        // Add click listener to the card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the pop-up card layout
                View popupView = LayoutInflater.from(context).inflate(R.layout.popup_card, null);

                // Initialize the pop-up window
                PopupWindow popupWindow = new PopupWindow(popupView,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // Get the event details and display them in the pop-up card
                TextView popupHead = popupView.findViewById(R.id.popup_head);
                popupHead.setText(listItem.getHead());
//
//                TextView popupCategory = popupView.findViewById(R.id.popup_category);
//                popupCategory.setText(listItem.getCategory());

                TextView popupDesc = popupView.findViewById(R.id.popup_desc);
                popupDesc.setText(listItem.getDesc());

                TextView popupParticipants = popupView.findViewById(R.id.popup_participants);
                popupParticipants.setText(listItem.getParticipants());

                TextView popupDate = popupView.findViewById(R.id.popup_date);
                popupDate.setText(listItem.getDate());

                TextView popupVenue = popupView.findViewById(R.id.popup_venue);
                popupVenue.setText(listItem.getVenue());

                TextView popupIncharge = popupView.findViewById(R.id.popup_incharge);
                popupIncharge.setText(listItem.getIncharge());

                // Add a "Close" button to the pop-up card
                Button closeButton = popupView.findViewById(R.id.popup_close_button);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Eventregister.class);
                        context.startActivity(intent);
                        intent.putExtra("event_name", popupHead.getText().toString());
                        context.startActivity(intent);
                    }
                });

                // Display the pop-up window
                popupWindow.showAtLocation(holder.itemView, Gravity.CENTER, 0, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
   public TextView textViewHead;
   public TextView textViewDesc;
   public  TextView textViewDatef;
        public  TextView textViewVenuef;
        public  TextView textViewCategoryf;
        public  TextView textViewInchargef;
        public TextView textViewPart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
//            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
//            textViewPart = (TextView) itemView.findViewById(R.id.textViewPart);
//            textViewDatef = (TextView) itemView.findViewById(R.id.textViewDatef);
//            textViewVenuef = (TextView) itemView.findViewById(R.id.textViewVenuef);
            textViewCategoryf = (TextView) itemView.findViewById(R.id.textViewC);
//            textViewInchargef = (TextView) itemView.findViewById(R.id.textViewInchargef);
        }
    }
}
