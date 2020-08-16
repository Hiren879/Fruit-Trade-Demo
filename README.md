# Fruit-Trade-Demo
On this Trade platform user can trade multiple fruits at the same time and get the profit and loss status of his trades.
Trade engine ensures validation of input data and shows proper error messages in case of invalid input.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
1. Java version above 7 is recommended.
2. Maven enabled Eclipse/Net Beans IDE
3. zip of this project
4. "FruitTrade-0.0.1-SNAPSHOT.jar" jar file to run directly from cmd

### Options to run the project
1. Simplest way to run this project is to unzip the folder and import project as maven project. Run project simply by running file **TradeEngine.java** as a main program from IDE.
2. Unzip the folder go to command line, go to project folder and enter following commands. You need to locate jar file in .m2 repository. 
	- maven clean install
	- java -jar FruitTrade-0.0.1-SNAPSHOT.jar


### Instruction before using Trade Engine :

1. User should strictly follow instructions given at the starting of Trade Engine.
2. User should go thorough samples of input/output provided at the end of this file.

### Functionality :

1. Multiple fruits can be traded
2. Validations provided to prevent bad input
3. Proper messages are communicated in case of any issue/errors
4. Simple and better interaction with Trade Engine via CMD
5. Profit for specific fruit can be fetched using command :: PROFIT FRUIT_NAME


### Validations added :

1. Only positive integers are allowed for price and quantity
2. User can not enter keywords other than"BUY" or "SELL"
3. User can not place SELL order before BUY order
4. User can not SELL more quantity than he actually owns
5. User can not directly get profit before making any BUY-SELL trades.


### Points taken care when creating Trade Engine :

1. No external library is used

### Scope of improvement :
This is minimalistic version of proposed system hence there is always room to do more and add more functionality in existing application. What are they ? Here is the list of upcoming features/fixes.

1. User regex to validate input to give flexibility to User
2. Add more functionalities like give list of fruits to trade, last traded price, last traded quantity etc.


## Note for reviewer
Due to limited access and firewall restriction I need to move project from one machine to another. Hence full git history will not be available. Though most of the commit history can be found at my github link provided below. Let me know in case of any query/question. 

### Author
---

[Hiren Savalia](https://github.com/Hiren879/Fruit-Trade-Demo)


#### Sample input and output for reference

BUY APPLE 10 10

Bought 10 KG of APPLE at 10 rs.

BUY APPLE 20 20

Bought 20 KG of APPLE at 20 rs.

BUY APPLE 30 30

Bought 30 KG of APPLE at 30 rs.

SELL APPLE 30 50

Sold 50 KG of APPLE at 30 rs.

BUY BANANA 10 100

Bought 100 KG of BANANA at 10 rs.

BUY BANANA 20 200

Bought 200 KG of BANANA at 20 rs.

BUY BANANA 5 50

Bought 50 KG of BANANA at 5 rs.

SELL BANANA 20 150

Sold 150 KG of BANANA at 20 rs.

SELL BANANA 30 100

Sold 100 KG of BANANA at 30 rs.

PROFIT APPLE

Total profit on trading of fruit APPLE is :: 400

PROFIT BANANA

Total profit on trading of fruit BANANA is :: 2000

##### Sample validation output for reference


BUY BANANA -10 100

Kindly check price/quantity format. Only positive numbers are allowed.

BUY BANANA 10 -100

Kindly check price/quantity format. Only positive numbers are allowed.

BUYADFG BANANA 10 100

Kindly enter BUY or SELL keyword.

SELL BANANA 30 100

Kindly BUY something in order to SELL it.

PROFIT APPLE

Kindly Buy and Sell something to gain the profit.
