# Hello my BTC friends!

I've created an app **SMAY-B** so that you can send me bitcoins and check my account. Guess what is it short for... 😏<br/>
Everything you have to do is to run `./run/run.sh`.<br/>
Make sure you have docker and docker-compose installed.<br/>
Once it's launched you can send graphQL queries.

### 📖 To see my account's history:
```graphql
query ($historyRequest: HistoryRequest!) {
    history(historyRequest: $historyRequest) {
        datetime
        amount
    }
}
```

### 💸 To transfer BTC send:
```graphql
mutation ($transferRequest: TransferRequest!) {
    transfer(transferRequest: $transferRequest)
}
```

Here is the structure of the requests
```graphql
input HistoryRequest {
    startDatetime: DateTime
    endDatetime: DateTime
}

input TransferRequest {
    datetime: DateTime
    amount: BigDecimal
}
```

Enjoy! And don't hesitate to send as much as you can 😊
