package io.quut.fusion.api

/**
 * Represents the Fusion API.
 */
interface IFusionAPI
{
	/**
	 * The Fusion instance associated with this API.
	 */
	val fusion: IFusion

	companion object
	{
		private var instance: IFusionAPI? = null

		/**
		 * Gets the current Fusion API instance.
		 *
		 * @throws IllegalStateException If Fusion is not initialized.
		 * @return The current Fusion API instance.
		 */
		@JvmStatic
		fun get(): IFusionAPI = this.instance ?: throw IllegalStateException("Fusion is not initialized")

		/**
		 * Registers a Fusion API instance.
		 *
		 * Note: This is internal method.
		 */
		fun register(instance: IFusionAPI)
		{
			if (this.instance != null)
			{
				throw IllegalStateException("Already registered")
			}

			this.instance = instance
		}

		/**
		 * Unregisters the current Fusion API instance.
		 *
		 * Note: This is internal method.
		 */
		fun unregister(instance: IFusionAPI)
		{
			if (this.instance != instance)
			{
				throw IllegalArgumentException("Mismatched instance")
			}

			this.instance = null
		}
	}
}
