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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.addcard.AddCard
import com.oluwatimilehin.cryptoconverter.currencyconversion.ConversionActivity
import com.oluwatimilehin.cryptoconverter.data.Card
import com.oluwatimilehin.cryptoconverter.data.Constants
import kotlinx.android.synthetic.main.activity_cards.*
import kotlinx.android.synthetic.main.toolbar.*


class CardsActivity : AppCompatActivity(), CardsContract.View {

    lateinit var adapter: CardsAdapter
    private lateinit var dividerItemDecoration: DividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        cardsPresenter = CardsPresenter()

        addCardButton.setOnClickListener { cardsPresenter.addNewCard() }
        setUpRecyclerView()

        //hide the FAB when the list is scrolled
        cardsRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && addCardButton.isShown) {
                    addCardButton.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && addCardButton.visibility ==
                        View.VISIBLE) {
                    //The second condition exists because the FAB should not show when there are
                    // no currencies.
                    addCardButton.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        swipeRefresh.setOnRefreshListener { refreshData() }
    }

    override fun showConversionScreen(from: String, to: String, amount: Double) {
        val intent = Intent(this, ConversionActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.KEY_FROM, from)
        bundle.putString(Constants.KEY_TO, to)
        bundle.putDouble(Constants.KEY_AMOUNT, amount)
        intent.putExtras(bundle)

        startActivity(intent)
    }

    override fun cardsExist() {
        infoTV.visibility = View.GONE
        cardsRv.addItemDecoration(dividerItemDecoration)
    }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_refresh -> refreshData()
        }
        return true
    }

    override fun currenciesExist() {
        loadingIndicator.hide()
        if (infoTV.text != getString(R.string.no_cards_message)) {
            infoTV.visibility = View.INVISIBLE
        }
        addCardButton.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        val cards: List<Card> = ArrayList()
        adapter = CardsAdapter(cards)

        dividerItemDecoration = DividerItemDecoration(cardsRv.context,
                LinearLayoutManager(this).orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.card_divider))

        cardsRv.adapter = adapter
        cardsRv.layoutManager = LinearLayoutManager(this)

        adapter.setClickListener(object : CardsAdapter.CardClickListener {
            override fun onItemClicked(position: Int) {
                val currentCard = adapter.cards[position]
                cardsPresenter.loadDetails(currentCard)
            }
        })

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(0, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val card = adapter.cards[viewHolder?.adapterPosition!!]
                cardsPresenter.deleteCard(card)
            }
        })
        itemTouchHelper.attachToRecyclerView(cardsRv)
    }

    override fun onResume() {
        super.onResume()
        cardsPresenter.attachView(this)
        cardsPresenter.loadCards()
    }

    override fun onDestroy() {
        super.onDestroy()
        cardsPresenter.clearDisposables()
    }


    override fun showEmptyCardsError() {

        if (infoTV.visibility != View.VISIBLE) {
            infoTV.visibility = View.VISIBLE
            infoTV.text = getString(R.string.no_cards_message)
            cardsRv.removeItemDecoration(dividerItemDecoration)
        }
    }

    override fun showApiCallError() {
        Snackbar.make(coordinatorLayout, "Unable to load new data", Snackbar.LENGTH_SHORT).show()
        loadingIndicator.visibility = View.GONE

        if (infoTV.visibility == View.VISIBLE) {
            infoTV.text = getString(R.string.internet_error)
        }

        swipeRefresh.isRefreshing = false
    }

    override fun onDatabaseUpdateSuccess() {/**/
        swipeRefresh.isRefreshing = false
    }

    lateinit var cardsPresenter: CardsContract.Presenter;

    override fun updateRecyclerView(cards: List<Card>) {
        adapter.updateList(cards)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ADD_CARD && resultCode == Activity.RESULT_OK) {
            Snackbar.make(coordinatorLayout, "Card added successfully", Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun refreshData(){
        cardsPresenter.loadCurrencies()
        cardsPresenter.loadCards()
    }
}
