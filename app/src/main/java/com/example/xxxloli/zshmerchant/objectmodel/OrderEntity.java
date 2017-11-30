package com.example.xxxloli.zshmerchant.objectmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxxloli on 2017/11/10.
 */

public class OrderEntity {

    /**
     * id : 2c9ad8435f9676da015fa51258a001de
     * orderNumber : 201711100436038726389
     * createDate : 2017-11-10 16:36:04
     * createUserId : null
     * orderOfCompanyId : null
     * createUserName : null
     * createUserPhone : null
     * type : Cater
     * type_value : 餐饮订单
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
     * comment : null
     * tariffId : null
     * userPayFee : 0
     * cost : 22
     * deliveryFee : 0
     * packingprice : 0
     * userActualFee : 0
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
     * lineOrder : ShopConsumption
     * lineOrder_value : 到店消费单
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
     * startHouseNumber : 太极大道77号
     * startLng : 107.376391
     * startLat : 29.700001
     * receiverId : null
     * receiverName : null
     * receiverPhone : null
     * endPro : null
     * endCity : null
     * endDistrict : null
     * endStreet : null
     * endHouseNumber : null
     * endLng : 0
     * endLat : 0
     * shopId : 2c9ad8435f75d383015f75d5b9690003
     * shopName : 吊炸天
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
     * goods : [{"id":"2c9ad8435f9676da015fa51258a001df","shopId":"2c9ad8435f75d383015f75d5b9690003","shopName":"吊炸天","goodsId":"2c9ad8435f9676fc015f9a2598bb0099","goodsName":"好吃点甄好曲奇巧克力豆味208g","smallImg":"78f8bd6b-f75f-4693-aeef-ebf0e2dee4b7.jpg","goodsPrice":22,"goodsnum":1,"isStandard":"no","productcategory":"","standardName":"","serving":"no"}]
     * mealcode : 125
     * mealnumber : 1
     * tableNumber : 1
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
    private double redPacketsFee;
    private Object redPacketsName;
    private Object redPacketsId;
    private Object transactionNumber;
    private Object alipayBinding;
    private Object weixinBingding;
    private Object comment;
    private Object tariffId;
    private double userPayFee;
    private double cost;
    private double deliveryFee;
    private double packingprice;
    private double userActualFee;
    private String payStatus;
    private String payStatus_value;
    private String urgent;
    private double urgentFee;
    private String insured;
    private Object goodsTotal;
    private double insuredFee;
    private double weight;
    private double postmanProfit;
    private double compnayProfit;
    private double platformProfit;
    private double shopProfit;
    private String platformDelivery;
    private Object platformDeliveryOrderId;
    private String shopOrder;
    private Object shopOrderId;
    private double shopUserDistance;
    private Object deliveryStatesExplain;
    private Object platformDeliveryFee;
    private String lineOrder;
    private String lineOrder_value;
    private String discountProportion;
    private double discountCost;
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
    private String startHouseNumber;
    private double startLng;
    private double startLat;
    private Object receiverId;
    private Object receiverName;
    private Object receiverPhone;
    private Object endPro;
    private Object endCity;
    private Object endDistrict;
    private Object endStreet;
    private Object endHouseNumber;
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
    private String mealcode;
    private String mealnumber;
    private String tableNumber;
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

    public double getRedPacketsFee() {
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

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getTariffId() {
        return tariffId;
    }

    public void setTariffId(Object tariffId) {
        this.tariffId = tariffId;
    }

    public double getUserPayFee() {
        return userPayFee;
    }

    public void setUserPayFee(int userPayFee) {
        this.userPayFee = userPayFee;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getPackingprice() {
        return packingprice;
    }

    public void setPackingprice(int packingprice) {
        this.packingprice = packingprice;
    }

    public double getUserActualFee() {
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

    public double getUrgentFee() {
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

    public double getInsuredFee() {
        return insuredFee;
    }

    public void setInsuredFee(int insuredFee) {
        this.insuredFee = insuredFee;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getPostmanProfit() {
        return postmanProfit;
    }

    public void setPostmanProfit(int postmanProfit) {
        this.postmanProfit = postmanProfit;
    }

    public double getCompnayProfit() {
        return compnayProfit;
    }

    public void setCompnayProfit(int compnayProfit) {
        this.compnayProfit = compnayProfit;
    }

    public double getPlatformProfit() {
        return platformProfit;
    }

    public void setPlatformProfit(int platformProfit) {
        this.platformProfit = platformProfit;
    }

    public double getShopProfit() {
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

    public double getShopUserDistance() {
        return shopUserDistance;
    }

    public void setShopUserDistance(double shopUserDistance) {
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

    public double getDiscountCost() {
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

    public String getStartHouseNumber() {
        return startHouseNumber;
    }

    public void setStartHouseNumber(String startHouseNumber) {
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

    public Object getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(Object receiverName) {
        this.receiverName = receiverName;
    }

    public Object getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(Object receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public Object getEndPro() {
        return endPro;
    }

    public void setEndPro(Object endPro) {
        this.endPro = endPro;
    }

    public Object getEndCity() {
        return endCity;
    }

    public void setEndCity(Object endCity) {
        this.endCity = endCity;
    }

    public Object getEndDistrict() {
        return endDistrict;
    }

    public void setEndDistrict(Object endDistrict) {
        this.endDistrict = endDistrict;
    }

    public Object getEndStreet() {
        return endStreet;
    }

    public void setEndStreet(Object endStreet) {
        this.endStreet = endStreet;
    }

    public Object getEndHouseNumber() {
        return endHouseNumber;
    }

    public void setEndHouseNumber(Object endHouseNumber) {
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

    public String getMealcode() {
        return mealcode;
    }

    public void setMealcode(String mealcode) {
        this.mealcode = mealcode;
    }

    public String getMealnumber() {
        return mealnumber;
    }

    public void setMealnumber(String mealnumber) {
        this.mealnumber = mealnumber;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
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
