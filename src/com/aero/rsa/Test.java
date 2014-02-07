package com.aero.rsa;

import java.math.BigInteger;

import com.aero.rsa.RsaKey.*;

public class Test
{
	public static void main(String[] args)
	{
		new Test();
	}
	
	public Test()
	{
		RSA rsa = new RSA();
		PublicKey publicKey = rsa.getPublicKey();
		PrivateKey privateKey = rsa.getPrivateKey();
		
		String message = "ERTUGRUL";
		
		System.out.println("Mesaj : " + message);
		System.out.println("Kullanýlan anahtar uzunlugu : " + rsa.GetKeySize() + " bit");
		System.out.println("Anahtar üretim zamaný : " + rsa.ProgressTime() * Math.pow(10, -6) + " ms.");
		
		// Sifreleme islemi
		Encoder encoder = new Encoder(publicKey);
		String crypto = encoder.Encrypt(message);
		
		System.out.println("Þifre : " + crypto);
		
		System.out.print("Þifre karakter uzunluðu : ");
		System.out.println(crypto.length());
		System.out.print("Þifre bit uzunluðu : ");
		System.out.println(new BigInteger(crypto).toString(2).length());
		
		// Sifre cozme islemi
		Decoder decoder = new Decoder(privateKey);
		String message2 = decoder.Decrypt(crypto);
		
		System.out.println("Çözülen mesaj : " + message2);
	}
	
}
