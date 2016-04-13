/**
 * 
 */
package nioSample;

import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.io.IOException;
import java.util.*;

/**
 * @author kevin_yang
 *
 */
public class TinyNioServer
{
    public static void main(String[] args)
    {
        int port = 23333;
        try
        {
            port = Integer.parseInt(args[0]);
        }
        catch(RuntimeException e)
        {
            e.printStackTrace();
        }
        
        byte[] serverInfo = new byte[26];
        for(byte i = 'a'; i <= 'z'; i++)
        {
            serverInfo[i - 'a'] = i;
        }
        ServerSocketChannel serverChannel;
        Selector selector;
        try
        {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            ss.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Listenning on port: " + port);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return;
        }
        
        while(true)
        {
            try
            {
                selector.select();
                Set<SelectionKey> readKey = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readKey.iterator();
                while(iterator.hasNext())
                {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try
                    {
                        if(key.isAcceptable())
                        {
                            ServerSocketChannel server = (ServerSocketChannel)key.channel();
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_WRITE);
                            System.out.println("Accepted client from : " + client);
                        }
                        else if (key.isWritable())
                        {
                            SocketChannel client = (SocketChannel)key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(27);
                            buffer.put(serverInfo);
                            buffer.put((byte) '\n');
                            buffer.flip();
                            client.write(buffer);
                            System.out.println("Buffer" + buffer +" written to: " + client);
                        }
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                        key.cancel();
                        try
                        {
                            key.channel().close();
                        }
                        catch(IOException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
