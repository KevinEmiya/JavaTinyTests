package rmiSamples.tinyService;

import java.rmi.*;

public class RmiTinyClient 
{
	public RmiTinyRemote stub()
	{
		RmiTinyRemote stub = null;
		try
		{
			stub = (RmiTinyRemote)Naming.lookup("rmi://127.0.1.1/TinyServer");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return stub;
	}
	
	public static void main(String[] args)
	{
		try
		{
			RmiTinyClient client = new RmiTinyClient();
			int sumLocal = client.stub().getSum(5, 6);
			System.out.println("get sum from server: sum = " + sumLocal);
			client.stub().printSum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
