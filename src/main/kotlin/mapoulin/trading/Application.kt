package mapoulin.trading

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.netflix.archaius.DefaultConfigLoader
import com.netflix.archaius.DefaultPropertyFactory
import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.jaxrs2.JAXRS2Contract
import feign.slf4j.Slf4jLogger
import mapoulin.trading.kraken.clients.KrakenFutures
import mapoulin.trading.kraken.clients.auth.AuthenticationInterceptor
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

fun main() {
    val config = DefaultConfigLoader.builder().build()
        .newLoader().load("application.properties")

    val propertyFactory = DefaultPropertyFactory.from(config)

    val apiUrl = propertyFactory.get("kraken.api.url", String::class.java)
    val publicKey = propertyFactory.get("kraken.api.public_key", String::class.java)
    val privateKey = propertyFactory.get("kraken.api.private_key", String::class.java)

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

//    val response = client.sendOrder(
//        TickerSymbol.PI_XBT_USD,
//        OrderType.LIMIT,
//        OrderSide.BUY,
//        BigInteger.TEN,
//        BigDecimal.valueOf(10_000)
//    )

    client.getAccounts()


//    val terminal: Terminal = DefaultTerminalFactory().apply {
//        setTerminalEmulatorTitle("")
//    }.createTerminal()
//
//    terminal.enterPrivateMode()
//
//    val graphics = terminal.newTextGraphics()
//    val origin = terminal.cursorPosition
//
//    var keyStroke: KeyStroke = terminal.readInput()
//
//    while (keyStroke.character != 'q') {
//        graphics.putString(origin.row, origin.column, "Last Keystroke: ", SGR.BOLD)
//
//        graphics.putString(
//            origin.row + 1 + "Last Keystroke: ".length, origin.column,
//            keyStroke.character.toString()
//        )
//
//        terminal.flush()
//        keyStroke = terminal.readInput();
//    }
//
//    terminal.close()
}