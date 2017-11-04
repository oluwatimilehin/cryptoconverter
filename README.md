# cryptoconverter

This repository contains an Android application for converting between cryptocurrency and some of the major world currencies. 

It implements MVP architecture and makes use of RxJava and Google's Room architecture component. It gets its data from the Cryptocompare API.

A user can create a card to convert from cryptocurrency to another currency anytime after the conversion rates have been saved
to the database the first time.

Each time the exchange rates are updated, the cards that a user has created get updated with the new data. 

Clicking on a card takes the user to a screen where he/she can enter an amount to perform conversion between currencies.

<p align="center">
  <img src="/screenshots/home.png">
  <img src="/screenshots/add_card.png">
  <img src="/screenshots/convert.png">
  <img src="/screenshots/home_empty.png">
</p>



