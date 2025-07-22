package com.example.routing.person

import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/person")
object Person

@Serializable
@Resource("all")
data class GetPeople(
    val parent: Person = Person,
    val sort: String? = null
)

@Serializable
@Resource("{id}")
data class GetPersonById(
    val parent: Person = Person,
    val id: Int
)

@Serializable
data class PersonBody(
    val name: String = "",
    val age: Int = -1,
    val money: Int = -1
)

@Serializable
@Resource("{id}")
data class DeletePersonById(
    val parent: Person = Person,
    val id: Int
)

@Serializable
data class UpdateSalaryBody(val newSalary: Int)

@Serializable
@Resource("{id}/salary")
data class UpdatePersonSalary(
    val parent: Person = Person,
    val id: Int
)

@Serializable
data class PersonPatchBody(
    val name: String? = null,
    val age: Int? = null,
    val money: Int? = null
)

@Serializable
@Resource("{id}")
data class UpdatePerson(
    val parent: Person = Person,
    val id: Int
)