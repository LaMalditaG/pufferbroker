package io.github.lamalditag

import club.arson.impulse.api.config.ServerConfig
import club.arson.impulse.api.server.Broker
import club.arson.impulse.api.server.BrokerFactory
import org.slf4j.Logger

@Suppress("unused")
class PufferControllerBrokerFactory : BrokerFactory {

	/**
	 * This broker is designed to call the pufferpanel controller api to control servers
	 */

	override val provides: List<String> = listOf("puffer")


	/**
	 * Create a pufferpanel broker from a ServerConfig Object
	 *
	 * Checks to make sure the ServerConfig is a valid pufferpanel config
	 * @param config Server config to create a pufferpanel broker for
	 * @param logger Logger ref for log messages
	 * @return A result containing a pufferpanel broker if we were able to make on for the server, else an error
	 */

	override fun createFromConfig(config: ServerConfig, logger: Logger?): Result<Broker> {
		return when (config.config) {
			is PufferControllerBrokerConfig -> Result.success(PufferControllerBroker(config, logger))
			else -> Result.failure(IllegalArgumentException("Invalid configuration for puffer broker"))
		}
	}

}