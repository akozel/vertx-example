package by.akozel.consensus.core.exception

import by.akozel.consensus.core.config.response.ResponseFactory
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import java.util.*

class ExceptionHandler(router: Router) {

    init {
        router.route().last()
                .handler { context: RoutingContext ->
                    ResponseFactory.notFound(context)
                }

        router.route().failureHandler { failureRoutingContext: RoutingContext ->

            val throwable = failureRoutingContext.failure()

            when (throwable) {
                is IllegalArgumentException -> ResponseFactory.badRequest(failureRoutingContext)
                is NoSuchElementException -> ResponseFactory.notFound(failureRoutingContext)
                is RuntimeException -> ResponseFactory.error(throwable, failureRoutingContext)
                else -> ResponseFactory.error(throwable, failureRoutingContext)
            }
        }

    }

}
