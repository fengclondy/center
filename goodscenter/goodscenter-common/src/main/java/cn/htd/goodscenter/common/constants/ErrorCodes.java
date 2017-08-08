package cn.htd.goodscenter.common.constants;

/**
 * 商品中心统一错误码
 * 错误码格式：固定标识 + 错误类型 + 错误场景 + 错误编码； 例： E10001
 *           错误类型 :  E0	系统错误，E1	业务错误，E2	第三方错误
 *           错误业务场景 ： 00 校验场景，01类目异常   ……… ………
 *           错误编码  ： 01 ~ 99 范围
 *
 * @author chenkang
 */
public enum ErrorCodes {
	
	SUCCESS("000000"),
	
    /**
     * 业务错误 E1
     */
    // 1. 校验场景
	
    E10000("%s对象为NULL"),

    E10001("%s字段非空"),

    E10002("%s长度过小"),

    E10003("%s长度过大"),

    E10004("%s必须是数字"),
    
    E10005("参数对象为NULL"),
    
    E10006("参数校验未通过"),
    
    // 2. 类目场景
    E10101("类目存在子类目，不允许删除"),

    E10102("类目下存在品牌，不允许删除"),

    E10103("待删除的类目不存在"),

    E10104("类目下存在商品，不允许删除"),

    E10105("类目查询失败"),

    // 3. 属性场景
    E10201("添加属性失败"),

    E10202("添加属性值失败"),

    E10203("有商品正在使用该属性，不能删除"),

    E10204("删除类目属性失败"),

    E10205("删除类目属性值失败"),

    E10206("查询类目属性属性值列表失败"),

    E10207("添加类目属性失败, 属性名称已经存在"),

    E10208("添加类目属性值失败, 属性值名称已经存在"),

    // 4. 品牌场景
    E10301("添加品牌失败"),

    E10302("查询品牌列表失败"),

    E10303("修改品牌失败"),

    E10304("删除品牌失败，品牌和类目存在关联"),

    E10305("删除品牌异常"),

    E10306("删除品牌品类关系失败，品牌下存在商品"),

    E10307("删除品牌品类关系异常"),

    E10308("添加品牌品类关系失败"),

    E10309("查询品牌品类关系失败"),

    // 5. 库存场景
    E10401("锁定库存不足以释放"),
    E10402("库存不足"),
   
    /**
     * 系统错误 E0
     */
    E10501("超出销售范围"),
    
    E00001("系统异常");


    /**
     * 第三方错误 E2
     */

    /**
     * 错误消息
     */
    private String errorMsg;

    private ErrorCodes(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 获取错误码的错误消息
     * @return
     */
    public String getErrorMsg() {
        return this.errorMsg;
    }

    /**
     * 更加详细的，可附加消息的错误消息.可以在消息中添加占位符，增加消息的多样性。
     *
     * @param appendMsg, 附加错误消息
     * @return
     */
    public String getErrorMsg(String... appendMsg) {
        // 如果定义了占位符，用appendMsg替换{}；否则使用默认getErrorMsg()；
        return String.format(this.errorMsg, appendMsg);
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(E10001.getErrorMsg("ID"));
        System.out.println(E10001.name());
    }
}
