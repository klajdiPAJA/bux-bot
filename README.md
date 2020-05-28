## Introduction

The BUX mobile apps communicate with the BUX backend through an HTTP RESTful API and messages exchanged over a WebSocket connection.
The data format of the REST API is structured in JSON, as well as the messages exchanged over WebSocket.
On top of the WebSocket connection, we have created an application protocol based on the concept of real-time feed "channels". 
The client can subscribe to these channels in order to receive real-time updates for it.
We leverage the 'full-duplex' nature of WebSockets: the client has to send a WebSocket message to subscribe for a channel, 
and from this moment on, until unsubscription (or disconnect), he will start receiving messages with updates on this channel over the WebSocket connection.

## Assignment Requirements
The goal of this assignment is to build a very basic Trading Bot that tracks the price of a certain product 
and will execute a pre-defined trade in said product when it reaches a given price. 
After the product has been bought the Trading Bot should keep on tracking the prices and execute a sell order when a certain price is hit. In the input there should be a "upper limit" price and a "lower limit" price.
At startup the Trading Bot should gather four inputs;
   1. The product id (see below for a suggested list to test with)
   2. The buy price
   3. The upper limit sell price this is the price you are willing to close a position and make a profit.
   4. The lower limit sell price this the price you want are willing to close a position at and make a loss.


*Note that for the trading logic to be valid, the relation between the buy price and the lower / upper limit should be: lower limit sell price < buy price < upper limit sell price. 
Think about what it means to close a position with a profit. What should the relation between the current price and the upper limit selling price should be when deciding to close the position or not?
The Trading Bot should then subscribe to the Websocket channel for the given product id and when the buy price is reached it should execute the buy order (API definition below) 
and then when one of the limits is reached it should execute the sell order



***
## How to run Trading Bot

To run the project you need **Java 8** and **Maven** in order to build the executable jar.
The executable jar is created on the `package` phase, to create it just run the following maven command:
```
$ mvn clean package
``` 

After successful build `TradingBot.jar` will be created under `target` folder in the root of the project,
and you can run it using the following command :

```
$ java -jar target/TradingBot.jar  --id=sb26493 --buyAt=10402 --lowerSell=10400 --upperSell=10405
``` 
the 4 params in command line are all required to the Bot and represent the following:

* `--id` is the product id the bot will be subscribing too and handle the trades on.
* `--buyAt` is the buying price where the bot should open a position.
* `--lowerSell` is the lower limit sell price, the price you are willing to close a position and make a loss.
* `--upperSell` is the upper limit sell price, the price you want to close the position at and make a profit.

Some product ids you can use for testing 
(in respective order these are ids for products labeled as 'Germany30', 'US500', 'EUR/USD', 'Gold', 'Apple' and 'Deutsche Bank'):
* sb26493
* sb26496
* sb26502
* sb26500
* sb26513
* sb28248

The logs of the application can be found on the console or in `app.log` file in same folder as the jar.
***

## Configuration
Some of the bot configuration can be changed using properties file in  _resources/application.properties_, the properties below can be changed:
The properties under _bux.defaultInvestment*_ are the ones that are used to build the investing amount used to open a position.
```
bux.authorization.bearer=eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg   
### LOCAL ###
#bux.ws.url=ws://localhost:8080/subscriptions/me

### BETA ###
bux.ws.url=wss://rtf.beta.getbux.com/subscriptions/me
bux.open.position.url=https://api.beta.getbux.com/core/21/users/me/trades
bux.close.position.url=https://api.beta.getbux.com/core/21/users/me/portfolio/positions/{positionId}
#defaulInvestment
bux.defaultInvestment.currency=BUX
bux.defaultInvestment.decimals=2
bux.defaultInvestment.amount=10.0
```
