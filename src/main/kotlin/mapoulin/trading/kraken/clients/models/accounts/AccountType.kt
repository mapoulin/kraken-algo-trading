package mapoulin.trading.kraken.clients.models.accounts

import com.fasterxml.jackson.annotation.JsonProperty

enum class AccountType {
    @JsonProperty("marginAccount")
    MARGIN,

    @JsonProperty("cashAccount")
    CASH
}