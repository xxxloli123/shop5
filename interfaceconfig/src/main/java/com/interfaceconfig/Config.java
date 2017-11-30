package com.interfaceconfig;

public interface Config {
    String LOCAL_HOST = "http://192.168.0.110:8080/";
    String HOST = "http://www.zsh7.com/";
    // 登录
    String LOGIN = "slowlife/appuser/userlogin";
    // 用户登录后修改密码
    String Password = "slowlife/appuser/updatepassword";
    // 验证码
    String SMS_CODE = "slowlife/share/getsmsmobile";
    // 注册
    String REGISTER = "slowlife/appshop/shopregister";

    // 检测更新
    String UPDATE = "slowlife/share/getnewestappversion?type=android_zsh_shop";

    /**
     * 重设密码
     */
    String ChangePhone = "slowlife/appuser/updatePhone";

    /**
     * 重设密码
     */
    String SEARPWD = "slowlife/appuser/findpassword";

    /**
     * 二维码
     */
    String QRCODE = "slowlife/img/twoDimensionCode/";

    /**
     * 商品图片
     */
    String IMG_Commodity = "slowlife/img/pictureLibrary/";

    /**
     * 头像图片
     */
    String IMG_Hear = "slowlife/img/shop/";

    /**
     *
     */
    String GET_AutoOrder = "slowlife/appshop/getshopdayinorder";

    /**
     * 商家回复评价
     */
    String Reply_Evaluate = "slowlife/appshop/shopreplyevaluate";

    /**
     * 商家获取全部评价，获取未回复评价
     */
    String GET_Evaluate = "slowlife/appshop/shopgetevaluate";

    /**
     * app店铺 管理统计
     */
    String GET_Data = "slowlife/appshop/shopmanagecount";

    /**
     * 商家账单流水
     */
    String GET_Bills = "slowlife/appshop/getuseraccount";

    /**
     * 掌生活 支付宝账户绑定
     */
    String Bound_Alipay = "slowlife/apppay/bindingalipayzsh";

    /**
     * 掌生活 微信账户绑定
     */
    String Bound_Weixin = "slowlife/wxpay/bindingweixinzsh";

    /**
     * 查询商家消息
     */
    String GET_Message = "slowlife/appshop/shopquerynotices";

    /**
     * 修改商品基本属性
     */
    String EDIT_Commodity = "slowlife/appshop/modifyproduct";

    /**
     * 更改商品状态
     */
    String EDIT_CommodityStatus  = "slowlife/appshop/modifyproductstatus";

    /**
     * 删除商品
     */
    String DELETE_Commodity = "slowlife/appshop/delproduct";

    /**
     * 商家下架已上传的平台活动商品
     */
    String DELETE_ActivityCommodity = "slowlife/appshop/shopmodifybargaingoodsstatus";

    /**
     * 商家申请上传平台活动商品
     */
    String ADD_ActivityCommodity = "slowlife/appshop/shopaddbargaingoods";

    /**
     * 商家查询自己的活动商品
     */
    String GET_ActivityCommodity = "slowlife/appshop/shopquerybargaingoods";


    /**
     * 规格
     */
    String Specification = "slowlife/shop/invoking_add.html?productId=";

    /**
     * 更改商家自己 活动状态
     */
    String EDIT_Activity_Status= "slowlife/appshop/shopmodifyshopactivitystatus ";

    /**
     * 获取店铺所有自己 活动::
     */
    String GET_Activity= "slowlife/appshop/shopgetshopactivity ";

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
     描述：商家修改订单价格
     */
    String Edit_Price= "slowlife/appshop/shopmodifyordercost ";

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

    String platformException = "slowlife/appshop/shopopinion";
    String getPlatformException = "slowlife/appshop/shopqueryopinion";
    String getAllStaff = "slowlife/appshop/shopgetusermessage";
    String addStaff = "slowlife/appshop/shopadduser";
    String alterStaff = "slowlife/appshop/shopmodifyuserstatus";

    static class Url {
        public static String getUrl(String url) {
            return HOST + url;
        }
    }
}
