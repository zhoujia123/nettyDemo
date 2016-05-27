package cn.com.countrygarden.ohome.store.server.protocol;

import java.io.Serializable;

/**
 * 门店门禁协议
 * Created by zhoujia05 on 2016/5/27.
 */
public class StoreProtocol implements Serializable {

    private static final long   serialVersionUID    = 1L;

    /**
     * 起始字  1Byte 固定0x02
     */
    private byte SOI = 0x02;

    /**
     * 数据长度  2Byte, 除开始字节和结束字节外,数据包的长度
     */
    private byte[] LEN = new byte[2];

    /**
     * 设备地址  6Byte,控制器的实际地址，若采用TCP/IP通讯方式，地址为0
     */
    private byte[] ADDR = new byte[6];

    /**
     * 设备类型 1Byte,默认为0x11，门禁控制器
     */
    private byte TYPE = 0x11;

    /**
     * 命令、状态 2byte,当第一字节不为0时，说明是命令，和第2字节一起用于指明本数据包的含义，在问方发数据时使用该方式；当第一字节为0时，说明是状态返回，第2字节即操作是否成功。
     */
    private byte[] COM = new byte[2];

    /**
     * 传输数据
     */
    private byte[] INFO;

    /**
     * 校验字  1byte , 是从设备地址到校验字前的所有字节的异或值。
     */
    private byte CHK;

    /**
     * 结束字  1byte, 固定的字节 0x03.
     */
    private byte EOI = 0x03;


    public byte[] getLEN() {
        return LEN;
    }

    public void setLEN(byte[] LEN) {
        this.LEN = LEN;
    }

    public byte[] getADDR() {
        return ADDR;
    }

    public void setADDR(byte[] ADDR) {
        this.ADDR = ADDR;
    }

    public byte[] getCOM() {
        return COM;
    }

    public void setCOM(byte[] COM) {
        this.COM = COM;
    }

    public byte[] getINFO() {
        return INFO;
    }

    public void setINFO(byte[] INFO) {
        this.INFO = INFO;
    }

    public byte getCHK() {
        return CHK;
    }

    /**
     * 设置校验字
     */
    public void setCHK() {
        byte result = 0;
        for (byte a : ADDR) {
            result ^= a;
        }
        result ^= TYPE;
        for (byte a : COM) {
            result ^= a;
        }
        for (byte a : INFO) {
            result ^= a;
        }
        this.CHK = result;
    }

}
