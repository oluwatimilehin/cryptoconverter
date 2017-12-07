# Cryptocurrency Converter App

This repository contains an Android application for converting between cryptocurrencies and some of the major world currencies.   [App link](https://drive.google.com/open?id=1yrS6j736p9nLUrxtIf1In9FEbm6uVla2)

It implements the MVP architecture pattern and adapts Google's Room architecture component.
Data is gotten from the Cryptocompare API.

A user can create a card to convert from cryptocurrency to another currency anytime after the conversion rates have been saved
to the database the first time. Each time the exchange rates are updated, the cards that a user has created get updated with the new data.  Clicking on a card takes the user to a screen where he/she can enter an amount to perform conversion between currencies.

<p align="center">
  <img src="/screenshots/home.png" height="600" width="360">
  <img src="/screenshots/add_card.png" height="600" width="360" >
  <img src="/screenshots/convert.png" height="600" width="360">
  <img src="/screenshots/home_empty.png" height="600" width="360">
</p>
  
  
 ## Libraries
 * [Currency Picker](https://github.com/midorikocak/currency-picker-android) - For loading currencies data
 * Retrofit - for Networking
 * Gson - for parsing the JSON responses
 * Glide - for image loading
 * Room - for local data persistence
 * Dagger 2- for dependency injection
 * RxJava/RxAndroid - for reactive programming
 * UI: RecyclerView, CardView, ConstraintLayout.
    * [RxBindings](https://github.com/JakeWharton/RxBinding)
    * [Circle Image View](https://github.com/hdodenhof/CircleImageView)
    * [Material Spinner](https://github.com/ganfra/MaterialSpinner)
    * [AV Loading Indicator](https://github.com/81813780/AVLoadingIndicatorView)
    * [Material Values](https://github.com/AoDevBlue/MaterialValues)
 * Unit Testing:
    * [JUnit4](https://github.com/junit-team/junit4)
    * [Mockito](https://github.com/mockito/mockito)
 * Developer Debugging Tools:
    * [Stetho](https://github.com/facebook/stetho)
    * [LeakCanary](https://github.com/square/leakcanary)
  
 ## Testing
 This projects has local unit tests powered by JUnit and mockito for mocking objects.



