package com.codecool.eyerelaxer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codecool.eyerelaxer.dao.CardDaoImplementation;
import com.codecool.eyerelaxer.model.Card;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private LayoutInflater layoutInflater;
    private CardDaoImplementation cardsDao = CardDaoImplementation.getCardDao();

    public CardRecyclerViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemV = layoutInflater.inflate(R.layout.schedule_card, parent, false);
        return new CardViewHolder(itemV);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card currentCard = cardsDao.getCardByIndex(position);
        if (currentCard != null) {
            //set title and header for the card
            holder.titleTextV.setText(currentCard.getTitle());
            if (currentCard.getContent() != null) {
                holder.headerTextV.setText(currentCard.getContent());
            }
            // set schedules TextView content
            if (currentCard.getScheduleCount() > 0) {
                holder.scheduleTextV.setText(currentCard.getSchedulesAsString());
            }

        }
    }



    @Override
    public int getItemCount() {
        return cardsDao.getCardsCount();
    }
}
