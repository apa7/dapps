package top.apa7.dapp.utils;

import java.io.File;
import java.util.UUID;
import top.apa7.dapp.constant.BaseLogger;
import top.apa7.dapp.utils.crypt.Md5Util;


/**
 * 主键工具类
 * 
 * @author: apa7
 * @date:2015年12月28日
 * @Copyright
 */
public class PrimaryUtil extends BaseLogger {

    private static PrimaryUtil primaryUtil = null;

    public synchronized static PrimaryUtil getInstance() {
        if (primaryUtil == null) {
            primaryUtil = new PrimaryUtil();
        }
        return primaryUtil;
    }

    private PrimaryUtil() {
    }

    /**
     * 主键生成规则(uuid+日期+随机码),最后使用md5加密
     * 
     * @return
     * @throws Exception
     */
    public synchronized String getPrimaryValue() {
        try {
            String key = UUID.randomUUID().toString() + File.separator + System.currentTimeMillis() + File.separator
                    + RandomUtil.getInstance().createRandom(false, 8);
            return Md5Util.getInstance().md5Encode(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main_test(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(PrimaryUtil.getInstance().getPrimaryValue());
        }
    }

}
