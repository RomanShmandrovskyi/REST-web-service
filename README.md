# REST-web-service
## 1. Start web service
mvn clean install jetty:run
## 2. Postman configing
1. Open Postman<br/>
2. Make 3 post requests on http://localhost:8080/rest/service/wallet/add to add 3 new wallets
## 3. Web Service opportunities
Web Service has 3 GET requests, 3 POST requests and 1 DELETE request<br/>
#### GET: (you can put this in your browser)
1. http://localhost:8080/rest/service/wallet/all - to get all existing wallet (if there is no one wallet, you will be informed)<br/>
2. http://localhost:8080/rest/service/wallet/{id}/balance - get balance of wallet with id = {id} (if wallet with such id not exist you will be informed)<br/>
*Example: http://localhost:8080/rest/service/wallet/2/balance*
3. http://localhost:8080/rest/service/wallet/{id} - get wallet with id = {id} (if wallet with such id not exist you will be informed)<br/>
*Exampple: http://localhost:8080/rest/service/wallet/2*
#### POST (only in Postman):
1. http://localhost:8080/rest/service/wallet/{id}/buy?price={price} - buy some good from wallet with id = {id} on sum = {price} (you have credit limit 50. If you exceed it you wil be informed)<br/>
*Example: http://localhost:8080/rest/service/wallet/2/buy?price=24.84*
2. http://localhost:8080/rest/service/wallet/{id}/putMoney?moneyQnt={moneyQnt} - put money on wallet with id = {id} on sum = {price} (you have max limit 1000. If you exceed it you wil be informed)<br/>
*Example: http://localhost:8080/rest/service/wallet/2/putMoney?moneyQnt=515.4*
3. http://localhost:8080/rest/service/wallet/add - add new wallet (id = previousWalletId++, balance = 100.0)
#### DELETE (only in Postman):
1. http://localhost:8080/rest/service/wallet/{id}/delete - delete wallet with id = {id} (if wallet with such id not exist you will be informed)<br/>
*Example: http://localhost:8080/rest/service/wallet/2/delete*
