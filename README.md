Vending Machine Application
===

Instructions
---

```$xslt
> git clone https://github.com/desertsystems/vending.git
> cd vending
> mvn package -Dmaven.test.skip=true
> mvn -q spring-boot:run -Drun.arguments="coins.json"
```
*(you should see an interactive menu, if successful. If you do, follow the instructions on the menu)*


Design
---

**API Classes**
* VendingMachineService
* VendingMachineServiceImpl
* Coin
* CoinRepository
* InsufficientChangeException
* Transaction
* TransactionType
* RemoveStrategy
* RemoveByDescendingCoinValue
* RemoveByAscendingCoinValue

**Client Classes**

* Menu
* OptionType
* VendingMachineApplication (main)
* VendingMachine
* VendingMachineMenu
* VendingMachineMainMenu
* VendingMachineDepositMenu
* VendingMachineRemoveMenu
* VendingMachineMenuFactory
* VendingMachineNavigator
* VendingMachineProperties


```
Initialise Vending Machine

VendingMachine                  VendingMachineService            VendingMachineServiceImpl    CoinRepository
--------------                  ---------------------            -------------------------    --------------
  |                                       |                                  |                     |
  | Initialise with set of coins          |                                  |                     |
  |-------------------------------------->|                                  |                     |
  |                                       |  initialise(Map<Coin, Integer>)  |                     |
  |                                       |--------------------------------->| populate repository |
  |                                       |                                  |-------------------->|
  |                                       |         boolean                  |<--------------------|
  |                                       |<---------------------------------|     boolean         |
  |    notify initialied state            |                                  |                     |
  |<--------------------------------------|                                  |                     |
  |                                       |                                  |                     |
```

```
Deposit Coins

VendingMachine                  VendingMachineService            VendingMachineServiceImpl    CoinRepository
--------------                  ---------------------            -------------------------    --------------
  |                                       |                                  |                     |
  |         Transact coins                |                                  |                     |
  |-------------------------------------->|                                  |                     |
  |                                       |      deposit(Transaction)        |                     |
  |                                       |--------------------------------->| populate repository |
  |                                       |                                  |-------------------->|
  |                                       |         boolean                  |<--------------------|
  |                                       |<---------------------------------|     boolean         |
  |    notify deposit state               |                                  |                     |
  |<--------------------------------------|                                  |                     |
  |                                       |                                  |                     |
```

```
Remove Coins

VendingMachine                  VendingMachineService            VendingMachineServiceImpl    CoinRepository
--------------                  ---------------------            -------------------------    --------------
  |                                       |                                  |                          |
  |         Transact coins                |                                  |                          |
  |-------------------------------------->|                                  |                          |
  |                                       |      remove(Transaction)         |                          |
  |                                       |--------------------------------->|     remove stragegy      |
  |                                       |                                  |------------------------->|
  |                                       |         Transactio               |<-------------------------|
  |                                       |<---------------------------------|     boolean              |
  |    notify remove state                |                                  |                          |
  |<--------------------------------------|                                  |                          |
  |                                       |                                  |                          |
```

Design decisions 
---
1. Coin object was modelled, rather than using an enum, so the coins can be defined externally via properties file or via coins.json (run parameter).
2. Strategy pattern was used to remove a sum of coins from the machine. The algorithm can be changed from properties file and new algorithms can be implemented in the future.
3. The client code is packed in with the API code for spring config simplicity and time restriction. Otherwise it would be developed as a different project (external to the API code).

Coin file
---
```$xslt
[
  {
    "option": 1,
    "label": "1p",
    "type": "PENCE",
    "value": 0.01,
    "reset": 100
  },
  {
    "option": 2,
    "label": "2p",
    "type": "TWO_PENCE",
    "value": 0.02,
    "reset": 100
  },
  {
    "option": 3,
    "label": "5p",
    "type": "FIVE_PENCE",
    "value": 0.05,
    "reset": 100
  },
  {
    "option": 4,
    "label": "10p",
    "type": "TEN_PENCE",
    "value": 0.1,
    "reset": 100
  },
  {
    "option": 5,
    "label": "20p",
    "type": "TWENTY_PENCE",
    "value": 0.2,
    "reset": 100
  },
  {
    "option": 6,
    "label": "50p",
    "type": "FIFTY_PENCE",
    "value": 0.5,
    "reset": 100
  },
  {
    "option": 7,
    "label": "£1",
    "type": "POUND",
    "value": 1,
    "reset": 100
  },
  {
    "option": 8,
    "label": "£2",
    "type": "TWO_POUND",
    "value": 2,
    "reset": 100
  },
  {
    "option": 9,
    "label": "£10",
    "type": "TEN_POUND",
    "value": 10,
    "reset": 100
  }
]
```
