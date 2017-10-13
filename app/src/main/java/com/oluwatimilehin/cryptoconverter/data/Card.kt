package com.oluwatimilehin.cryptoconverter.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
@Entity(tableName = "cards",
        indices = arrayOf(Index(value = *arrayOf("from","to"),
                unique = true)
                )
        )
class Card(val cardName: String, val from: String, val to: String, val amount: Double){
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0;
}