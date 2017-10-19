package com.example.xxxloli.zshmerchant.objectmodel;

import com.util.ParcelableUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class OrderEntity extends ParcelableUtil<OrderEntity> {


    /**
     * id : 402880ea5f22ecfa015f22f524390002
     * orderNumber : 201710161013318320858
     * createDate : 2017-10-16 10:13:32
     * createUserId : null
     * orderOfCompanyId : null
     * createUserName : null
     * createUserPhone : null
     * type : Shopping
     * type_value : 购物类
     * status : UnPayed
     * status_value : 待付款
     * receivables : null
     * receivables_value : null
     * payType : null
     * payType_value : null
     * payDate : null
     * redPackets : no
     * redPacketsFee : 0
     * redPacketsName : null
     * redPacketsId : null
     * transactionNumber : null
     * alipayBinding : null
     * weixinBingding : null
     * comment : 31
     * tariffId : null
     * userPayFee : 0
     * cost : 1
     * deliveryFee : 200
     * packingprice : 0
     * userActualFee : 201
     * payStatus : UnPayed
     * payStatus_value : 未付款
     * urgent : no
     * urgentFee : 0
     * insured : no
     * goodsTotal : null
     * insuredFee : 0
     * weight : 0
     * postmanProfit : 0
     * compnayProfit : 0
     * platformProfit : 0
     * shopProfit : 0
     * platformDelivery : no
     * platformDeliveryOrderId : null
     * shopOrder : no
     * shopOrderId : null
     * shopUserDistance : 0
     * deliveryStatesExplain : null
     * platformDeliveryFee : null
     * lineOrder : NormalOrder
     * lineOrder_value : 配送到家
     * discountProportion : 100%
     * discountCost : 0
     * postmanId : null
     * postmanName : null
     * postmanPhone : null
     * postmanheaderImg : null
     * postmanCommpanyId : null
     * postmanCommpanyName : null
     * userChoiceCommpanyId : null
     * userChoiceCommpanyCode : null
     * userChoiceCommpanyName : null
     * logisticsNumber : null
     * barCodeName : null
     * markDestination : null
     * goodsName : null
     * startPro : 重庆市
     * startCity : 重庆市
     * startDistrict : 涪陵区
     * startStreet : 城区
     * startHouseNumber : null
     * startLng : 107.376253
     * startLat : 29.700085
     * receiverId : null
     * receiverName : 2
     * receiverPhone : 123
     * endPro : 重庆市
     * endCity : 重庆市
     * endDistrict : 涪陵区
     * endStreet : 城区
     * endHouseNumber : 123
     * endLng : 107.37788
     * endLat : 29.70762
     * shopId : 402880e65ed0bda0015ed0c876e00007
     * shopName : 金刀客
     * arriveDate : null
     * postmanDate : null
     * shopSendDate : null
     * postmanSendDate : null
     * postmanPickupDate : null
     * returnDate : null
     * returnReason : null
     * merchantReplay : null
     * tradeImg : null
     * evaluate : no
     * goods : [{"id":"402880ea5f22ecfa015f22f5243a0003","shopId":"402880e65ed0bda0015ed0c876e00007","shopName":"金刀客","goodsId":"402880ea5effdb5e015f002103530006","goodsName":"商品1-3-2","smallImg":"d9337e3d-c7b8-43a8-8129-130dce3865bb.jpg","goodsPrice":1,"goodsnum":1,"isStandard":"yes","productcategory":"商品1-3-2-2","standardName":"3"}]
     * mealcode : null
     * mealnumber : null
     * quick : no
     */

    private String id;
    private String orderNumber;
    private String createDate;
    private Object createUserId;
    private Object orderOfCompanyId;
    private Object createUserName;
    private Object createUserPhone;
    private String type;
    private String type_value;
    private String status;
    private String status_value;
    private Object receivables;
    private Object receivables_value;
    private Object payType;
    private Object payType_value;
    private Object payDate;
    private String redPackets;
    private int redPacketsFee;
    private Object redPacketsName;
    private Object redPacketsId;
    private Object transactionNumber;
    private Object alipayBinding;
    private Object weixinBingding;
    private String comment;
    private Object tariffId;
    private int userPayFee;
    private int cost;
    private int deliveryFee;
    private int packingprice;
    private int userActualFee;
    private String payStatus;
    private String payStatus_value;
    private String urgent;
    private int urgentFee;
    private String insured;
    private Object goodsTotal;
    private int insuredFee;
    private int weight;
    private int postmanProfit;
    private int compnayProfit;
    private int platformProfit;
    private int shopProfit;
    private String platformDelivery;
    private Object platformDeliveryOrderId;
    private String shopOrder;
    private Object shopOrderId;
    private int shopUserDistance;
    private Object deliveryStatesExplain;
    private Object platformDeliveryFee;
    private String lineOrder;
    private String lineOrder_value;
    private String discountProportion;
    private int discountCost;
    private Object postmanId;
    private Object postmanName;
    private Object postmanPhone;
    private Object postmanheaderImg;
    private Object postmanCommpanyId;
    private Object postmanCommpanyName;
    private Object userChoiceCommpanyId;
    private Object userChoiceCommpanyCode;
    private Object userChoiceCommpanyName;
    private Object logisticsNumber;
    private Object barCodeName;
    private Object markDestination;
    private Object goodsName;
    private String startPro;
    private String startCity;
    private String startDistrict;
    private String startStreet;
    private Object startHouseNumber;
    private double startLng;
    private double startLat;
    private Object receiverId;
    private String receiverName;
    private String receiverPhone;
    private String endPro;
    private String endCity;
    private String endDistrict;
    private String endStreet;
    private String endHouseNumber;
    private double endLng;
    private double endLat;
    private String shopId;
    private String shopName;
    private Object arriveDate;
    private Object postmanDate;
    private Object shopSendDate;
    private Object postmanSendDate;
    private Object postmanPickupDate;
    private Object returnDate;
    private Object returnReason;
    private Object merchantReplay;
    private Object tradeImg;
    private String evaluate;
    private Object mealcode;
    private Object mealnumber;
    private String quick;
    private ArrayList<BillCommodity> goods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public Object getOrderOfCompanyId() {
        return orderOfCompanyId;
    }

    public void setOrderOfCompanyId(Object orderOfCompanyId) {
        this.orderOfCompanyId = orderOfCompanyId;
    }

    public Object getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(Object createUserName) {
        this.createUserName = createUserName;
    }

    public Object getCreateUserPhone() {
        return createUserPhone;
    }

    public void setCreateUserPhone(Object createUserPhone) {
        this.createUserPhone = createUserPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_value() {
        return type_value;
    }

    public void setType_value(String type_value) {
        this.type_value = type_value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_value() {
        return status_value;
    }

    public void setStatus_value(String status_value) {
        this.status_value = status_value;
    }

    public Object getReceivables() {
        return receivables;
    }

    public void setReceivables(Object receivables) {
        this.receivables = receivables;
    }

    public Object getReceivables_value() {
        return receivables_value;
    }

    public void setReceivables_value(Object receivables_value) {
        this.receivables_value = receivables_value;
    }

    public Object getPayType() {
        return payType;
    }

    public void setPayType(Object payType) {
        this.payType = payType;
    }

    public Object getPayType_value() {
        return payType_value;
    }

    public void setPayType_value(Object payType_value) {
        this.payType_value = payType_value;
    }

    public Object getPayDate() {
        return payDate;
    }

    public void setPayDate(Object payDate) {
        this.payDate = payDate;
    }

    public String getRedPackets() {
        return redPackets;
    }

    public void setRedPackets(String redPackets) {
        this.redPackets = redPackets;
    }

    public int getRedPacketsFee() {
        return redPacketsFee;
    }

    public void setRedPacketsFee(int redPacketsFee) {
        this.redPacketsFee = redPacketsFee;
    }

    public Object getRedPacketsName() {
        return redPacketsName;
    }

    public void setRedPacketsName(Object redPacketsName) {
        this.redPacketsName = redPacketsName;
    }

    public Object getRedPacketsId() {
        return redPacketsId;
    }

    public void setRedPacketsId(Object redPacketsId) {
        this.redPacketsId = redPacketsId;
    }

    public Object getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Object transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Object getAlipayBinding() {
        return alipayBinding;
    }

    public void setAlipayBinding(Object alipayBinding) {
        this.alipayBinding = alipayBinding;
    }

    public Object getWeixinBingding() {
        return weixinBingding;
    }

    public void setWeixinBingding(Object weixinBingding) {
        this.weixinBingding = weixinBingding;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Object getTariffId() {
        return tariffId;
    }

    public void setTariffId(Object tariffId) {
        this.tariffId = tariffId;
    }

    public int getUserPayFee() {
        return userPayFee;
    }

    public void setUserPayFee(int userPayFee) {
        this.userPayFee = userPayFee;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public int getPackingprice() {
        return packingprice;
    }

    public void setPackingprice(int packingprice) {
        this.packingprice = packingprice;
    }

    public int getUserActualFee() {
        return userActualFee;
    }

    public void setUserActualFee(int userActualFee) {
        this.userActualFee = userActualFee;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatus_value() {
        return payStatus_value;
    }

    public void setPayStatus_value(String payStatus_value) {
        this.payStatus_value = payStatus_value;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public int getUrgentFee() {
        return urgentFee;
    }

    public void setUrgentFee(int urgentFee) {
        this.urgentFee = urgentFee;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public Object getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Object goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public int getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(int insuredFee) {
        this.insuredFee = insuredFee;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPostmanProfit() {
        return postmanProfit;
    }

    public void setPostmanProfit(int postmanProfit) {
        this.postmanProfit = postmanProfit;
    }

    public int getCompnayProfit() {
        return compnayProfit;
    }

    public void setCompnayProfit(int compnayProfit) {
        this.compnayProfit = compnayProfit;
    }

    public int getPlatformProfit() {
        return platformProfit;
    }

    public void setPlatformProfit(int platformProfit) {
        this.platformProfit = platformProfit;
    }

    public int getShopProfit() {
        return shopProfit;
    }

    public void setShopProfit(int shopProfit) {
        this.shopProfit = shopProfit;
    }

    public String getPlatformDelivery() {
        return platformDelivery;
    }

    public void setPlatformDelivery(String platformDelivery) {
        this.platformDelivery = platformDelivery;
    }

    public Object getPlatformDeliveryOrderId() {
        return platformDeliveryOrderId;
    }

    public void setPlatformDeliveryOrderId(Object platformDeliveryOrderId) {
        this.platformDeliveryOrderId = platformDeliveryOrderId;
    }

    public String getShopOrder() {
        return shopOrder;
    }

    public void setShopOrder(String shopOrder) {
        this.shopOrder = shopOrder;
    }

    public Object getShopOrderId() {
        return shopOrderId;
    }

    public void setShopOrderId(Object shopOrderId) {
        this.shopOrderId = shopOrderId;
    }

    public int getShopUserDistance() {
        return shopUserDistance;
    }

    public void setShopUserDistance(int shopUserDistance) {
        this.shopUserDistance = shopUserDistance;
    }

    public Object getDeliveryStatesExplain() {
        return deliveryStatesExplain;
    }

    public void setDeliveryStatesExplain(Object deliveryStatesExplain) {
        this.deliveryStatesExplain = deliveryStatesExplain;
    }

    public Object getPlatformDeliveryFee() {
        return platformDeliveryFee;
    }

    public void setPlatformDeliveryFee(Object platformDeliveryFee) {
        this.platformDeliveryFee = platformDeliveryFee;
    }

    public String getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(String lineOrder) {
        this.lineOrder = lineOrder;
    }

    public String getLineOrder_value() {
        return lineOrder_value;
    }

    public void setLineOrder_value(String lineOrder_value) {
        this.lineOrder_value = lineOrder_value;
    }

    public String getDiscountProportion() {
        return discountProportion;
    }

    public void setDiscountProportion(String discountProportion) {
        this.discountProportion = discountProportion;
    }

    public int getDiscountCost() {
        return discountCost;
    }

    public void setDiscountCost(int discountCost) {
        this.discountCost = discountCost;
    }

    public Object getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(Object postmanId) {
        this.postmanId = postmanId;
    }

    public Object getPostmanName() {
        return postmanName;
    }

    public void setPostmanName(Object postmanName) {
        this.postmanName = postmanName;
    }

    public Object getPostmanPhone() {
        return postmanPhone;
    }

    public void setPostmanPhone(Object postmanPhone) {
        this.postmanPhone = postmanPhone;
    }

    public Object getPostmanheaderImg() {
        return postmanheaderImg;
    }

    public void setPostmanheaderImg(Object postmanheaderImg) {
        this.postmanheaderImg = postmanheaderImg;
    }

    public Object getPostmanCommpanyId() {
        return postmanCommpanyId;
    }

    public void setPostmanCommpanyId(Object postmanCommpanyId) {
        this.postmanCommpanyId = postmanCommpanyId;
    }

    public Object getPostmanCommpanyName() {
        return postmanCommpanyName;
    }

    public void setPostmanCommpanyName(Object postmanCommpanyName) {
        this.postmanCommpanyName = postmanCommpanyName;
    }

    public Object getUserChoiceCommpanyId() {
        return userChoiceCommpanyId;
    }

    public void setUserChoiceCommpanyId(Object userChoiceCommpanyId) {
        this.userChoiceCommpanyId = userChoiceCommpanyId;
    }

    public Object getUserChoiceCommpanyCode() {
        return userChoiceCommpanyCode;
    }

    public void setUserChoiceCommpanyCode(Object userChoiceCommpanyCode) {
        this.userChoiceCommpanyCode = userChoiceCommpanyCode;
    }

    public Object getUserChoiceCommpanyName() {
        return userChoiceCommpanyName;
    }

    public void setUserChoiceCommpanyName(Object userChoiceCommpanyName) {
        this.userChoiceCommpanyName = userChoiceCommpanyName;
    }

    public Object getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(Object logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public Object getBarCodeName() {
        return barCodeName;
    }

    public void setBarCodeName(Object barCodeName) {
        this.barCodeName = barCodeName;
    }

    public Object getMarkDestination() {
        return markDestination;
    }

    public void setMarkDestination(Object markDestination) {
        this.markDestination = markDestination;
    }

    public Object getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(Object goodsName) {
        this.goodsName = goodsName;
    }

    public String getStartPro() {
        return startPro;
    }

    public void setStartPro(String startPro) {
        this.startPro = startPro;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getStartDistrict() {
        return startDistrict;
    }

    public void setStartDistrict(String startDistrict) {
        this.startDistrict = startDistrict;
    }

    public String getStartStreet() {
        return startStreet;
    }

    public void setStartStreet(String startStreet) {
        this.startStreet = startStreet;
    }

    public Object getStartHouseNumber() {
        return startHouseNumber;
    }

    public void setStartHouseNumber(Object startHouseNumber) {
        this.startHouseNumber = startHouseNumber;
    }

    public double getStartLng() {
        return startLng;
    }

    public void setStartLng(double startLng) {
        this.startLng = startLng;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public Object getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Object receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getEndPro() {
        return endPro;
    }

    public void setEndPro(String endPro) {
        this.endPro = endPro;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getEndDistrict() {
        return endDistrict;
    }

    public void setEndDistrict(String endDistrict) {
        this.endDistrict = endDistrict;
    }

    public String getEndStreet() {
        return endStreet;
    }

    public void setEndStreet(String endStreet) {
        this.endStreet = endStreet;
    }

    public String getEndHouseNumber() {
        return endHouseNumber;
    }

    public void setEndHouseNumber(String endHouseNumber) {
        this.endHouseNumber = endHouseNumber;
    }

    public double getEndLng() {
        return endLng;
    }

    public void setEndLng(double endLng) {
        this.endLng = endLng;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Object getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Object arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Object getPostmanDate() {
        return postmanDate;
    }

    public void setPostmanDate(Object postmanDate) {
        this.postmanDate = postmanDate;
    }

    public Object getShopSendDate() {
        return shopSendDate;
    }

    public void setShopSendDate(Object shopSendDate) {
        this.shopSendDate = shopSendDate;
    }

    public Object getPostmanSendDate() {
        return postmanSendDate;
    }

    public void setPostmanSendDate(Object postmanSendDate) {
        this.postmanSendDate = postmanSendDate;
    }

    public Object getPostmanPickupDate() {
        return postmanPickupDate;
    }

    public void setPostmanPickupDate(Object postmanPickupDate) {
        this.postmanPickupDate = postmanPickupDate;
    }

    public Object getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Object returnDate) {
        this.returnDate = returnDate;
    }

    public Object getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Object returnReason) {
        this.returnReason = returnReason;
    }

    public Object getMerchantReplay() {
        return merchantReplay;
    }

    public void setMerchantReplay(Object merchantReplay) {
        this.merchantReplay = merchantReplay;
    }

    public Object getTradeImg() {
        return tradeImg;
    }

    public void setTradeImg(Object tradeImg) {
        this.tradeImg = tradeImg;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Object getMealcode() {
        return mealcode;
    }

    public void setMealcode(Object mealcode) {
        this.mealcode = mealcode;
    }

    public Object getMealnumber() {
        return mealnumber;
    }

    public void setMealnumber(Object mealnumber) {
        this.mealnumber = mealnumber;
    }

    public String getQuick() {
        return quick;
    }

    public void setQuick(String quick) {
        this.quick = quick;
    }

    public ArrayList<BillCommodity> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<BillCommodity> goods) {
        this.goods = goods;
    }
}
