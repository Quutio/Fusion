package io.quut.fusion.velocity.api

import com.velocitypowered.api.proxy.server.RegisteredServer
import io.quut.fusion.api.IFusion
import io.quut.fusion.api.connection.IConnectionRequestParameters
import io.quut.fusion.api.connection.IConnectionRequestTemplate

/**
 * Represents the entrypoint of Fusion for Velocity.
 */
interface IVelocityFusion : IFusion
{
	/**
	 * Creates a connection request template for the specified server.
	 *
	 * @param server The server to create the connection request template for.
	 * @return The created connection request template.
	 */
	fun connectionRequestTemplate(server: RegisteredServer): IConnectionRequestTemplate =
		this.connectionRequestTemplate(server, IConnectionRequestParameters.EMPTY)

	/**
	 * Creates a connection request template for the specified server
	 * with the provided connection request parameters.
	 *
	 * @param server The server to create the connection request template for.
	 * @param parameters The connection request parameters to use for the template.
	 * @return The created connection request template.
	 */
	fun connectionRequestTemplate(server: RegisteredServer, parameters: IConnectionRequestParameters): IConnectionRequestTemplate
}
