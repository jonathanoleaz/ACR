package Socket;

import java.nio.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Iterator;

public class SHMNB {
    public static void main(String[] args) {
        try{
            int pto = 1234;
            InetSocketAddress l = new InetSocketAddress(pto);
            ServerSocketChannel s= ServerSocketChannel.open();
            s.configureBlocking(false);
            s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            s.socket().bind(l);
            System.out.println("Servicio Iniciado ...  Esperando clientes ...");
            Selector sel = Selector.open();
            String msj="Un mensaje desde el servidor....";
            s.register(sel, SelectionKey.OP_ACCEPT);
            for(;;){
                sel.select();
                Iterator<SelectionKey>it=sel.selectedKeys().iterator();
                while(it.hasNext()){
                    SelectionKey k = (SelectionKey)it.next();
                    it.remove();
                    if(k.isAcceptable()){
                        SocketChannel cl = s.accept();
                        System.out.println("Cliente conectado desde: "+cl.socket().getInetAddress()+":"+cl.socket().getPort());
                        cl.configureBlocking(false);
                        cl.register(sel, SelectionKey.OP_WRITE);
                        continue;
                    }
                    if(k.isWritable()){
                        SocketChannel ch = (SocketChannel)k.channel();
                        ByteBuffer b = ByteBuffer.wrap(msj.getBytes());
                        ch.write(b);
                        ch.close();
                        continue;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
