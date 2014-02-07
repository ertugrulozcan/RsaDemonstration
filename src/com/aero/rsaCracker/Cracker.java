package com.aero.rsaCracker;

import java.math.BigInteger;
import com.aero.rsa.RSA;


public abstract class Cracker
{
	public static void main(String[] args)
	{
		
	}
	
	//
	// Þifre kýrýcý denemesi
	//
	public static RSA Crack(com.aero.rsa.RsaKey.PublicKey publicKey)
	{
		// Sonuç deðeri
		RSA result = null;
		
		BigInteger probableP, probableQ;
		
		BigInteger squareRootN = BigIntegerSquareRoot.SquareRootFloor(publicKey.N());
		
		if(squareRootN.isProbablePrime(1))
		{
			probableP = squareRootN;
			probableQ = publicKey.N().divide(squareRootN);
			
			if(probableQ.isProbablePrime(1))
			{
				// Bulundu
				System.out.println("p = " + probableP);
				System.out.println("q = " + probableQ);
			}
		}
		
		return result;
	}
}
