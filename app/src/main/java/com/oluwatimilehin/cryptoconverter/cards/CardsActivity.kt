package com.oluwatimilehin.cryptoconverter.cards

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Card

class CardsActivity : AppCompatActivity(), CardsContract.View {
    override fun onDatabaseUpdateSuccess() {
        Toast.makeText(this, "Database updated", Toast.LENGTH_SHORT).show();
    }

    override fun onApiCallErrorResponse() {
        Toast.makeText(this, "An Error occured", Toast.LENGTH_SHORT).show();
    }


    override fun displayErrorMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var cardsPresenter: CardsContract.Presenter;

    override fun showProgressIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateRecyclerView(cards: List<Card>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardsPresenter = CardsPresenter()
        cardsPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsPresenter.onDestroy()
    }
}
