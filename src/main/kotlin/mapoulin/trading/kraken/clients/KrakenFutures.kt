package mapoulin.trading.kraken.clients

import mapoulin.trading.kraken.clients.models.accounts.AccountResponse
import mapoulin.trading.kraken.clients.models.orders.CancelOrderResponse
import mapoulin.trading.kraken.clients.models.orders.OrderSide
import mapoulin.trading.kraken.clients.models.orders.OrderType
import mapoulin.trading.kraken.clients.models.orders.RecentOrderResponse
import mapoulin.trading.kraken.clients.models.orders.SendOrderResponse
import mapoulin.trading.kraken.clients.models.tickers.TickerSymbol
import mapoulin.trading.kraken.clients.models.tickers.TickersResponse
import java.math.BigDecimal
import java.math.BigInteger
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.QueryParam

@Path("/derivatives/api/v3")
interface KrakenFutures {
    @GET
    @Path("/recentorders")
    fun getRecentOrders(@QueryParam("symbol") symbol: TickerSymbol): RecentOrderResponse

    @GET
    @Path("/accounts")
    fun getAccounts(): AccountResponse

    @GET
    @Path("/tickers")
    fun getTickers(): TickersResponse

    @POST
    @Path("/sendorder")
    fun sendOrder(
        @QueryParam("symbol") symbol: TickerSymbol,
        @QueryParam("orderType") orderType: OrderType,
        @QueryParam("side") side: OrderSide,
        @QueryParam("size") size: BigInteger,
        @QueryParam("limitPrice") limitPrice: BigDecimal,
        @QueryParam("stopPrice") stopPrice: BigDecimal? = null,
        @QueryParam("triggerSignal") triggerSignal: String? = null,
        @QueryParam("cliOrdId") cliOrdId: String? = null,
        @QueryParam("reduceOnly") reduceOnly: Boolean? = null
    ): SendOrderResponse

    @POST
    @Path("/cancelorder")
    fun cancelOrder(
        @QueryParam("order_id") orderId: String,
        @QueryParam("cliOrdId") cliOrdId: String? = null
    ): CancelOrderResponse
}