package io.quut.fusion.api.connection

import net.kyori.adventure.key.Key
import java.util.function.Function

/**
 * Represents a connection request that can be used to configure
 * the connection process.
 */
interface IConnectionRequest
{
	/**
	 * The strategy used for this connection request.
	 */
	val strategy: IConnectionRequestStrategy

	/**
	 * Registers a handler for login plugin messages on the specified channel.
	 *
	 * Note that not all connection methods support login plugin messages.
	 * If the connection method does not support them, this method will have no effect.
	 *
	 * @param channel The channel to listen for messages.
	 * @param handler The handler function to process incoming messages.
	 */
	fun loginPluginMessageHandler(channel: Key, handler: Function<ByteArray, ByteArray?>)
}
