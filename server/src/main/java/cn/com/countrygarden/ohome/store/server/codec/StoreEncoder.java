package cn.com.countrygarden.ohome.store.server.codec;

import cn.com.countrygarden.ohome.store.server.enums.Statu;
import cn.com.countrygarden.ohome.store.server.kits.CheckKit;
import cn.com.countrygarden.ohome.store.server.protocol.StoreProtocol;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;

import java.util.List;

/**
 * Created by xiaobai on 16/5/28.
 */
public class StoreEncoder extends ByteToMessageCodec<StoreProtocol> {

    /**
     * 最少报文长度
     */
    private final static int TOTAL_LEN = 14;
    /**
     * 头标示
     */
    private final static byte SOI = 0x02;

    /**
     * 类型标示
     */
    private final static byte TYPE = 0x11;

    /**
     * 尾标示
     */
    private final static byte EOI = 0x03;


    @Override
    protected void encode(ChannelHandlerContext ctx, StoreProtocol msg, ByteBuf out) throws Exception {
        //start
        out.writeByte(SOI);
        //长度
        out.writeShort(msg.getLEN());
        //设备地址
        out.writeBytes(msg.getADDR());
        //设备类型
        out.writeByte(TYPE);
        //命令,状态
        out.writeShort(msg.getCOM());
        //数据
        out.writeBytes(msg.getINFO());
        //校验字
        out.writeByte(CheckKit.createCheck(msg.getADDR(),TYPE,Shorts.toByteArray(msg.getCOM()),msg.getINFO()));
        //end
        out.writeByte(EOI);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < TOTAL_LEN){
            return;
        }
        //标记读
        in.markReaderIndex();
        byte start = in.readByte();
        //如果协议头不正确则返回
        if(start != SOI) {
            throw new DecoderException("协议头不正确");
        }

        short len = in.readShort();
        //如果内容不够,则返回
        if(in.readableBytes() + 3 < len){
            //重置读
            in.resetReaderIndex();
            return;
        }
//        in.skipBytes(3);
        //设备地址
        byte[] addr = new byte[6];
        in.readBytes(addr);
        //设备类型
        byte type = in.readByte();
        if(type != TYPE) {
//            addr.release();
            throw new DecoderException("设备类型不正确");
        }
        //命令
        short com = in.readShort();
//        in.readBytes(com);
        //数据长度
        int dataLen = len - 12;
        byte[] data = null;
        if(dataLen > 0){
            data = new byte[dataLen];
            in.readBytes(data);
        }
        //校验字
        final byte chk = in.readByte();
        if(!validCheck(chk,addr,type,Shorts.toByteArray(com),data)){
            throw new DecoderException("数据校验位不正确");
        }
        byte end = in.readByte();
        if(end != EOI){
            throw new DecoderException("尾标示不正确");
        }
        out.add(new StoreProtocol(start,len,addr,type,com,data,chk,end));
    }

    /**
     * 校验
     * @param chk
     * @param addr
     * @param type
     * @param com
     * @param data
     * @return
     */
    private boolean validCheck(byte chk,byte[] addr,byte type,byte[] com,byte[] data){
        byte result = CheckKit.createCheck(addr, type, com, data);
        return chk == result;
    }

}
