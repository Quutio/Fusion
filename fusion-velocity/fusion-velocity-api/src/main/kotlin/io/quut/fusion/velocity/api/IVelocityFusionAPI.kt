package io.quut.fusion.velocity.api

import io.quut.fusion.api.IFusionAPI

/**
 * Represents the Fusion API for Velocity.
 */
interface IVelocityFusionAPI : IFusionAPI
{
	/**
	 * The Fusion instance for Velocity associated with this API.
	 */
	override val fusion: IVelocityFusion

	companion object
	{
		/**
		 * Gets the current Fusion API instance for Velocity.
		 *
		 * @throws IllegalStateException If Fusion is not initialized or the platform is invalid.
		 * @return The current Fusion API instance for Velocity.
		 */
		@JvmStatic
		fun get(): IVelocityFusionAPI = IFusionAPI.get() as? IVelocityFusionAPI ?: throw IllegalStateException("Invalid platform")
	}
}
