package com.oluwatimilehin.cryptoconverter.cards

/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter : CardsContract.Presenter{

    lateinit var view: CardsContract.View;

    override fun attachView(view: CardsContract.View) {
        this.view = view
    }


    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadDataFromApi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveDataInDb() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadDataFromDb() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}