package mapoulin.trading.kraken.clients.models.tickers

import mapoulin.trading.kraken.clients.models.KrakenError
import mapoulin.trading.kraken.clients.models.KrakenResult
import java.time.Instant

data class TickersResponse(
    val result: KrakenResult,
    val error: KrakenError? = null,
    val serverTime: Instant,
    val tickers: List<Tickers> = listOf()
)