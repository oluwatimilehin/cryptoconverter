package com.oluwatimilehin.cryptoconverter.cards

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.addcard.AddCard
import com.oluwatimilehin.cryptoconverter.data.Card
import kotlinx.android.synthetic.main.activity_cards.*
import kotlinx.android.synthetic.main.toolbar.*

class CardsActivity : AppCompatActivity(), CardsContract.View {

    val REQUEST_ADD_CARD = 122

    override fun showAddCard() {
        val intent= Intent(this, AddCard::class.java)
        startActivityForResult(intent, REQUEST_ADD_CARD)
    }

    override fun showEmptyCurrenciesError() {
        loadingIndicator.visibility = View.VISIBLE
        loadingIndicator.show()
        infoTV.text = getString(R.string.loading_data)
        infoTV.visibility = View.VISIBLE

    }

    override fun currenciesExist() {
        loadingIndicator.hide()
        infoTV.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        cardsPresenter = CardsPresenter()
        cardsPresenter.attachView(this)
        cardsPresenter.loadCards()

        addCardButton.setOnClickListener { cardsPresenter.addNewCard() }
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsPresenter.onDestroy()
    }


    override fun showEmptyCardsError() {

    }

    override fun showApiCallError() {

    }

    override fun onDatabaseUpdateSuccess() {
        Toast.makeText(this, "Database updated", Toast.LENGTH_SHORT).show();
    }

    lateinit var cardsPresenter: CardsContract.Presenter;

    override fun updateRecyclerView(cards: List<Card>) {
        val adapter = CardsAdapter(cards)

        cardsRv.adapter = adapter
        cardsRv.layoutManager = LinearLayoutManager(this)
    }

}
