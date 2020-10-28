package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Interactable

interface FocusListener<T : Interactable> {
    fun afterEnterFocus(instance: T, direction: Interactable.FocusChangeDirection, previouslyInFocus: Interactable?) {
        //By default no action
    }

    fun afterLeaveFocus(instance: T, direction: Interactable.FocusChangeDirection, nextInFocus: Interactable) {
        //By default no action
    }
}