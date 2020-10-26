package mapoulin.trading.kraken.clients.models

import com.fasterxml.jackson.annotation.JsonProperty

enum class KrakenError(val argument: String? = null) {
    @JsonProperty("apiLimitExceeded")
    API_LIMIT_EXCEEDED,

    @JsonProperty("authenticationError")
    AUTHENTICATION_ERROR,

    @JsonProperty("accountInactive")
    ACCOUNT_INACTIVE,

    @JsonProperty("requiredArgumentMissing")
    REQUIRED_ARGUMENT_MISSING,

    @JsonProperty("invalidArgument")
    INVALID_ARGUMENT,

    @JsonProperty("nonceBelowThreshold")
    NONCE_BELOW_THRESHOLD,

    @JsonProperty("nonceDuplicate")
    NONCE_DUPLICATE
}