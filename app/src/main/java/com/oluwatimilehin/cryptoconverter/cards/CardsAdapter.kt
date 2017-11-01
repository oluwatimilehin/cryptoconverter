package com.oluwatimilehin.cryptoconverter.cards

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oluwatimilehin.cryptoconverter.R
import com.oluwatimilehin.cryptoconverter.data.Card
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

class CardsAdapter(var cards: List<Card>) : RecyclerView.Adapter<CardsAdapter
.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.card_item,
                parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val card = cards[position]
        holder.bindItems(card)
    }

    override fun getItemCount(): Int {
       return cards.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bindItems(card: Card){
                Glide.with(itemView.context)
                        .load("")
                        .apply(RequestOptions().placeholder(card.flag))
                        .into(itemView.flagContainer)

                itemView.mainCurrency.text = card.to
                itemView.bitcoinCurrency.text = card.from
                itemView.amountField.text = card.amount.toString()
                itemView.currencyNameField.text = card.currencyName
            }
      }

    fun updateList(cards: List<Card>){
        this.cards = cards
        notifyDataSetChanged()
    }
}