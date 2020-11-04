package com.codecool.eyerelaxer;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextV;
    public TextView headerTextV;
    public TextView scheduleTextV;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextV = itemView.findViewById(R.id.title_text_view);
        headerTextV = itemView.findViewById(R.id.header_text_view);
        scheduleTextV = itemView.findViewById(R.id.schedules_text_view);
    }

}
