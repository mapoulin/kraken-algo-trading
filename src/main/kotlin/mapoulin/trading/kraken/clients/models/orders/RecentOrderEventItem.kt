package mapoulin.trading.kraken.clients.models.orders

import java.time.Instant

data class RecentOrderEventItem(
    val timestamp: Instant,
    val event: RecentOrderEvent
)