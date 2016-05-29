package cn.com.countrygarden.ohome.store.server.enums;

/**
 * 命令,指令
 * Created by zhoujia05 on 2016/5/27.
 */
public final class Command {

    /**
     * 二维码返回
     */
    public final static short Qr_Return = 0x5506;

    /**
     * 远程开门
     */
    public final static short Remote_Open = 0x5000;

    /**
     * 心跳
     */
    public final static short Heart = 0x4508;

    /**
     * 远程开门携带用户数据
     */
    public final static short Remote_Open_With_Data = 0x5055;

    /**
     * 二维码TCP服务器IP与端口设置指令
     */
    public final static short Tcp_Ip_Setting = 0x4407;

    /**
     * 二维码UDP服务器IP与端口设置指令
     */
    public final static short Udp_Ip_Setting = 0x4406;




}
