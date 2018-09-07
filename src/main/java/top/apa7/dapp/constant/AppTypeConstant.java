package top.apa7.dapp.constant;

/**
 * 项目类型常量
 * 
 * @author: apa7
 * @date:2016年1月9日
 * @Copyright
 */
public enum AppTypeConstant {

    IOS("1", "ios"), ANDROID("2", "android"), WEB("3", "web"), WECHAT("4", "wechat");

    /**
     * key
     */
    private String appType;

    /**
     * 值
     */
    private String appValue;

    // 构造方法
    private AppTypeConstant(String appType, String appValue) {
        this.appType = appType;
        this.appValue = appValue;
    }

    // 普通方法
    public static String getAppSecret(String appType) {
        for (AppTypeConstant appKeySecretConstant : AppTypeConstant.values()) {
            if (appKeySecretConstant.getAppType().equalsIgnoreCase(appType)) {
                return appKeySecretConstant.getAppValue();
            }
        }
        return null;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppValue() {
        return appValue;
    }

    public void setAppValue(String appValue) {
        this.appValue = appValue;
    }

}
