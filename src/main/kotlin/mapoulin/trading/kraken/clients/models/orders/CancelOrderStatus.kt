package mapoulin.trading.kraken.clients.models.orders

import com.fasterxml.jackson.annotation.JsonProperty

enum class CancelOrderStatus {
    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("filled")
    FILLED,

    @JsonProperty("notFound")
    NOT_FOUND
}
