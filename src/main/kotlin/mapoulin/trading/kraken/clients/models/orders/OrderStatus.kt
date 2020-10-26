package mapoulin.trading.kraken.clients.models.orders

import com.fasterxml.jackson.annotation.JsonProperty

enum class OrderStatus {
    @JsonProperty("placed")
    PLACED,

    @JsonProperty("cancelled")
    CANCELLED,

    @JsonProperty("invalidOrderType")
    INVALID_ORDER_TYPE,

    @JsonProperty("invalidSide")
    INVALID_SLIDE,

    @JsonProperty("invalidSize")
    INVALID_SIZE,

    @JsonProperty("invalidPrice")
    INVALID_PRICE,

    @JsonProperty("insufficientAvailableFunds")
    INSUFFICIENT_AVAILABLE_FUNDS,

    @JsonProperty("selfFill")
    SELF_FILL,

    @JsonProperty("tooManySmallOrders")
    TOO_MANY_SMALL_ORDERS,

    @JsonProperty("maxPositionViolation")
    MAX_POSITION_VIOLATION,

    @JsonProperty("marketSuspended")
    MARKET_SUSPENDED,

    @JsonProperty("marketInactive")
    MARKET_INACTIVE,

    @JsonProperty("clientOrderIdAlreadyExist")
    CLIENT_ORDER_ID_ALREADY_EXIST,

    @JsonProperty("clientOrderIdTooLong")
    CLIENT_ORDER_ID_TOO_LONG,

    @JsonProperty("outsidePriceCollar")
    OUTSIDE_PRICE_COLLAR,

    @JsonProperty("postWouldExecute")
    POST_WOULD_EXECUTE,

    @JsonProperty("iocWouldNotExecute")
    IOC_WOULD_NOT_EXECUTE,

    @JsonProperty("wouldCauseLiquidation")
    WOULD_CAUSE_LIQUIDATION,

    @JsonProperty("wouldNotReducePosition")
    WOULD_NOT_REDUCE_POSITION;
}
