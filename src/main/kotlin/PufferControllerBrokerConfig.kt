package io.github.lamalditag

import club.arson.impulse.api.config.BrokerConfig
import kotlinx.serialization.Serializable

@BrokerConfig("puffer")
@Serializable
data class PufferControllerBrokerConfig(
	var address: String? = null,
	var serverID: String,
	// var token: String,
	var clientID: String,
	var clientSecret: String,
	var pufferApiAddress: String = "http://localhost:8080",
	var insecureMode: Boolean = false
)