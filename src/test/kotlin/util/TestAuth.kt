package util

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.stringType

object TestAuth {
    private val config = try{ ConfigurationProperties.fromResource("local.properties") } catch (e: Exception) { null }
    private val server_port = Key("server.port", intType)
    private val server_host = Key("server.host", stringType)
    private val server_pass = Key("server.pass", stringType)
    val host = try{ config?.get(server_host)!! } catch (e: Exception) { "bolt://localhost" }
    val port = try{ config?.get(server_port)!! } catch (e: Exception) { 6379 }
    val password = try{ config?.get(server_pass) } catch (e: Exception) { null }
}