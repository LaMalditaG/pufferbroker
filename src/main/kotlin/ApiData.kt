package io.github.lamalditag

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Data class to hold the json response from the pufferpanel controller api
 */


// @Serializable
// data class TokenResponse(
// 	@SerialName("status") var status: String,
// 	@SerialName("data") var data: TokenData? = TokenData(),
// 	@SerialName("error") var error: String? = null,
// 	@SerialName("error_data") var errorData: String? = null,
// 	@SerialName("info") var info: String? = null,
// )

@Serializable
data class ApiResponse(
	@SerialName("status") var status: String? = null,
	@SerialName("running") var running: Boolean? = null,
	@SerialName("info") var info: String? = null,
	@SerialName("error") var error: String? = null,
	@SerialName("error_data") var errorData: String? = null,
)


@Serializable
data class ApiData(
	@SerialName("status") var status: String,
	@SerialName("running") var running: Boolean? = false,
	@SerialName("error") var error: String? = null,
	@SerialName("error_data") var errorData: String? = null,
	@SerialName("info") var info: String? = null,
)

@Serializable
data class TokenData(
	@SerialName("access_token") var accessToken: String? = null,
	@SerialName("token_type") var tokenType: String? = null,
	@SerialName("scope") var scope: String? = null,
	@SerialName("expires_in") var expiresIn: Int? = null,
)

@Serializable
data class ServerData(
	@SerialName("stats_id") var statsId: Int? = null,
	@SerialName("created") var created: String? = null,
	@SerialName("server_id") var serverId: ServerId? = ServerId(),
	@SerialName("started") var started: String? = null,
	@SerialName("running") var running: Boolean? = null,
	@SerialName("cpu") var cpu: Double? = null,
	@SerialName("mem") var mem: String? = null,
	@SerialName("mem_percent") var memPercent: Double? = null,
	@SerialName("world_name") var worldName: String? = null,
	@SerialName("world_size") var worldSize: String? = null,
	@SerialName("server_port") var serverPort: Int? = null,
	@SerialName("int_ping_results") var intPingResults: String? = null,
	@SerialName("online") var online: Int? = null,
	@SerialName("max") var max: Int? = null,
	@SerialName("players") var players: String? = null,
	@SerialName("desc") var desc: String? = null,
	@SerialName("icon") var icon: String? = null,
	@SerialName("version") var version: String? = null,
	@SerialName("updating") var updating: Boolean? = null,
	@SerialName("waiting_start") var waitingStart: Boolean? = null,
	@SerialName("first_run") var firstRun: Boolean? = null,
	@SerialName("crashed") var crashed: Boolean? = null,
	@SerialName("importing") var importing: Boolean? = null,

)

@Serializable
data class ServerId(
	@SerialName("server_id") var serverId: String? = null,
	@SerialName("created") var created: String? = null,
	@SerialName("server_name") var serverName: String? = null,
	@SerialName("path") var path: String? = null,
	@SerialName("executable") var executable: String? = null,
	@SerialName("log_path") var logPath: String? = null,
	@SerialName("execution_command") var executionCommand: String? = null,
	@SerialName("auto_start") var autoStart: Boolean? = null,
	@SerialName("auto_start_delay") var autoStartDelay: Int? = null,
	@SerialName("crash_detection") var crashDetection: Boolean? = null,
	@SerialName("stop_command") var stopCommand: String? = null,
	@SerialName("executable_update_url") var executableUpdateUrl: String? = null,
	@SerialName("server_ip") var serverIp: String? = null,
	@SerialName("server_port") var serverPort: Int? = null,
	@SerialName("logs_delete_after") var logsDeleteAfter: Int? = null,
	@SerialName("type") var type: String? = null,
	@SerialName("show_status") var showStatus: Boolean? = null,
	@SerialName("created_by") var createdBy: Int? = null,
	@SerialName("shutdown_timeout") var shutdownTimeout: Int? = null,
	@SerialName("ignored_exits") var ignoredExits: String? = null,
	@SerialName("count_players") var countPlayers: Boolean? = null

)

@Serializable
data class ServerStatus(
	@SerialName("running") var running: Boolean? = null
)