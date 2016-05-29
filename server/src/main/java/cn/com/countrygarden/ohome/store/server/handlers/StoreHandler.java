package cn.com.countrygarden.ohome.store.server.handlers;

import cn.com.countrygarden.ohome.store.server.enums.Command;
import cn.com.countrygarden.ohome.store.server.protocol.StoreProtocol;
import com.google.common.primitives.Shorts;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;

import java.util.concurrent.Callable;

/**
 * Created by xiaobai on 16/5/28.
 */
public class StoreHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof StoreProtocol)){
            return;
        }
        System.out.println(((StoreProtocol) msg).getCHK());
        System.out.println(new String(((StoreProtocol) msg).getINFO()));

        switch (((StoreProtocol) msg).getCOM()){
            case Command.Qr_Return:
                if(((StoreProtocol) msg).getINFO() != null){
                    // TODO: 16/5/29 开启线程去check ,然后发送通知
                    System.out.println("开启线程去check ,然后发送通知");
                    Future<Boolean> result = ctx.executor().submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            //// TODO: 16/5/29 校验数据库里面是否包含该标示
                            return true;
                        }
                    });
                    if(result.isSuccess() && result.get()){
                        // TODO: 16/5/29 发送远程开门指令
                        StoreProtocol openDoor = new StoreProtocol();
                        openDoor.setADDR(((StoreProtocol) msg).getADDR());
                        openDoor.setCOM(Command.Remote_Open);
                        openDoor.setLEN((short) 14);
                        openDoor.setINFO(Shorts.toByteArray((short) 0xFF01));
                        ctx.writeAndFlush(openDoor);
                    }
                }
                break;
            default: break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
