package mapoulin.trading.kraken.clients.models.orders

import java.time.Instant

data class RecentOrderEvent(
    val timestamp: Instant,
    val uid: String,
    val orderPlaced: RecentOrderPlaced? = null,
    val orderCancelled: RecentOrderCancelled? = null,
    val orderRejected: RecentOrderRejected? = null,
    val executionEvent: RecentExecutionEvent? = null
)