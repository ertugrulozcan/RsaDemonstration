package com.aero.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aero.rsa.RsaKey.*;
import com.aero.rsa.User;

public abstract class Json
{
	public static String SerializeUserList(List<User> userList)
	{
		// Serialization i�lemi:
		// Nesne >>> String D�n���m�
		
		JSONArray array = new JSONArray();
		JSONObject arrayObject = new JSONObject();
		
		try
		{
			for(int i = 0; i < userList.size(); i++)
			{
				JSONObject object = new JSONObject();
				object.put("phoneNumber", userList.get(i).phoneNumber);
				object.put("email", userList.get(i).emailAdress);
				object.put("publicKeyN", userList.get(i).publicKey.N().toString());
				object.put("publicKeyE", userList.get(i).publicKey.E().toString());
				
				if(userList.get(i).privateKey != null)
				{
					object.put("privateKeyN", userList.get(i).privateKey.N().toString());
					object.put("privateKeyD", userList.get(i).privateKey.D().toString());
				}
				
				array.put(object);
			}
			
			// Serializator
			arrayObject.put("Users", array);
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return arrayObject.toString();
	}
	
	public static List<User> DeserializeUserList(String jsonString)
	{
		// Deserialization i�lemi:
		// String >>> Nesne D�n���m�
		
		// Json nesnesi ve d�n��t�rme i�lemi
		List<User> userList = new ArrayList<User>();
		try
		{
			JSONObject json = new JSONObject(jsonString);
			JSONArray jArray = json.getJSONArray("Users");
			for(int i = 0; i < jArray.length(); i++)
			{
				String phoneNumber, email, publicKeyN, publicKeyE, privateKeyN, privateKeyD;
				phoneNumber = jArray.getJSONObject(i).getString("phoneNumber");
				email = jArray.getJSONObject(i).getString("email");
				publicKeyN = jArray.getJSONObject(i).getString("publicKeyN");
				publicKeyE = jArray.getJSONObject(i).getString("publicKeyE");
				privateKeyN = jArray.getJSONObject(i).getString("privateKeyN");
				privateKeyD = jArray.getJSONObject(i).getString("privateKeyD");
				
				if(privateKeyN == null || privateKeyN == "" || privateKeyD == null || privateKeyD == "")
					userList.add(new User(phoneNumber, email, new PublicKey(new BigInteger(publicKeyN), new BigInteger(publicKeyE))));
				else
					userList.add(new User(phoneNumber, email, new PublicKey(new BigInteger(publicKeyN), new BigInteger(publicKeyE)), new PrivateKey(new BigInteger(privateKeyN), new BigInteger(privateKeyD))));
			}
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userList;
	}
	
	public static String ReadFromFile(String path)
	{
		String text = "";
		File file = new File(path);
		BufferedReader br = null;
		
		try
		{
			String line;
			
			br = new BufferedReader(new FileReader(file));
			
			while ((line = br.readLine()) != null)
			{
				text += line;
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (br != null)
					br.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		
		return text;
	}
	
	public static void WriteToFileFile(String content, String path, boolean appendMode)
	{
		BufferedWriter bW = null;
		try
        {
            File file = new File(path);

            bW = new BufferedWriter(new FileWriter(file, appendMode));
            bW.write(content);
            bW.newLine();
            bW.flush();
            
        }
        catch(IOException e)
        {
             e.printStackTrace();
        }
		finally
		{
			try
			{
				bW.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
