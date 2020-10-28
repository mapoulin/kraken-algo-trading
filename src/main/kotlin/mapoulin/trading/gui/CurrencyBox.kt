package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.ComboBox
import mapoulin.trading.Currencies
import org.joda.money.CurrencyUnit

class CurrencyBox(default: CurrencyUnit? = null) : ComboBox<String>(Currencies.ALL_CURRENCIES.map { it.code }) {
    init {
        isReadOnly = true
        selectedItem = default?.code
    }
}