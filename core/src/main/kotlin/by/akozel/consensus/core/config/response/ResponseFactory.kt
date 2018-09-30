package by.akozel.consensus.core.config.response

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

object ResponseFactory {

    val APPLICATION_JSON = "application/json"
    val TEXT_PLAIN = "text/plain"
    val APPLICATION_OCTET_STREAM = "application/octet-stream"

    private val CONTENT_TYPE_HEADER = "content-type"

    init {
        Json.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        Json.prettyMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        Json.mapper.registerModule(JavaTimeModule())
        Json.prettyMapper.registerModule(JavaTimeModule())
    }

    /**
     * response with typed body
     */
    fun <T> json(routingContext: RoutingContext, contract: T) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encode(contract))
    }

    fun text(routingContext: RoutingContext, text: String) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
                .end(text)
    }

    /**
     * response with empty body
     */
    fun accepted(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(202)
                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
                .end()
    }

    fun created(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(201)
                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
                .end()
    }

    fun deleted(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(204)
                .end()
    }

    /**
     * ERRORS
     *
     * @param routingContext
     */
    // 400
    fun badRequest(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(400)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encode(ResponseMessageLibrary.RESOURCE_BAD_REQUEST_400))
    }

    // 404
    fun notFound(routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(404)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encode(ResponseMessageLibrary.RESOURCE_NOT_FOUND_404))
    }

    // 500
    fun error(t: Throwable, routingContext: RoutingContext) {
        routingContext
                .response()
                .setStatusCode(500)
                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .end(Json.encode(ResponseMessageLibrary.customResponseMessage(t.javaClass.name + ": " + t.message)))
    }

    //
    //    public static void badRequest(final RoutingContext routingContext) {
    //        routingContext
    //                .response()
    //                .setStatusCode(400)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
    //                .end(Json.encodePrettily(ResponseMessageLibrary.RESOURCE_BAD_REQUEST_400));
    //    }
    //
    //    public static void error(final Throwable t, final RoutingContext routingContext) {
    //        routingContext
    //                .response()
    //                .setStatusCode(500)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
    //                .end(Json.encodePrettily(new ResponseMessageLibrary.ResponseStatus(t.getClass().getName() + ": " + t.getMessage())));
    //    }
    //
    //    public static void unauthorized(final RoutingContext routingContext) {
    //        routingContext
    //                .response()
    //                .setStatusCode(401)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
    //                .end(Json.encode(ResponseMessageLibrary.RESOURCE_UNAUTHORIZED_401));
    //    }
    //
    //    public static <T> void json(final RoutingContext routingContext, final T contract) {
    //        routingContext
    //                .response()
    //                .setStatusCode(200)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
    //                .end(Json.encode(contract));
    //    }
    //
    //    public static <T> void byteBuffer(final RoutingContext routingContext, final ByteBuffer byteBuffer) {
    //        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
    //        readOnlyBuffer.position(0);
    //
    //        ByteBuf buf = Unpooled.wrappedBuffer(readOnlyBuffer);
    //
    //        routingContext
    //                .response()
    //                .setStatusCode(200)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_OCTET_STREAM)
    //                .putHeader("Content-Disposition", "attachment; filename=\"picture.png\"")
    //                .end(Buffer.buffer(buf));
    //    }
    //
    //    public static <T> void byteBuf(final RoutingContext routingContext, final ByteBuf byteBuf) {
    //        routingContext
    //                .response()
    //                .setStatusCode(200)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_OCTET_STREAM)
    //                .putHeader("Content-Disposition", "attachment; filename=\"picture.png\"")
    //                .end(Buffer.buffer(byteBuf));
    //    }
    //
    //    public static <T> void bytes(final RoutingContext routingContext, final byte[] bytes) {
    //        routingContext
    //                .response()
    //                .setStatusCode(200)
    //                .putHeader(CONTENT_TYPE_HEADER, APPLICATION_OCTET_STREAM)
    //                .end(Buffer.buffer(bytes));
    //    }
    //
    //    public static void ok(final RoutingContext routingContext, final String text) {
    //        routingContext
    //                .response()
    //                .setStatusCode(200)
    //                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
    //                .end(text);
    //    }
    //
    //    public static void accepted(final RoutingContext routingContext, final String text) {
    //        routingContext
    //                .response()
    //                .setStatusCode(202)
    //                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
    //                .end(text);
    //    }
    //
    //    public static void created(final RoutingContext routingContext) {
    //        routingContext
    //                .response()
    //                .setStatusCode(201)
    //                .putHeader(CONTENT_TYPE_HEADER, TEXT_PLAIN)
    //                .end();
    //    }
    //
    //    public static void deleted(final RoutingContext routingContext) {
    //        routingContext
    //                .response()
    //                .setStatusCode(204)
    //                .end();
    //    }
}

