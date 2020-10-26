package mapoulin.trading.kraken.clients.models.accounts

import mapoulin.trading.kraken.clients.models.KrakenError
import mapoulin.trading.kraken.clients.models.KrakenResult
import java.time.Instant

data class AccountResponse(
    val result: KrakenResult,
    val error: KrakenError? = null,
    val serverTime: Instant,
    val accounts: Accounts
)