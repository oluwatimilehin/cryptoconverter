package com.oluwatimilehin.cryptoconverter.cards

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Card

class CardsActivity : AppCompatActivity(), CardsContract.View {

    lateinit var cardsPresenter: CardsContract.Presenter;

    override fun showProgressIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgressIndicator() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateRecyclerView(cards: List<Card>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardsPresenter = CardsPresenter(this)
        cardsPresenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsPresenter.onDestroy()
    }
}
