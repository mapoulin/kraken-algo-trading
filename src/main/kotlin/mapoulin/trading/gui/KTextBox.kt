package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Interactable
import com.googlecode.lanterna.gui2.TextBox
import com.googlecode.lanterna.input.KeyStroke

open class KTextBox(init: String? = null) : TextBox(init ?: "") {
    var keyStrokeListeners: List<KeystrokeListener<in KTextBox>> = listOf()
    var focusListeners: List<FocusListener<in KTextBox>> = listOf()

    override fun afterEnterFocus(direction: Interactable.FocusChangeDirection, previouslyInFocus: Interactable?) {
        focusListeners.forEach {
            it.afterEnterFocus(this, direction, previouslyInFocus)
        }

        super.afterEnterFocus(direction, previouslyInFocus)
    }

    override fun afterLeaveFocus(direction: Interactable.FocusChangeDirection, nextInFocus: Interactable) {
        focusListeners.forEach {
            it.afterLeaveFocus(this, direction, nextInFocus)
        }

        super.afterLeaveFocus(direction, nextInFocus)
    }

    @Synchronized
    override fun handleKeyStroke(keyStroke: KeyStroke): Interactable.Result {
        keyStrokeListeners.forEach {
            val result = it.handleKeyStroke(this, keyStroke)
            if (result != Interactable.Result.UNHANDLED) {
                return@handleKeyStroke result
            }
        }

        return super.handleKeyStroke(keyStroke)
    }

    fun addFocusListener(listener: FocusListener<in KTextBox>) {
        focusListeners = focusListeners.plus(listener)
    }

    fun addKeyStrokeListener(listener: KeystrokeListener<in KTextBox>) {
        keyStrokeListeners = keyStrokeListeners.plus(listener)
    }
}