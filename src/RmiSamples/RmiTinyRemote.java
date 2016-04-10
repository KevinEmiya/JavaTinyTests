package RmiSamples;

import java.rmi.*;

public interface RmiTinyRemote extends Remote {
	public  int getSum(int a, int b) throws RemoteException;
	public  void printSum() throws RemoteException; 
}
