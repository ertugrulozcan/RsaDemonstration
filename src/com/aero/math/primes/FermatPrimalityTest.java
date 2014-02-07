package com.aero.math.primes;

import java.math.BigInteger;
import java.util.Random;

public class FermatPrimalityTest
{
    private final static Random rand = new Random();

    public static boolean isPrime(BigInteger n, int maxIterations)
    {
        if (n.equals(BigInteger.ONE))
            return false;

        for (int i = 0; i < maxIterations; i++)
        {
            BigInteger a = getRandomFermatBase(n);
            a = a.modPow(n.subtract(BigInteger.ONE), n);

            if (!a.equals(BigInteger.ONE))
                return false;
        }

        return true;
    }

    public static boolean isPrime(int n, int maxIterations)
    {
        if (n == 1)
            return false;

        for (int i = 0; i < maxIterations; i++)
        {
            int a = getRandomFermatBase(n);

            if (Math.pow(a, n-1) % n != 1)
                return false;
        }

        return true;
    }

    private static BigInteger getRandomFermatBase(BigInteger n)
    {
        while (true)
        {
            final BigInteger a = new BigInteger (n.bitLength(), rand);
            // 1 <= a < n
            if (BigInteger.ONE.compareTo(a) <= 0 && a.compareTo(n) < 0)
            {
                return a;
            }
        }
    }
    
    private static int getRandomFermatBase(int n)
    {
        while (true)
        {
            final int a = rand.nextInt();
            // 1 <= a < n
            if (1 <= a && a < n)
            {
                return a;
            }
        }
    }
}