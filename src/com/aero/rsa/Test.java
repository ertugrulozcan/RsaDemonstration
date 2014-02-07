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
		System.out.println("Kullan�lan anahtar uzunlugu : " + rsa.GetKeySize() + " bit");
		System.out.println("Anahtar �retim zaman� : " + rsa.ProgressTime() * Math.pow(10, -6) + " ms.");
		
		// Sifreleme islemi
		Encoder encoder = new Encoder(publicKey);
		String crypto = encoder.Encrypt(message);
		
		System.out.println("�ifre : " + crypto);
		
		System.out.print("�ifre karakter uzunlu�u : ");
		System.out.println(crypto.length());
		System.out.print("�ifre bit uzunlu�u : ");
		System.out.println(new BigInteger(crypto).toString(2).length());
		
		// Sifre cozme islemi
		Decoder decoder = new Decoder(privateKey);
		String message2 = decoder.Decrypt(crypto);
		
		System.out.println("��z�len mesaj : " + message2);
	}
	
}
