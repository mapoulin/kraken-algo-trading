package mapoulin.trading

import org.joda.money.CurrencyUnit

object Currencies {
    val XBT: CurrencyUnit = CurrencyUnit.of("XBT")
    val USD: CurrencyUnit = CurrencyUnit.USD

    val ALL_CURRENCIES = listOf(XBT, USD)
}