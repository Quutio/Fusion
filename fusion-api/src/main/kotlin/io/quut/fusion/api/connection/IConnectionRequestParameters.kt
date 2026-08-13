package io.quut.fusion.api.connection

/**
 * Represents a set of parameters that can be supplied to a connection request.
 */
fun interface IConnectionRequestParameters
{
	/**
	 * Supplies the parameters to the given connection request.
	 *
	 * @param request The connection request to supply parameters to.
	 */
	fun supply(request: IConnectionRequest)

	/**
	 * Combines this set of parameters with another set of parameters.
	 *
	 * @param other The other set of parameters to combine with.
	 * @return A new set of parameters that combines both sets.
	 */
	fun combine(other: IConnectionRequestParameters): IConnectionRequestParameters
	{
		if (this == IConnectionRequestParameters.EMPTY)
		{
			return other
		}
		else if (other == IConnectionRequestParameters.EMPTY)
		{
			return this
		}

		return IConnectionRequestParameters()
		{ r ->
			this.supply(r)
			other.supply(r)
		}
	}

	companion object
	{
		/**
		 * An empty set of connection request parameters that does nothing when supplied.
		 * This can be used as a default or placeholder when no parameters are needed.
		 */
		@JvmStatic
		val EMPTY: IConnectionRequestParameters = { }
	}
}
