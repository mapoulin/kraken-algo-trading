package mapoulin.trading

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.googlecode.lanterna.gui2.MultiWindowTextGUI
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import com.netflix.archaius.DefaultConfigLoader
import com.netflix.archaius.DefaultPropertyFactory
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.jaxrs2.JAXRS2Contract
import feign.slf4j.Slf4jLogger
import mapoulin.trading.gui.windows.OrderWindow
import mapoulin.trading.kraken.clients.KrakenFutures
import mapoulin.trading.kraken.clients.auth.AuthenticationInterceptor
import mapoulin.trading.kraken.clients.models.tickers.TickerSymbol
import mu.KotlinLogging
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.math.RoundingMode

val logger = KotlinLogging.logger {}

fun main() {
    val config = DefaultConfigLoader.builder().build()
        .newLoader().load("application.properties")

    val propertyFactory = DefaultPropertyFactory.from(config)

    val apiUrl = propertyFactory.get("kraken.api.url", String::class.java)
    val publicKey = propertyFactory.get("kraken.api.public_key", String::class.java)
    val privateKey = propertyFactory.get("kraken.api.private_key", String::class.java)

    // Registering bitcoin as a currency
    CurrencyUnit.registerCurrency("XBT", 0, 8, listOf())

    val mapper = ObjectMapper()
        .registerModule(KotlinModule())
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)
        .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    val client = Feign.builder()
        .encoder(JacksonEncoder(mapper))
        .decoder(JacksonDecoder(mapper))
        .contract(JAXRS2Contract())
        .logger(Slf4jLogger())
        .logLevel(Logger.Level.FULL)
        .requestInterceptor(AuthenticationInterceptor(publicKey.get(), privateKey.get()))
        .target(KrakenFutures::class.java, apiUrl.get())

    val accountResponse = client.getAccounts()
    val xbtBalance = Money.of(
        Currencies.XBT,
        accountResponse.accounts.fi_xbtusd.balances.xbt,
        RoundingMode.HALF_UP
    )

    val tickerResponse = client.getTickers()
    val xbtPrice = Money.of(
        Currencies.USD,
        tickerResponse.tickers.last { it.symbol == TickerSymbol.PI_XBT_USD }.last,
        RoundingMode.HALF_UP
    )

    val usdBalance = xbtBalance.convertedTo(Currencies.USD, xbtPrice.amount, RoundingMode.HALF_UP)
//    val maxLoss = usdBalance.multipliedBy(0.05, RoundingMode.HALF_UP)
//
//    val positionSize = Money.of(Currencies.XBT, 1.0)
//
//    val stopLossDelta = maxLoss.dividedBy(positionSize.amount, RoundingMode.HALF_UP)
//    val stopLossPrice = xbtPrice.minus(stopLossDelta)
//
//    println("Account balance: $xbtBalance")
//    println("XBT price: $xbtPrice")
//    println("Stop Loss 95%: $stopLossPrice")

//    val response = client.sendOrder(
//        TickerSymbol.PI_XBT_USD,
//        OrderType.LIMIT,
//        OrderSide.BUY,
//        BigInteger.TEN,
//        BigDecimal.valueOf(10_000)
//    )

    val terminal: Terminal = DefaultTerminalFactory().setTerminalEmulatorTitle("").createTerminal()
    val screen = TerminalScreen(terminal)

    terminal.use {
        screen.use {
            screen.startScreen()
            val gui = MultiWindowTextGUI(screen);
            val orderWindow = OrderWindow(usdBalance, xbtPrice)
            gui.addWindowAndWait(orderWindow)
        }
    }
}