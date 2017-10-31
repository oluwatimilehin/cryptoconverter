package com.oluwatimilehin.cryptoconverter.cards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.addcard.AddCard
import com.oluwatimilehin.cryptoconverter.data.Card
import kotlinx.android.synthetic.main.activity_cards.*
import kotlinx.android.synthetic.main.toolbar.*



class CardsActivity : AppCompatActivity(), CardsContract.View {
    override fun showCardDeleted() {
        Snackbar.make(coordinatorLayout, "Card deleted", Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        val REQUEST_ADD_CARD = 122
    }

    override fun showAddCard() {
        val intent = Intent(this, AddCard::class.java)
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

        addCardButton.setOnClickListener { cardsPresenter.addNewCard() }

        //hide the FAB when the list is scrolled
        cardsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0 || dy < 0 && addCardButton.isShown){
                    addCardButton.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    addCardButton.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        } )
    }

    override fun onResume() {
        super.onResume()

        cardsPresenter.attachView(this)
        cardsPresenter.loadCards()
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
        val dividerItemDecoration = DividerItemDecoration(cardsRv.context,
                LinearLayoutManager(this).orientation )
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.card_divider))

        cardsRv.adapter = adapter
        cardsRv.layoutManager = LinearLayoutManager(this)
        cardsRv.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.Callback(){
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val card = cards[viewHolder?.adapterPosition!!]
                cardsPresenter.deleteCard(card)
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(cardsRv)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_CARD && resultCode == Activity.RESULT_OK) {
            Snackbar.make(coordinatorLayout, "Card successfully added", Snackbar.LENGTH_SHORT).show()
        }
    }
}
