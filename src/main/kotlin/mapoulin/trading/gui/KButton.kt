package mapoulin.trading.gui

import com.googlecode.lanterna.gui2.Button


class KButton(
    label: String,
    action: () -> Unit = {},
) : Button(label, action) {
    fun invoke() {
        triggerActions()
    }

//    fun isShortcut(keyStroke: KeyStroke): Boolean {
//        if (keyStroke.keyType != KeyType.Character) {
//            return false
//        }
//
//        val char = Normalizer.normalize(keyStroke.character.toString(), Normalizer.Form.NFD)
//
//        return keyStroke.isAltDown && char == label[0].toLowerCase().toString()
//    }
//
//    override fun isActivationStroke(keyStroke: KeyStroke): Boolean {
//        return isShortcut(keyStroke) || super.isActivationStroke(keyStroke)
//    }
}