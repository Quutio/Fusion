package io.quut.fusion.velocity.connection

import com.velocitypowered.api.proxy.Player
import com.velocitypowered.api.proxy.messages.ChannelIdentifier
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier
import com.velocitypowered.api.proxy.server.RegisteredServer
import io.quut.fusion.api.connection.IConnectionRequest
import io.quut.fusion.api.connection.IConnectionRequestParameters
import io.quut.fusion.api.connection.IConnectionRequestStrategy
import io.quut.fusion.api.connection.IConnectionRequestTemplate
import io.quut.fusion.velocity.listeners.ServerLoginPluginListener
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import java.util.function.Function

internal class VelocityConnectionRequestTemplate(
	private val listener: ServerLoginPluginListener,
	private val server: RegisteredServer,
	private val parameters: IConnectionRequestParameters) : IConnectionRequestTemplate
{
	override fun connect(target: Audience, parameters: IConnectionRequestParameters)
	{
		target.forEachAudience()
		{ audience ->
			if (audience !is Player)
			{
				return@forEachAudience
			}

			val map: MutableMap<ChannelIdentifier, Function<ByteArray, ByteArray?>> = hashMapOf()

			val request = object : IConnectionRequest
			{
				override val strategy: IConnectionRequestStrategy
					get() = Strategy.INSTANCE

				override fun loginPluginMessageHandler(channel: Key, handler: Function<ByteArray, ByteArray?>)
				{
					map[MinecraftChannelIdentifier.from(channel)] = handler
				}
			}

			this.parameters.supply(request)
			parameters.supply(request)

			this.listener.addConnection(audience, this.server, map)

			audience.createConnectionRequest(this.server).connectWithIndication()
		}
	}

	private class Strategy : IConnectionRequestStrategy
	{
		override val name: String
			get() = "proxy"

		companion object
		{
			val INSTANCE: Strategy = Strategy()
		}
	}
}
