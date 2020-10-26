package mapoulin.trading.kraken.clients.models.tickers

import com.fasterxml.jackson.annotation.JsonProperty

enum class TickerSymbol {
    @JsonProperty("pi_xbtusd")
    PI_XBT_USD;

    // Feign uses toString() to serialise QueryParams
    override fun toString(): String {
        val field = this.declaringClass.getField(this.name)
        val annotation = field.getAnnotation(JsonProperty::class.java)
        return annotation?.value ?: this.name
    }
}