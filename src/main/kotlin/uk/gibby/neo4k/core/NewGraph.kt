package uk.gibby.neo4k.core

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

class NewGraph(val name: String, val host: String, val username: String, val password: String) {
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json()
        }
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(this@NewGraph.username, this@NewGraph.password)
                }
                realm = "Access to the '/' path"
            }
        }
    }

}