package RmiSamples;

import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public class RmiTinyServer extends UnicastRemoteObject implements RmiTinyRemote
{
	private int m_sum;
	public RmiTinyServer() throws RemoteException
	{
		m_sum = -1;
	};
	
	public int getSum(int a, int b)
	{
		m_sum = a + b;
		return m_sum;
	}
	
	public void printSum()
	{
		System.out.println("Sum is : " + m_sum);
	}
	
	public static void main(String[] args)
	{
		try {
			RmiTinyRemote service = new RmiTinyServer();
			Naming.bind("TinyServer", service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
