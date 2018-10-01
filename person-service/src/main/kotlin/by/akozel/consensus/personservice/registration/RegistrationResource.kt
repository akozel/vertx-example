package by.akozel.consensus.personservice.registration

import by.akozel.consensus.core.config.logging.Loggable
import by.akozel.consensus.core.config.response.ResponseFactory
import by.akozel.consensus.core.config.logging.loggable
import by.akozel.consensus.personservice.Context
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

        router
                .get("/mono")
                // .handler(auth::user)
                .produces(HttpHeaderValues.APPLICATION_JSON.toString())
                .handler { item: RoutingContext -> this.httpGetMono(item) }

        router
                .get("/bus")
                // .handler(auth::user)
                .produces(HttpHeaderValues.APPLICATION_JSON.toString())
                .handler { item: RoutingContext -> this.httpGetBus(item) }
    }

    private fun httpGet(routingContext: RoutingContext) {
        var result = 1
        for(i in 1..10000000) {
            result += 2
        }
        ResponseFactory.text(routingContext, result.toString())
    }

    private fun httpGetMono(routingContext: RoutingContext) {
        var option = 2
        RegistrationService
                .doWork(option)
                .subscribe { response -> ResponseFactory.text(routingContext, response) }
    }

    private fun httpGetBus(routingContext: RoutingContext) {

        Context.eventBus.send<String>("registration.worker", "Yay! some message") { ar ->
            if (ar.succeeded()) {
                ResponseFactory.text(routingContext, ar.result().body())
            }
        }
    }

    private fun httpPost(routingContext: RoutingContext) {

    }

}
