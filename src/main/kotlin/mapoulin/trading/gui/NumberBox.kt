package mapoulin.trading.gui

import java.util.regex.Pattern

class NumberBox(init: String? = null) : KTextBox(init) {
    companion object {
        val FLOAT_PATTERN: Pattern = Pattern.compile("^[0-9]+(\\.[0-9]*)?\$")
    }

    init {
        setValidationPattern(FLOAT_PATTERN)
    }
}