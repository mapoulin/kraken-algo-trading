package mapoulin.trading.kraken.clients.models.orders

import java.time.Instant

data class RecentOrder(
    val uid: String,
    val timestamp: Instant,
    val direction: OrderSide,
    val quantity: Int
)