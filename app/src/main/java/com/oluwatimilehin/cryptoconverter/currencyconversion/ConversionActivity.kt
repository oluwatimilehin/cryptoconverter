package com.oluwatimilehin.cryptoconverter.currencyconversion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Constants
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_convert_amount.*
import kotlinx.android.synthetic.main.toolbar.*

class ConversionActivity : AppCompatActivity(), ConversionContract.View {
    override fun showAmount(amount: String, conversion: String) {
            when(conversion){
                "from" -> {
                    toField.setText(amount)
                }
                "to" -> {
                    fromField.setText(amount)
                }
            }
    }

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_amount)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Convert Amount"

        val bundle = intent.extras
        val rate = bundle.getDouble(Constants.KEY_AMOUNT)

        fromLabel.text = bundle.getString(Constants.KEY_FROM)
        toLabel.text = bundle.getString(Constants.KEY_TO)
        toField.hint = rate.toString()

        val presenter = ConversionPresenter()
        presenter.attachView(this, rate)

        disposables.add(RxTextView.textChanges(fromField)
                .filter { fromField.hasFocus() }
                .subscribe({ s ->
                    run {
                        if (s.isNotEmpty()) {
                            presenter.convertAmount(s.toString(), "from")
                        } else {
                            toField.setText("")
                        }
                    }
                }))


        disposables.add(RxTextView.textChanges(toField)
                .filter { toField.hasFocus() }
                .subscribe({ s ->
                    run {
                        if (s.isNotEmpty()) {
                            presenter.convertAmount(s.toString(), "to")
                        } else {
                            fromField.setText("")
                        }
                    }
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
