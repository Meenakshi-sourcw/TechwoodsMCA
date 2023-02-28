package com.example.techwoodsmca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    public Context mContext;

    private String[] mTitles = { "Profile", "Events", "Logout" };

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView;
        public Context mContext;

        public ViewHolder(View view,Context context) {
            super(view);
            mContext = context;
            mTextView = view.findViewById(R.id.textview);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            switch (position) {
                case 0:
                    Intent profileIntent = new Intent(mContext, UserProfile.class);
                    mContext.startActivity(profileIntent);
                    break;
                case 1:
                    Intent eventsIntent = new Intent(mContext, Events.class);
                    mContext.startActivity(eventsIntent);
                    break;
                case 2:
                    SharedPrefManager.getInstance(mContext).logout();

                    Intent logoutIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(logoutIntent);
                    break;
            }

        }
    }


    public DrawerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_item, parent, false);
        return new ViewHolder(view,mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mTitles[position]);
    }
    @Override
    public int getItemCount() {
        return mTitles.length;
    }
}

