# cryptoconverter

This repository contains an Android application for converting between cryptocurrency and some of the major world currencies.  [apk](https://drive.google.com/open?id=1yrS6j736p9nLUrxtIf1In9FEbm6uVla2)

It implements MVP architecture and makes use of RxJava and Google's Room architecture component. It also contains tests(unit and instrumentation) written for some of the app's components.

Data is gotten from the Cryptocompare API.

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



