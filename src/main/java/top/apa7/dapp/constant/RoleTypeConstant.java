package top.apa7.dapp.constant;

/**
 * 角色类别常量
 * 
 * @author: apa7
 * @date:2016年1月9日
 * @Copyright
 */
public enum RoleTypeConstant {

    ADMIN("admin", "系统管理员");

    /**
     * 类别
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    // 构造方法
    private RoleTypeConstant(String key, String name) {
        this.key = key;
        this.name = name;
    }

    // 普通方法
    public static String getName(String key) {
        for (RoleTypeConstant fileTypeConstant : RoleTypeConstant.values()) {
            if (fileTypeConstant.getKey().equals(key)) {
                return fileTypeConstant.getName();
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
