package mapoulin.trading.gui.windows

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.bundle.LanternaThemes
import com.googlecode.lanterna.gui2.BasicWindow
import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.gui2.Direction
import com.googlecode.lanterna.gui2.EmptySpace
import com.googlecode.lanterna.gui2.GridLayout
import com.googlecode.lanterna.gui2.Interactable
import com.googlecode.lanterna.gui2.Interactable.FocusChangeDirection
import com.googlecode.lanterna.gui2.Label
import com.googlecode.lanterna.gui2.LocalizedString
import com.googlecode.lanterna.gui2.Panel
import com.googlecode.lanterna.gui2.Separator
import com.googlecode.lanterna.gui2.TextBox
import com.googlecode.lanterna.gui2.Window
import mapoulin.trading.Currencies
import mapoulin.trading.gui.ClearTextBoxListener
import mapoulin.trading.gui.ComputedLabel
import mapoulin.trading.gui.CurrencyBox
import mapoulin.trading.gui.FocusListener
import mapoulin.trading.gui.KButton
import mapoulin.trading.gui.KTextBox
import mapoulin.trading.gui.NumberBox
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.BigDecimal
import java.math.RoundingMode

class OrderWindow(usdBalance: Money, xbtPrice: Money) : BasicWindow() {
    private val balance = NumberBox(usdBalance.amount.toPlainString()).apply {
        isReadOnly = true
    } to CurrencyBox(Currencies.USD)

    private val position = NumberBox("1").apply {
        addKeyStrokeListener(ClearTextBoxListener())
        addFocusListener(refreshLabels())
    } to CurrencyBox(Currencies.XBT)

    private val profit = NumberBox(xbtPrice.amount.toPlainString()).apply {
        addKeyStrokeListener(ClearTextBoxListener())
        addFocusListener(refreshLabels())
    } to CurrencyBox(Currencies.USD)

    private val entry = NumberBox(xbtPrice.amount.toPlainString()).apply {
        addKeyStrokeListener(ClearTextBoxListener())
        addFocusListener(refreshLabels())
    } to CurrencyBox(Currencies.USD)

    private val stop = NumberBox(xbtPrice.amount.toPlainString()).apply {
        addKeyStrokeListener(ClearTextBoxListener())
        addFocusListener(refreshLabels())
    } to CurrencyBox(Currencies.USD)

    private val ratioLabel: ComputedLabel = ComputedLabel {
        val profit = profit.asMoney(KTextBox::getText)
        val entry = entry.asMoney(KTextBox::getText)
        val stop = stop.asMoney(KTextBox::getText)

        val denominator = entry.minus(stop).takeUnless { it.isZero }
        val ratio = denominator?.let { profit.minus(entry).dividedBy(it.amount, RoundingMode.HALF_EVEN) }
        return@ComputedLabel ratio?.amount?.toPlainString() ?: "NaN"
    }

    private val rewardBalance = ComputedLabel {
        val profit = profit.asMoney(KTextBox::getText)
        val entry = entry.asMoney(KTextBox::getText)
        val position = position.asMoney(KTextBox::getText)
        val balance = balance.asMoney(KTextBox::getText)

        val delta = profit.minus(entry).multipliedBy(position.amount, RoundingMode.HALF_EVEN)

        // TODO calculate balance at profit price
        return@ComputedLabel balance.plus(delta).amount.toPlainString()
    } to CurrencyBox(Currencies.USD)

    private val rewardPercentage = ComputedLabel {
        val rewardBalance = rewardBalance.asMoney(Label::getText)
        val balance = balance.asMoney(KTextBox::getText)

        return@ComputedLabel rewardBalance.minus(balance)
            .dividedBy(balance.amount, RoundingMode.HALF_EVEN)
            .multipliedBy(100).amount.toPlainString()
    } to Label("%")

    private val riskBalance = ComputedLabel {
        val entry = entry.asMoney(KTextBox::getText)
        val stop = stop.asMoney(KTextBox::getText)
        val position = position.asMoney(KTextBox::getText)
        val balance = balance.asMoney(KTextBox::getText)

        val delta = entry.minus(stop).multipliedBy(position.amount, RoundingMode.HALF_EVEN)

        // TODO calculate balance at stop price
        return@ComputedLabel balance.minus(delta).amount.toPlainString()
    } to CurrencyBox(Currencies.USD)

    private val riskPercentage = ComputedLabel {
        val riskBalance = riskBalance.asMoney(Label::getText)
        val balance = balance.asMoney(KTextBox::getText)

        return@ComputedLabel balance.minus(riskBalance)
            .dividedBy(balance.amount, RoundingMode.HALF_EVEN)
            .multipliedBy(100).amount.toPlainString()
    } to Label("%")

    private val orderButton = KButton("Order")
    private val closeButton = KButton(LocalizedString.Close.toString(), this::close)

    init {
        setHints(setOf(Window.Hint.CENTERED))
        theme = LanternaThemes.getRegisteredTheme("businessmachine")

        val mainPanel = Panel().apply { layoutManager = GridLayout(3) }

        mainPanel.addComponent(leftPanel())
        mainPanel.addComponent(EmptySpace().apply { preferredSize = TerminalSize(10, 0) })
        mainPanel.addComponent(rightPanel())

        mainPanel.addComponent(Separator(Direction.HORIZONTAL).span(3))
        mainPanel.addComponent(EmptySpace().span(2))

        val actionPanel = Panel().apply { layoutManager = GridLayout(3) }.span(1, GridLayout.Alignment.END)
        actionPanel.addComponent(orderButton)
        actionPanel.addComponent(closeButton)

        mainPanel.addComponent(actionPanel)
        component = mainPanel
//
//        addWindowListener(object : WindowListenerAdapter() {
//            override fun onInput(basePane: Window, keyStroke: KeyStroke, deliverEvent: AtomicBoolean) {
//                orderButton.takeIf { it.isActivationStroke(keyStroke) }?.invoke()
//                closeButton.takeIf { it.isActivationStroke(keyStroke) }?.invoke()
//            }
//        })
    }

    private fun leftPanel(): Panel {
        val leftPanel = Panel().apply { layoutManager = GridLayout(3) }

        leftPanel.addComponent(Label("Balance: "))
        leftPanel.addComponent(balance.first)
        leftPanel.addComponent(balance.second)

        leftPanel.addComponent(Label("Position: "))
        leftPanel.addComponent(position.first)
        leftPanel.addComponent(position.second)

        leftPanel.addComponent(EmptySpace().span(3))
        leftPanel.addComponent(EmptySpace().span(3))
        leftPanel.addComponent(EmptySpace().span(3))
        leftPanel.addComponent(EmptySpace().span(3))

        leftPanel.addComponent(Label("Reward: "))
        leftPanel.addComponent(rewardPercentage.first)
        leftPanel.addComponent(rewardPercentage.second)

        leftPanel.addComponent(Label("Balance: "))
        leftPanel.addComponent(rewardBalance.first)
        leftPanel.addComponent(rewardBalance.second)

        leftPanel.addComponent(EmptySpace().span(3))

        return leftPanel
    }

    private fun rightPanel(): Panel {
        val rightPanel = Panel().apply { layoutManager = GridLayout(3) }

        rightPanel.addComponent(Label("Profit: "))
        rightPanel.addComponent(profit.first)
        rightPanel.addComponent(profit.second)

        rightPanel.addComponent(Label("Entry: "))
        rightPanel.addComponent(entry.first)
        rightPanel.addComponent(entry.second)

        rightPanel.addComponent(Label("Stop: "))
        rightPanel.addComponent(stop.first)
        rightPanel.addComponent(stop.second)

        rightPanel.addComponent(EmptySpace().span(3))

        rightPanel.addComponent(Label("Ratio: "))
        rightPanel.addComponent(ratioLabel)
        rightPanel.addComponent(EmptySpace())

        rightPanel.addComponent(EmptySpace().span(3))

        rightPanel.addComponent(Label("Risk: "))
        rightPanel.addComponent(riskPercentage.first)
        rightPanel.addComponent(riskPercentage.second)

        rightPanel.addComponent(Label("Balance: "))
        rightPanel.addComponent(riskBalance.first)
        rightPanel.addComponent(riskBalance.second)

        rightPanel.addComponent(EmptySpace().span(3))

        return rightPanel
    }

    private fun refreshLabels(): FocusListener<TextBox> = object : FocusListener<TextBox> {
        override fun afterLeaveFocus(instance: TextBox, direction: FocusChangeDirection, nextInFocus: Interactable) {
            // reset value to reset if empty
            instance.text = instance.text.takeIf { it != "" } ?: "0"

            ratioLabel.refresh()
            rewardBalance.first.refresh()
            rewardPercentage.first.refresh()
            riskBalance.first.refresh()
            riskPercentage.first.refresh()
        }
    }

    private fun <T> Pair<T, CurrencyBox>.asMoney(getText: T.() -> String): Money {
        return Money.of(CurrencyUnit.of(second.text), BigDecimal(getText(first)))
    }

    private fun <T : Component> T.span(span: Int, alignment: GridLayout.Alignment = GridLayout.Alignment.FILL): T {
        val function = when (alignment) {
            GridLayout.Alignment.FILL -> GridLayout::createHorizontallyFilledLayoutData
            GridLayout.Alignment.END -> GridLayout::createHorizontallyEndAlignedLayoutData
            else -> throw RuntimeException()
        }

        return this.apply {
            layoutData = function.invoke(span)
        }
    }
}