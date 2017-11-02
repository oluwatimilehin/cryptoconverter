package com.oluwatimilehin.cryptoconverter.currencyconversion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Constants
import kotlinx.android.synthetic.main.activity_convert_amount.*
import kotlinx.android.synthetic.main.toolbar.*

class ConvertAmountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_amount)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Convert Amount"

        val bundle = intent.extras

        fromLabel.text = bundle.getString(Constants.KEY_FROM)
        toLabel.text = bundle.getString(Constants.KEY_TO)
        toField.hint = bundle.getDouble(Constants.KEY_AMOUNT).toString()
    }
}
