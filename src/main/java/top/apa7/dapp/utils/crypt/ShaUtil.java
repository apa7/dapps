package top.apa7.dapp.utils.crypt;

import java.security.MessageDigest;
import org.apache.commons.lang.StringUtils;
import top.apa7.dapp.constant.BaseLogger;
import top.apa7.dapp.constant.CommonConstant;


/**
 * Sha加密算法
 * 
 * @author: apa7
 * @date:2015年12月28日
 * @Copyright
 */
public class ShaUtil extends BaseLogger {

    private static ShaUtil shaUtil = null;

    public synchronized static ShaUtil getInstance() {
        if (shaUtil == null) {
            shaUtil = new ShaUtil();
        }
        return shaUtil;
    }

    private ShaUtil() {
    }

    /***
     * SHA加密 生成40位SHA码
     * 
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public String shaEncode(String inStr) throws Exception {
        StringBuffer hexValue = new StringBuffer();
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return hexValue.toString();
        }

        try {
            byte[] byteArray = (StringUtils.reverse(CommonConstant.SYSTEM_NAME) + StringUtils.reverse(inStr)
                                + CommonConstant.SYSTEM_NAME).getBytes(CommonConstant.CHARSET);

            byte[] md5Bytes = sha.digest(byteArray);
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return hexValue.toString();
    }

    /**
     * 测试主函数
     * 
     * @param args
     * @throws Exception
     */
    public static void main_test(String args[]) throws Exception {
        String str = new String("amigoxiexiexingxing");
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + ShaUtil.getInstance().shaEncode(str));
    }
}
