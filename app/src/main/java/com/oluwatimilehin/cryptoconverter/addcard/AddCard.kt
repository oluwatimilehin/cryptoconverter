package com.oluwatimilehin.cryptoconverter.addcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.cards.CardsActivity
import com.oluwatimilehin.cryptoconverter.data.Constants
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.toolbar.*

class AddCard : AppCompatActivity(), AddCardContract.View {

    lateinit var addCardPresenter: AddCardPresenter

    override fun cardExistsError() {
        Toast.makeText(this, "A card with these properties already exists", Toast.LENGTH_SHORT)
                .show()
    }

    override fun saveCardSuccess() {
        setResult(CardsActivity.REQUEST_ADD_CARD)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Add Card"

        addCardPresenter = AddCardPresenter()
        addCardPresenter.attachView(this)

        val fromAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, arrayOf("BTC",
                "ETH"))

        val toAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item,
                Constants.listOfCurrencies)

        fromSpinner.adapter = fromAdapter
        toSpinner.adapter = toAdapter

        saveButton.setOnClickListener { view ->
            run {
                var from: String
                var to: String

                if (fromSpinner.selectedItem == null || toSpinner.selectedItem == null) {
                    Toast.makeText(this, "You must select an item for both fields", Toast
                            .LENGTH_SHORT).show()
                } else {
                    from = fromSpinner.selectedItem as String
                    to = toSpinner.selectedItem as String
                    addCardPresenter.saveCard(from, to)
                }
            }
        }

    }
}
