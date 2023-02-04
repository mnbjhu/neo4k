package util

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

object TestAuth {
    private val config = try{ ConfigurationProperties.fromResource("local.properties") } catch (e: Exception) { null }
    private val server_user = Key("server.user", stringType)
    private val server_pass = Key("server.pass", stringType)
    val user = try{ config?.get(server_user)!! } catch (e: Exception) { "neo4j" }
    val password = try{ config?.get(server_pass)!! } catch (e: Exception) { "myPassword123" }
}