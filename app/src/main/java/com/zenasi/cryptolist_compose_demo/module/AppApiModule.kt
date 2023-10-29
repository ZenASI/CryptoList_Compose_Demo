package com.zenasi.cryptolist_compose_demo.module


import com.zenasi.cryptolist_compose_demo.model.BaseResponse
import retrofit2.Response
import retrofit2.http.*

interface AppApiModule {
    companion object {
        private const val apiVer = "v2"
    }

    /**
     * The asset price is a volume-weighted average calculated by collecting ticker data from exchanges. Each exchange contributes to this price in relation to their volume, meaning higher volume exchanges have more affect on this global price. All values are translated into USD (United States Dollar) and can be translated into other units of measurement through the /rates endpoint.
     */
    @GET("/$apiVer/assets")
    suspend fun asset(@QueryMap(encoded = true) data: Map<String, String>): Response<BaseResponse>

    @GET("/$apiVer/assets/{id}")
    suspend fun assetById(@Path(value = "id", encoded = true) id: String): Response<BaseResponse>

    /**
     * interval	required	m1, m5, m15, m30, h1, h2, h6, h12, d1
     */
    @GET("/$apiVer/assets/{id}/history")
    suspend fun assetByIdForHistory(
        @Path(value = "id", encoded = true) id: String,
        @QueryMap(encoded = true) data: Map<String, String>
    ): Response<BaseResponse>

    @GET("/$apiVer/assets/{id}/markets")
    suspend fun assetByIdForMarket(
        @Path(value = "id", encoded = true) id: String,
        @QueryMap(encoded = true) data: Map<String, String>
    ): Response<BaseResponse>

    /**
     * All prices on the CoinCap API are standardized in USD (United States Dollar). To make translating to other values easy, CoinCap now offers a Rates endpoint. We offer fiat and top cryptocurrency translated rates. Fiat rates are available through OpenExchangeRates.org.
     */
    @GET("/$apiVer/rates")
    suspend fun rate(): Response<BaseResponse>

    @GET("/$apiVer/rates/{id}")
    suspend fun rateById(@Path(value = "id", encoded = true) page: String): Response<BaseResponse>

    /**
     * The /exchanges endpoint offers an understanding into the where cryptocurrency is being exchanged and offers high-level information on those exchanges. CoinCap strives to provide transparency in the recency of our exchange data. For that purpose you will find an "updated" key for each exchange. For more details into coin pairs and volume, see the /markets endpoint.
     */
    @GET("/$apiVer/exchanges")
    suspend fun exchange(): Response<Unit>

    @GET("/$apiVer/exchanges/{id}")
    suspend fun exchangeById(
        @Path(
            value = "id",
            encoded = true
        ) page: String
    ): Response<BaseResponse>

    /**
     * Take a closer look into exchanges with the /markets endpoint. Similar to the /exchanges endpoint, we strive to offer transparency into how real-time our data is with a key identifying when the market was last updated. For a historical view on how a market has performed, see the /candles endpoint. All market data represents actual trades processed, orders on an exchange are not represented. Data received from individual markets is used to calculate the current price of an asset.
     */
    //Key	Required	Value	Description
    //exchangeId	optional	poloniex	search by exchange id
    //baseSymbol	optional	BTC	returns all containing the base symbol
    //quoteSymbol	optional	ETH	returns all containing the quote symbol
    //baseId	optional	bitcoin	returns all containing the base id
    //quoteId	optional	ethereum	returns all containing the quote id
    //assetSymbol	optional	BTC	returns all assets containing symbol (base and quote)
    //assetId	optional	bitcoin	returns all assets containing id (base and quote)
    //limit	optional	5	max limit of 2000
    //offset	optional	1	offset
    @GET("/$apiVer/markets")
    suspend fun market(): Response<BaseResponse>

    /**
     * The /candles endpoint offers a look into how a market has performed historically. This data is represented in OHLCV candles - Open, High, Low, Close, and Volume. Please note that many parameters are required to request candles for a specific market. Candle history goes back to the inception of an exchange - you may even find data for exchanges that have come and gone!
     */
    //Key	Required	Value	Description
    //exchange	required	poloniex	exchange id
    //interval	required	m1, m5, m15, m30, h1, h2, h4, h8, h12, d1, w1	candle interval
    //baseId	required	ethereum	base id
    //quoteId	required	bitcoin	quote id
    //start	optional	1528410925604	UNIX time in milliseconds. omiting will return the most recent candles
    //end	optional	1528411045604	UNIX time in milliseconds. omiting will return the most recent candles
    @GET("/$apiVer/candles")
    suspend fun candles(): Response<BaseResponse>
}