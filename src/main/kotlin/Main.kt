import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

suspend fun main(args: Array<String>) {

    val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    log("HTTP Client $message" )
                }
            }
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    val quote : List<Quote> = client.get("https://api.quotable.io/quotes/random/").body()
    println(quote[0].content)
    println(quote[0].author)
}