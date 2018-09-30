package by.akozel.consensus.personservice.registration

import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.response.ResponseFactory
import by.akozel.consensus.core.config.logging.loggable
import io.netty.handler.codec.http.HttpHeaderValues
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import reactor.core.publisher.Mono

class RegistrationResource(router: Router, private val options: RegistrationOptions) : Loggable {

    private val logger by loggable()

    init {
        router
                .get("/")
                // .handler(auth::user)
                .produces(HttpHeaderValues.APPLICATION_JSON.toString())
                .handler { item: RoutingContext -> this.httpGet(item) }
    }


    private fun httpGet(routingContext: RoutingContext) {
        Mono.just("hello world ${options.counter}").subscribe { response -> ResponseFactory.json(routingContext, response) }
    }

    private fun httpPost(routingContext: RoutingContext) {

    }

}
