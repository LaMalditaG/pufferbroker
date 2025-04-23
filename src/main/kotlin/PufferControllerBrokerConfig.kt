package io.github.lamalditag

import club.arson.impulse.api.config.BrokerConfig
import kotlinx.serialization.Serializable

@BrokerConfig("puffer")
@Serializable
data class PufferControllerBrokerConfig(
	var address: String? = null,
	var serverID: String,
	var token: String,
	var pufferApiAddress: String = "https://localhost:8080",
	var insecureMode: Boolean = false
)