package io.quut.fusion.api.connection

import net.kyori.adventure.audience.Audience

/**
 * Represents a template for creating connection requests.
 */
fun interface IConnectionRequestTemplate
{
	/**
	 * Connects the specified target audience using this template.
	 *
	 * @param target The target audience to connect.
	 */
	fun connect(target: Audience) = this.connect(target, IConnectionRequestParameters.EMPTY)

	/**
	 * Connects the specified target audience using this template
	 * and the provided connection request parameters.
	 *
	 * @param target The target audience to connect.
	 * @param parameters The connection request parameters to use for the connection.
	 */
	fun connect(target: Audience, parameters: IConnectionRequestParameters)
}
