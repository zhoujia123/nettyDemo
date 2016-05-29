package cn.com.countrygarden.ohome.store.server.enums;

/**
 * 状态返回
 * Created by zhoujia05 on 2016/5/27.
 */
public final class Statu {

    /**
     * 命令接受成功
     */
    public final static short Sucess = 0x0000;

    /**
     * 命令无效
     */
    public final static short Command_Invalid = 0x0001;
    /**
     * 参数无效
     */
    public final static short Arge_Invalid = 0x0002;
    /**
     * 没有权限         (该命令需要在特定的情况下才能响应)
     */
    public final static short No_Auth = 0x0005;
    /**
     * 设备损坏         (由于设备的某些功能模块损坏而不能响应该命令)
     */
    public final static short Bad_Device = 0x0006;
    /**
     * 命令操作失败
     */
    public final static short Command_Fail = 0x0007;
    /**
     * 命令接受不成功
     */
    public final static short Command_Not_Arrive = 0x0008;

    /**
     * 控制器存储空间不足
     */
    public final static short Cpu_Not_torage = 0x0009;




}
