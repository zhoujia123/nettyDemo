package cn.com.countrygarden.ohome.store.server;

import cn.com.countrygarden.ohome.store.server.codec.StoreEncoder;
import cn.com.countrygarden.ohome.store.server.handlers.StoreHandler;
import cn.com.countrygarden.ohome.store.server.kits.CheckKit;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xiaobai on 16/5/28.
 */

public class TestServer {

    @Test
    public void testFramesDecoded() throws Exception{
        ByteBuf buf = Unpooled.buffer(); //2


        byte[] data = "zhoujia".getBytes(CharsetUtil.UTF_8);
        buf.writeByte(0x02);
        buf.writeShort(12 + data.length);
        byte[] addr = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00};
        buf.writeBytes(addr);
        byte type = 0x11;
        buf.writeByte(type);
        byte[] com = new byte[]{0x55,0x06};
        buf.writeBytes(com);

        buf.writeBytes(data);
        buf.writeByte(CheckKit.createCheck(addr,type,com,data));
        buf.writeByte(0x03);

//        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new StoreEncoder(),new StoreHandler()); //3


        channel.writeInbound(buf);//4
//        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));

//        channel.finish();  //5
        Object read = channel.readOutbound();
        System.out.println(read);
//        Assert.assertEquals(buf.readSlice(3), read);
//        read.release();

//        read = (ByteBuf) channel.readInbound();
//        Assert.assertEquals(buf.readSlice(3), read);
//        read.release();

//        read = (ByteBuf) channel.readInbound();
//        Assert.assertEquals(buf.readSlice(3), read);
//        read.release();

//        Assert.assertNull(channel.readInbound());
//        buf.release();
    }
}
