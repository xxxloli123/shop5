package com.interfaceconfig;

public interface Config {
    String LOCAL_HOST = "http://192.168.0.109:8080/";
    String HOST = "http://www.zsh7.com/";
    // 登录
    String LOGIN = "slowlife/appuser/userlogin";
    // 注册

    String REGISTER = "slowlife/appshop/shopregister";
    // 验证码
    String SMS_CODE = "slowlife/share/getsmsmobile";
    /**
     * 重设密码
     */
    String SEARPWD = "slowlife/appuser/findpassword";

    /**
     * 二维码
     */
    String QRCODE = "slowlife/img/twoDimensionCode/";

    /**
     * 图片
     */
    String IMG = "slowlife/img/pictureLibrary/";

    /**
     * 编辑规格
     */
    String Specification = "slowlife/shop/invoking_add.html?productId=";

    /**
     * 添加、修改 商家自己 活动:
     */
    String ADD_EDIT_Activity= "slowlife/appshop/shopmodifyshopactivity ";

    /**
     * 添加商品不添加规格:
     */
    String ADD_Commodity= "slowlife/appshop/addproductbasicattribute ";

    /**
     * 得到 上传商品时在图片库选择图片
     */
    String GET_SelectCommodityImg= "slowlife/appshop/pagequerypicturelibrary ";

    /**
     * 得到lineOrder订单 lineOrder:
     */
    String GET_LineOrder= "slowlife/appshop/shopqueryallorders ";

    /**
     * 商家接单 或拒单:
     */
    String Receive_Reject= "slowlife/appshop/shopreceivedorder ";

    /**
     * 得到待处理订单:
     */
    String GET_HandleOrder= "slowlife/appshop/shopqueryneworders ";

    /**
     * 根据商家二级分类ID获取商品:
     */
    String GET_Commodity= "slowlife/appshop/getproduct ";

    /**
     *  根据父级分类查询所有通用子级分类:
     */
    String GET_UniversalClassify = "slowlife/appshop/getallclassbyfatherid ";

    /**
     * 根据一级分类获取店铺二级分类:
     */
    String GET_Classify_2 = "slowlife/appshop/gettwoshopconsumecode ";

    /**
     * 注册所需街道信息
     */
    String REGISTER_ADDRESS = "slowlife/appuser/addaddressinformation ";

    /**
     * 获取商家注册时选择一级通用分类
     */
    String SHOP_TYPE = "slowlife/appshop/shopregisterofgenericclass";

    /**
     * 设置店铺缩略图、 身份、执照认证
     */
    String XXRZ = "slowlife/appshop/shopidentityconfirm";

    /**
     * 上传用户图片
     */
    String UPLoAD_IMG = "slowlife/appuser/uploaduserimgs";

    /**
     * 店铺自己修改店铺基本信息
     */
    String EDIT_SHOP_INFO = "slowlife/appshop/shopmodifymessage";

    /**
     * 获取所有桌号信息
     */
    String GET_TABLE = "slowlife/appshop/getshoptable";

    /**
     * 添加多个桌号
     */
    String ADD_TABLES = "slowlife/appshop/addmanyshoptable";

    /**
     *  删除一个桌号delete
     */
    String DELETE_TABLES = "slowlife/appshop/deloneshoptable";

    /**
     * 添加一个桌号
     */
    String ADD_TABLE = "slowlife/appshop/addoneshoptable";

    /**
     * 获取所有点餐码:
     */
    String ADD_ExpenseCode = "slowlife/appshop/addoneshopconsumecode";

    /**
     * 添加一个点餐码:
     */
    String GET_ExpenseCode = "slowlife/appshop/getshopconsumecode";

    /**
     * :获取店铺一级分类:
     */
    String GET_Classify_1 = "slowlife/appshop/getoneproductclass";

    /**
     * 添加修改店铺商品分类:
     */
    String ADD_Classify = "slowlife/appshop/modifyproductclass";

    /**
     * 删除店铺商品分类:
     */
    String DELETE_Classify = "slowlife/appshop/delproductclass";

    static class UrlFQ {
        public static String getUrl(String url) {
            return HOST + url;
        }
    }

    static class Url {
        public static String getUrl(String url) {
            return LOCAL_HOST + url;
        }
    }

}
