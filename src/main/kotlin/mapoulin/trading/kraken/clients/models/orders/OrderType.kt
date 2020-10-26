package mapoulin.trading.kraken.clients.models.orders

import com.fasterxml.jackson.annotation.JsonProperty

enum class OrderType {
    @JsonProperty("lmt")
    LIMIT,

    @JsonProperty("stp")
    STOP_LOSS,

    @JsonProperty("take_profit")
    TAKE_PROFIT,

    @JsonProperty("post")
    POST,

    @JsonProperty("ioc")
    IMMEDIATE_OR_CANCEL;

    // Feign uses toString() to serialise QueryParams
    override fun toString(): String {
        val field = this.declaringClass.getField(this.name)
        val annotation = field.getAnnotation(JsonProperty::class.java)
        return annotation?.value ?: this.name
    }
}
