package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Label

class ComputedLabel(private val compute: () -> String) : Label(compute.invoke()) {
    fun refresh() {
        text = compute.invoke()
    }
}