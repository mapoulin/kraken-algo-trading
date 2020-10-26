package mapoulin.trading.kraken.clients.models.orders

enum class OrderEventType {
    PLACE,
    CANCEL,
    EDIT,
    REJECT,
    EXECUTION
}