package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Interactable
import com.googlecode.lanterna.input.KeyStroke

interface KeystrokeListener<T : Interactable> {
    fun handleKeyStroke(instance: T, keyStroke: KeyStroke): Interactable.Result
}