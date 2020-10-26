package mapoulin.trading.kraken.clients.models

import com.fasterxml.jackson.annotation.JsonProperty

enum class KrakenResult {
    @JsonProperty("success")
    SUCCESS,

    @JsonProperty("error")
    ERROR
}