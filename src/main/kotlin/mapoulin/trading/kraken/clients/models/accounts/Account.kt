package mapoulin.trading.kraken.clients.models.accounts

data class Account(
    val type: AccountType,
    val currency: AccountCurrency? = null,
    val balances: AccountBalances
)