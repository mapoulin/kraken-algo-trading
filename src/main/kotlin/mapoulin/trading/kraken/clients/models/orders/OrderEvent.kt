package mapoulin.trading.kraken.clients.models.orders

import java.math.BigInteger

data class OrderEvent(
    val uid: String? = null,
    val type: OrderEventType,
    val reason: OrderStatus? = null, // when type = REJECT
    val reducedQuantity: BigInteger? = null,
    val order: Order
)