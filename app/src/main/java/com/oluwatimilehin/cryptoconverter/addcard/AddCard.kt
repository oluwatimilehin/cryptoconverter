package com.oluwatimilehin.cryptoconverter.addcard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Constants
import kotlinx.android.synthetic.main.activity_add_card.*
import kotlinx.android.synthetic.main.toolbar.*

class AddCard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Add Card"

        val fromAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item, arrayOf("BTC",
                "ETH") )

        val toAdapter = ArrayAdapter<String>(this, R.layout.custom_spinner_item,
                Constants.listOfCurrencies)

        fromSpinner.setAdapter(fromAdapter)
        toSpinner.setAdapter(toAdapter)
    }
}
