# gdax4s

### An unoffical Scala API for the [GDAX coin exchange](https://www.gdax.com/trade/BTC-USD)

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

### API
This is a work-in-progress library. 
The api is split into [PublicGDaxClient](./src/main/scala/com/gdax/client/publicGDaxClient.scala) and [AuthenticatedGdaxClient](./src/main/scala/com/gdax/client/AuthenticatedGDaxClient.scala) for public and private calls respectively. Api keys can be generated [here](https://www.gdax.com/settings/api) and [here for the public sandbox](https://public.sandbox.gdax.com/settings/api).

#### [PublicGDaxClient](./src/main/scala/com/gdax/client/publicGDaxClient.scala)
Implemented calls:
```scala
def products(): Future[Either[ErrorCode, List[GDaxProduct]]]

def ticker(productId: String): Future[Either[ErrorCode, Ticker]]

def topBook(productId: String): Future[Either[ErrorCode, Book]]

def fullBooks(productId: String): Future[Either[ErrorCode, FullBook]]

def top50Books(productId: String): Future[Either[ErrorCode, Book]]

def trades(productId: String, before: Option[Int] = None, after: Option[Int] = None, limit: Option[Int] = None): Future[Either[ErrorCode, List[Trades]]]

def candles(productId: String, start: Instant, end: Instant, granularity: Long): Future[Either[ErrorCode, List[Candle]]]

def time(): Future[Either[ErrorCode, Time]]

def currencies(): Future[Either[ErrorCode, List[Currencies]]]

def dailyStats(productId: String): Future[Either[ErrorCode, DailyStats]]
```

#### [AuthenticatedGDaxClient](./src/main/scala/com/gdax/client/AuthenticatedGDaxClient.scala)
Implemented calls:
```scala
def account(accountId: String): Future[Either[ErrorCode, AccountWithProfile]]

def accounts(): Future[Either[ErrorCode, List[Account]]]

def paymentMethods(): Future[Either[ErrorCode, List[PaymentMethod]]]

def coinbaseAccounts(): Future[Either[ErrorCode, List[CoinBaseAccount]]]

def depositFromPaymentMethod(amount: Double, currency: String, paymentMethodId: String): Future[Either[ErrorCode, PaymentMethodDeposit]]

def depositFromCoinbaseAccount(amount: Double, currency: String, coinbaseAccountId: String): Future[Either[ErrorCode, CoinBaseDeposit]]

def limitOrder(productId: String, side: Side, price: Double, size: Double, timeInForce: Option[TimeInForce] = None,
               cancelAfter: Option[CancelAfter] = None, stp: Option[Boolean] = None,
               postOnly: Option[Boolean] = None, clientId: Option[String] = None): Future[Either[ErrorCode, LimitOrderResponse]]
               
def marketOrder(productId: String, side: Side, stp: Option[Boolean] = None, 
                clientId: Option[String] = None, size: Option[Double] = None, funds: Option[Double] = None): Future[Either[ErrorCode, MarketAndStopOrderResponse]]
                
def stopOrder(productId: String, side: Side, price: Double, stp: Option[Boolean] = None, 
              clientId: Option[String] = None, size: Option[Double] = None, funds: Option[Double] = None): Future[Either[ErrorCode, MarketAndStopOrderResponse]]
              
def cancelOrder(orderId: String): Future[Either[ErrorCode, CanceledOrders]]

def cancelAllOrders(productId: Option[String] = None): Future[Either[ErrorCode, CanceledOrders]]
```
