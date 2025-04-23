package io.github.lamalditag

import club.arson.impulse.api.config.ServerConfig
import club.arson.impulse.api.server.Broker
import club.arson.impulse.api.server.Status
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import java.net.InetSocketAddress
import java.net.SocketException

/**
 * This broker is designed to send api requests to a pufferpanel controller instance
 *
 * @property ServerConfig Server config to create a pufferpanel broker for
 * @property logger Logger ref for log messages
 */
class PufferControllerBroker(serverConfig: ServerConfig, private val logger: Logger? = null) : Broker {
	private var pufferConfig: PufferControllerBrokerConfig
	private val client: HttpClient

	/**
	 * Creates a new instance and sets up the broker based on the config
	 */
	init {
		pufferConfig = serverConfig.config as PufferControllerBrokerConfig
		client = HttpClient(CIO) {
			engine {
			}
			install(ContentNegotiation) {
				json(Json {
					prettyPrint = true
					isLenient = true
					ignoreUnknownKeys = true
				})
			}
		}
	}

	override fun address(): Result<InetSocketAddress> {
		if (pufferConfig.address == null) {
			return Result.failure(IllegalArgumentException("No address specified in config"))
		}
		val port = pufferConfig.address?.split(":")?.getOrNull(1)?.toIntOrNull() ?: 25565
		return runCatching { InetSocketAddress(pufferConfig.address, port) }
	}

	/**
	 * Reconcile any changes to the config with what impulse will try to do
	 *
	 * @param config Server config to reconcile with
	 * @return Result of a success or failure
	 */
	override fun reconcile(config: ServerConfig): Result<Runnable?> {
		if (config.type != "puffer") {
			logger?.error("config type error")
			return Result.failure(IllegalArgumentException("Expected PufferControllerConfig and got ${config.type}"))
		}

		val newConfig = config.config as PufferControllerBrokerConfig
		if (newConfig != pufferConfig) { // if the config changed
			pufferConfig = newConfig
			return Result.success(null)
		} else { // if the config didn't change
			pufferConfig = newConfig
			return Result.success(null)
		}
	}

	/**
	 * Returns the status of the server
	 *
	 * @return Status type representing the server state
	 */
	override fun getStatus(): Status {
		val response = apiRequest(RequestType.STATUS)
		if (response.status != "ok") {
			logger?.error("Unable to send status request! Error: ${response.info ?: response.errorData}")
			return Status.UNKNOWN
		}
		if (response.data?.running == true) {
			return Status.RUNNING
		}
		return Status.STOPPED
	}

	/**
	 * @return true if the server is running
	 */
	override fun isRunning(): Boolean {
		return getStatus() == Status.RUNNING
	}

	/**
	 * Attempts to remove the server
	 *
	 * requires CONFIG permission
	 *
	 * @return success if the server was killed, else an error
	 */
	override fun removeServer(): Result<Unit> {
		stopServer()
		val response = apiRequest(RequestType.DELETE)
		if (response.status == "ok") {
			return Result.success(Unit)
		}
		logger?.error("Unable to send delete request! Error: ${response.errorData}")
		return Result.failure(Throwable("ERROR! Unable to delete server: ${pufferConfig.serverID}, Error message: ${response.error}"))
	}


	/**
	 * Attempts to start the server
	 *
	 * requires COMMANDS permission
	 *
	 * @return success if the server was started, else an error
	 */
	override fun startServer(): Result<Unit> {
		val response = apiRequest(RequestType.START)
		if (response.status == "ok") {
			return Result.success(Unit)
		}
		logger?.error("Unable to send start request! Error: ${response.errorData}")
		return Result.failure(Throwable("ERROR! Unable to start server: ${pufferConfig.serverID}, Error message: ${response.error}"))
	}


	/**
	 * Attempts to stop the server
	 *
	 * requires COMMANDS permission
	 *
	 * @return success if the server was stopped, else an error
	 */
	override fun stopServer(): Result<Unit> {
		val response = apiRequest(RequestType.STOP)
		if (response.status == "ok") {
			return Result.success(Unit)
		}
		logger?.error("Unable to send stop request! Error: ${response.errorData}")
		return Result.failure(Throwable("ERROR! Unable to stop server: ${pufferConfig.serverID}, Error message: ${response.error}"))
	}

	/**
	 * Sends an api request to the pufferpanel controller api
	 *
	 * @param type type of the request to send to the server
	 * @return an ApiData object representing the received data
	 */
	private fun apiRequest(type: RequestType): ApiData = runBlocking {
		logger?.debug("Trying RequestType: {}", type)
		var response: ApiData
		try {
			response = client.request(pufferConfig.pufferApiAddress) {
				method = type.method
				url {
					appendPathSegments("daemon/server", pufferConfig.serverID, type.request)
				}
				headers {
					append(
						HttpHeaders.Authorization, "Bearer ${pufferConfig.token}"
					)
				}
			}.body()
		} catch (e: SocketException) { // handles if the connection fails because of an improper protocol
			if (e.message.equals("Connection reset")) {
				logger?.error("Unable to connect to the api! Check the protocol of the address!")
				response = ApiData(
					status = "error",
					errorData = "Connection reset",
				)
			} else {
				throw e
			}
		}

		logger?.debug("Valid json. Returning...")
		logger?.debug(response.toString())

		return@runBlocking response
	}
}