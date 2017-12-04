package com.oluwatimilehin.cryptoconverter.conversion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.conversion.di.ConversionModule
import com.oluwatimilehin.cryptoconverter.utils.Constants
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_convert_amount.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ConversionActivity : AppCompatActivity(), ConversionContract.View {
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var presenter: ConversionPresenter

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

        injectComponents()

        presenter.setAmount(rate)

        //When the fromField is being updated, update the toField
        disposables.add(RxTextView.textChanges(fromField)
                .filter { fromField.hasFocus() }
                .subscribe({ s ->
                    if (s.isNotEmpty()) presenter.convertAmount(s.toString(), "from")
                    else toField.setText("")
                }, { Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show(); }))

        disposables.add(RxTextView.textChanges(toField)
                .filter { toField.hasFocus() }
                .subscribe(
                        {  s: CharSequence ->
                            if( s.isNotEmpty()) presenter.convertAmount(s.toString(), "to")
                            else  toField.setText("")
                        },
                        {
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }))
    }

    private fun injectComponents() {
        (application as App)
                .appComponent
                .plus(ConversionModule(this))
                .inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun showAmount(amount: String, conversion: String) {
        when (conversion) {
            "from" -> {
                toField.setText(amount)
            }
            "to" -> {
                fromField.setText(amount)
            }
        }
    }
}
