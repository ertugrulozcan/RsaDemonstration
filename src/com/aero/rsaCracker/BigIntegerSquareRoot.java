package com.aero.rsaCracker;

import java.math.BigInteger;

public class BigIntegerSquareRoot
{
	public static BigInteger SquareRootFloor(BigInteger x) throws IllegalArgumentException
	{
		if (x.compareTo(BigInteger.ZERO) < 0)
		{
			throw new IllegalArgumentException("Negative argument.");
		}
		
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (x == BigInteger.ZERO || x == BigInteger.ONE)
		{
			return x;
		}
		
		BigInteger two = BigInteger.valueOf(2L);
		BigInteger y;
		
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; y = ((x.divide(y)).add(y)).divide(two))
			;
		
		return y;
	}
	
	public static BigInteger SquareRootCeil(BigInteger x) throws IllegalArgumentException
	{
		if (x.compareTo(BigInteger.ZERO) < 0)
		{
			throw new IllegalArgumentException("Negative argument.");
		}
		
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (x == BigInteger.ZERO || x == BigInteger.ONE)
		{
			return x;
		}
		
		BigInteger two = BigInteger.valueOf(2L);
		BigInteger y;
		
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; y = ((x.divide(y)).add(y)).divide(two))
			;
		
		if (x.compareTo(y.multiply(y)) == 0)
		{
			return y;
		}
		else
		{
			return y.add(BigInteger.ONE);
		}
	}
}
