package mapoulin.trading.kraken.clients.models.orders

import mapoulin.trading.kraken.clients.models.KrakenError
import mapoulin.trading.kraken.clients.models.KrakenResult
import java.time.Instant

data class SendOrderResponse(
    val result: KrakenResult,
    val error: KrakenError? = null,
    val serverTime: Instant,
    val sendStatus: SendStatus? = null
)