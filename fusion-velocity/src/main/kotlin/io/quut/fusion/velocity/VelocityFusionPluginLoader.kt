package io.quut.fusion.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import io.quut.fusion.api.IFusionAPI
import io.quut.fusion.velocity.api.IVelocityFusion
import io.quut.fusion.velocity.api.IVelocityFusionAPI
import io.quut.fusion.velocity.listeners.ServerLoginPluginListener

@Plugin(id = "fusion", name = "Fusion", version = "0.1-SNAPSHOT", url = "https://quut.io", authors = ["Joni Aromaa (isokissa3)"])
class VelocityFusionPluginLoader @Inject internal constructor(private val eventManager: EventManager)
{
	private val listener: ServerLoginPluginListener = ServerLoginPluginListener()
	private val plugin: VelocityFusionPlugin = VelocityFusionPlugin(this.listener)
	private val api: API = API()

	@Subscribe
	fun onProxyInitialize(event: ProxyInitializeEvent)
	{
		this.eventManager.register(this, this.listener)

		IFusionAPI.register(this.api)
	}

	@Subscribe
	fun onProxyShutdown(event: ProxyShutdownEvent)
	{
		IFusionAPI.unregister(this.api)
	}

	private inner class API : IVelocityFusionAPI
	{
		override val fusion: IVelocityFusion
			get() = this@VelocityFusionPluginLoader.plugin
	}
}
