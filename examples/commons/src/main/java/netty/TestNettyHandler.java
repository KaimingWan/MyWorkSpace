package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Wan Kaiming on 2017/1/5
 * @version 1.0
 */
public class TestNettyHandler {

    public static void main(String[] args) {
        ByteBuf in = Unpooled.buffer(16);
        in.writeByte(0xCA);
        in.writeShort(0x0010);
        in.writeByte(0xFE);
        in.writeBytes("HELLO, WORLD".getBytes());

        EmbeddedChannel channel = new EmbeddedChannel();
        channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 2, -3, 3));
        channel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));

    }
}
