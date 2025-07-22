package com.example.routing.person

import com.example.models.Person
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.put
import io.ktor.server.resources.patch
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configurePersonRouting() {
    val people = mutableListOf<Person>()
    var counter = 0

    routing {
        post("person") {
            val data = try {
                call.receive<PersonBody>()
            } catch (_: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                return@post
            }

            if (data.name.isBlank() || data.age <= 0 || data.money < 0) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid parameters")
                return@post
            }

            people.add(Person(counter++, data.name, data.age, data.money))

            call.respond(HttpStatusCode.Created, people.last())
        }

        post("people") {
            val peopleList = try {
                call.receive<List<PersonBody>>()
            } catch (_: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                return@post
            }

            val invalid = peopleList.any { it.name.isBlank() || it.age <= 0 || it.money < 0 }
            if (invalid) {
                call.respond(HttpStatusCode.BadRequest, "Some items have invalid fields")
                return@post
            }

            peopleList.forEach {
                people.add(Person(counter++, it.name, it.age, it.money))
            }

            call.respond(HttpStatusCode.Created, "People added")
        }

        get<GetPeople> { request ->
            val result = when(request.sort?.lowercase()) {
                "salary" -> people.sortedByDescending { it.money }
                "name" -> people.sortedBy { it.name }
                "age" -> people.sortedBy { it.age }
                else -> people
            }
            call.respond(result)
        }

        get<GetPersonById> { request ->
            call.respond(people.find { it.id == request.id } ?: HttpStatusCode.NotFound)
        }

        delete<DeletePersonById> { request ->
            val removed = people.removeIf { it.id == request.id }

            if (removed) {
                call.respondText("Person with id ${request.id} deleted")
            } else {
                call.respond(HttpStatusCode.NotFound, "Person not found")
            }
        }

        put<UpdatePersonSalary> { request ->
            val body = try {
                call.receive<UpdateSalaryBody>()
            } catch (_: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                return@put
            }

            val person = people.find { it.id == request.id }
            if (person == null) {
                call.respond(HttpStatusCode.NotFound, "Person not found")
                return@put
            }

            val updated = person.copy(money = body.newSalary)
            people[people.indexOf(person)] = updated

            call.respond(HttpStatusCode.OK, updated)
        }

        patch<UpdatePerson> { request ->
            val body = try {
                call.receive<PersonPatchBody>()
            } catch (_: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid JSON format")
                return@patch
            }

            val person = people.find { it.id == request.id }
            if (person == null) {
                call.respond(HttpStatusCode.NotFound, "Person not found")
                return@patch
            }

            val updated = person.copy(
                name = body.name ?: person.name,
                age = body.age ?: person.age,
                money = body.money ?: person.money
            )

            people[people.indexOf(person)] = updated

            call.respond(HttpStatusCode.OK, updated)
        }
    }
}