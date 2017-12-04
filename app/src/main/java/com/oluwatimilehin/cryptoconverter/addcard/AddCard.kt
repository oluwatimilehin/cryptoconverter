package com.oluwatimilehin.cryptoconverter.addcard

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.addcard.di.AddCardPresenterModule
import com.oluwatimilehin.cryptoconverter.utils.Constants
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class AddCard : AppCompatActivity(), AddCardContract.View {

    @Inject
    lateinit var addCardPresenter: AddCardPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Add Card"

        injectComponents()

        val fromAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, arrayOf("BTC",
                "ETH", "LTC", "BCH"))

        val toAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item,
                Constants.getCurrenciesNames())

        fromSpinner.adapter = fromAdapter
        toSpinner.adapter = toAdapter

        saveButton.setOnClickListener { saveCard() }
    }

    private fun saveCard(){
        if (fromSpinner.selectedItem == null || toSpinner.selectedItem == null) {
            Toast.makeText(this, "You must select an item for both fields", Toast
                    .LENGTH_SHORT).show()
        } else {
            val from = fromSpinner.selectedItem as String
            val to = toSpinner.selectedItem as String
            addCardPresenter.saveCard(from, to)
        }
    }

    private fun injectComponents(){
        (application as App)
                .appComponent
                .plus(AddCardPresenterModule(this))
                .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        addCardPresenter.clearDisposables()
    }

    override fun cardExistsError() {
        Toast.makeText(this, "Card already exists", Toast.LENGTH_SHORT)
                .show()
    }

    override fun saveCardSuccess() {
        setResult(Activity.RESULT_OK)
        finish()
    }

}
