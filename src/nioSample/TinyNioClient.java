/**
 * 
 */
package nioSample;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;

/**
 * @author kevin_yang
 *
 */
public class TinyNioClient
{
    public static void printUsage()
    {
        System.out.println("TinyNioClient IPAdress [Port=2333]");
    }
    
    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            printUsage();
            return;
        }
        
        int port = 23333;
        if(args.length < 2)
        {
            try
            {
                port = Integer.parseInt(args[1]);
            }
            catch(RuntimeException e)
            {
                port = 23333;
            }
        }
        
        try
        {
            SocketAddress address = new InetSocketAddress(args[0], port);
            SocketChannel client = SocketChannel.open(address);
            client.configureBlocking(false);
            
            ByteBuffer buffer = ByteBuffer.allocate(26);
            WritableByteChannel out = Channels.newChannel(System.out);
            while(true)
            {
                int n = client.read(buffer);
                if(n > 0)
                {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();                   
                }
                else if(n == -1)
                {
                    break;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
