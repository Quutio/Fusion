package io.quut.fusion.velocity.listeners

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import com.velocitypowered.api.event.player.KickedFromServerEvent
import com.velocitypowered.api.event.player.ServerConnectedEvent
import com.velocitypowered.api.event.player.ServerLoginPluginMessageEvent
import com.velocitypowered.api.event.player.ServerLoginPluginMessageEvent.ResponseResult
import com.velocitypowered.api.event.player.ServerPreConnectEvent
import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.function.Function

internal class ServerLoginPluginListener
{
	private val pendingConnections: ConcurrentMap<UUID, PendingConnection> = ConcurrentHashMap()
	private val inFlightConnections: ConcurrentMap<UUID, PendingConnection> = ConcurrentHashMap()

	internal fun addConnection(player: Player, server: RegisteredServer, loginPluginMessageHandlers: Map<ChannelIdentifier, Function<ByteArray, ByteArray?>>)
	{
		this.pendingConnections[player.uniqueId] = PendingConnection(server, loginPluginMessageHandlers)
	}

	@Subscribe(priority = Short.MIN_VALUE)
	fun onServerPreConnect(event: ServerPreConnectEvent)
	{
		val connection: PendingConnection = this.pendingConnections[event.player.uniqueId] ?: return
		if (connection.server != event.originalServer
			|| !this.pendingConnections.remove(event.player.uniqueId, connection)
			|| !event.result.isAllowed)
		{
			return
		}

		this.inFlightConnections[event.player.uniqueId] = connection
	}

	@Subscribe
	fun onServerLoginPluginMessage(event: ServerLoginPluginMessageEvent)
	{
		val connection: PendingConnection = this.inFlightConnections[event.connection.player.uniqueId] ?: return
		if (connection.server != event.connection.server)
		{
			return
		}

		val handler: Function<ByteArray, ByteArray?> = connection.loginPluginMessageHandlers[event.identifier] ?: return
		val response = handler.apply(event.contents) ?: return event.setResult(ResponseResult.unknown())

		event.result = ResponseResult.reply(response)
	}

	@Subscribe
	fun onKickedFromServer(event: KickedFromServerEvent)
	{
		val connection: PendingConnection = this.inFlightConnections[event.player.uniqueId] ?: return
		if (connection.server != event.server)
		{
			return
		}

		this.inFlightConnections.remove(event.player.uniqueId, connection)
	}

	@Subscribe
	fun onServerConnected(event: ServerConnectedEvent)
	{
		val connection: PendingConnection = this.inFlightConnections[event.player.uniqueId] ?: return
		if (connection.server != event.server)
		{
			return
		}

		this.inFlightConnections.remove(event.player.uniqueId, connection)
	}

	@Subscribe
	fun onDisconnect(event: DisconnectEvent)
	{
		this.pendingConnections.remove(event.player.uniqueId)
		this.inFlightConnections.remove(event.player.uniqueId)
	}

	private class PendingConnection(val server: RegisteredServer, val loginPluginMessageHandlers: Map<ChannelIdentifier, Function<ByteArray, ByteArray?>>)
}
