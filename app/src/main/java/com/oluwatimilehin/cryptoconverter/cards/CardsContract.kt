package com.oluwatimilehin.cryptoconverter.cards;

import com.oluwatimilehin.cryptoconverter.data.Card;

import java.util.List;

/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

public interface CardsContract {

    public interface View{
        void showProgressIndicator();
        void hideProgressIndicator();
        void updateRecyclerView(List<Card> cards);
    }

    public interface Presenter{
        void loadDataFromApi();
        void saveDataInDb();
        void loadDataFromDb();
    }
}
