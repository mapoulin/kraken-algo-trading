package mapoulin.trading.kraken.clients.models.orders

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class SendStatus(
    @JsonProperty("order_id")
    val orderId: String,
    val status: OrderStatus,
    val receivedTime: Instant,
    val orderEvents: List<OrderEvent>
)
