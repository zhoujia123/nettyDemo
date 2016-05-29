package cn.com.countrygarden.ohome.store.server.kits;

/**
 * Created by xiaobai on 16/5/28.
 */
public class CheckKit {

    /**
     * 生成校验码
     * @param addr 设备地址
     * @param type 设备类型
     * @param com  命令,状态
     * @param data 数据
     * @return
     */
    public static byte createCheck(byte[] addr,byte type,byte[] com,byte[] data){
        byte result = 0;
        for (byte a : addr) {
            result ^= a;
        }
        result ^= type;
        for (byte a : com) {
            result ^= a;
        }
        if(data != null && data.length > 0){
            for (byte a : data) {
                result ^= a;
            }
        }
        return result;
    }
}
