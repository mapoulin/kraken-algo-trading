package mapoulin.trading.kraken.clients.models.orders

import mapoulin.trading.kraken.clients.models.tickers.TickerSymbol
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

data class Order(
    val orderId: String,
    val cliOrdId: String? = null,
    val timestamp: Instant,
    val lastUpdateTimestamp: Instant,
    val symbol: TickerSymbol,
    val type: OrderType,
    val side: OrderSide,
    val quantity: BigInteger,
    val limitPrice: BigDecimal,
    val stopPrice: BigDecimal? = null,
    val filled: BigDecimal,
    val reduceOnly: Boolean
)
