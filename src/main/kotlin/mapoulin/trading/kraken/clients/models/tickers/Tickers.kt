package mapoulin.trading.kraken.clients.models.tickers

import java.math.BigDecimal
import java.time.Instant

data class Tickers(
    val symbol: TickerSymbol?,
    val last: BigDecimal,
    val lastTime: Instant
)
