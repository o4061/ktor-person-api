package com.example.routing.test

import com.example.models.Blogs
import com.example.models.Hello
import com.example.models.Product
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.cio.writeChannel
import io.ktor.utils.io.copyAndClose
import io.ktor.utils.io.readRemaining
import io.ktor.utils.io.readText
import java.io.File

fun Application.configureTestRouting() {

    routing {

        authenticate("bearer-auth") {
            get<Hello> { hello ->
                val name = call.principal<UserIdPrincipal>()?.name
                call.respondText("Hello, ${hello.name} -> $name")
            }
        }

        get<Blogs> { blogs ->
            call.respondText("Blog: ${blogs.id} with q1: ${blogs.q1} and q2: ${blogs.q2}")
        }

        get(Regex(".+/test")) {
            call.respondText("Test api")
        }

        get(Regex("app/(?<apiVersion>v[1-3])/users")) {
            val version = call.parameters["apiVersion"]

            call.respondText("Version: $version")
        }

        post("greet") {
            val greet = call.receiveText()
            call.respondText("Hello, $greet!")
        }

        post("channel") {
            val channel = call.receiveChannel()
            val test = channel.readRemaining().readText()
            call.respondText(test)
        }

        post("upload") {
            val file = File("uploads/sample1.jpg").apply {
                parentFile.mkdirs()
            }

            /** «Κατέβασέ το όλο και βάλ’ το στη RAM». */
//            val byteArray = call.receive<ByteArray>()
//            file.writeBytes(byteArray)


            /** «Διάβασέ το κομμάτι-κομμάτι, μπλοκάροντας το thread». */
//            val stream = call.receiveStream()
//            file.outputStream().use { stream.copyTo(it, bufferSize = 4096) }

            /** «Διάβασέ το κομμάτι-κομμάτι, χωρίς να μπλοκάρεις· ιδανικό για coroutines». */
            val channel = call.receiveChannel()
            channel.copyAndClose(file.writeChannel())

            call.respondText("File uploaded")
        }

        post("product") {
            val product = call.receiveNullable<Product>() ?: return@post call.respond(HttpStatusCode.BadRequest)
            call.respond(product)
        }

        post("checkout") {
            val formData = call.receiveParameters()
            val productId = formData["productId"]
            val quantity = formData["quantity"]
            call.respondText("Checkout: $productId x $quantity")
        }

        post("test") {
            throw Exception("Database failed to initialize")
        }
    }
}
