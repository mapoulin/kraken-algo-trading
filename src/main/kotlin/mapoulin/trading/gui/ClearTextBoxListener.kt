package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Interactable
import com.googlecode.lanterna.gui2.TextBox
import com.googlecode.lanterna.input.KeyStroke

class ClearTextBoxListener : KeystrokeListener<TextBox> {
    override fun handleKeyStroke(instance: TextBox, keyStroke: KeyStroke): Interactable.Result {
        if (keyStroke.character == 'c') {
            instance.text = ""
            return Interactable.Result.HANDLED
        }

        return Interactable.Result.UNHANDLED
    }
}