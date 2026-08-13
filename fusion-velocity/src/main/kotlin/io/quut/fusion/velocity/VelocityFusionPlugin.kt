package io.quut.fusion.velocity

import com.velocitypowered.api.proxy.server.RegisteredServer
import io.quut.fusion.api.IFusionAPI
import io.quut.fusion.api.connection.IConnectionRequestParameters
import io.quut.fusion.api.connection.IConnectionRequestTemplate
import io.quut.fusion.velocity.api.IVelocityFusion
import io.quut.fusion.velocity.api.IVelocityFusionAPI
import io.quut.fusion.velocity.connection.VelocityConnectionRequestTemplate
import io.quut.fusion.velocity.listeners.ServerLoginPluginListener

internal class VelocityFusionPlugin(private val listener: ServerLoginPluginListener) : IVelocityFusion
{
	override fun connectionRequestTemplate(server: RegisteredServer, parameters: IConnectionRequestParameters): IConnectionRequestTemplate =
		VelocityConnectionRequestTemplate(this.listener, server, parameters)
}
