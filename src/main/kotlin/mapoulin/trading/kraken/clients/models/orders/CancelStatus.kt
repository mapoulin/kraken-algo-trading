package mapoulin.trading.kraken.clients.models.orders

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class CancelStatus(
    @JsonProperty("order_id")
    val orderId: String? = null,
    val cliOrdId: String? = null,
    val receivedTime: Instant,
    val status: CancelOrderStatus,
    val orderEvents: List<OrderEvent>? = null
)
