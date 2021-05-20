package com.trade.tradestorage.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.trade.tradestorage.model.TradeModel;

public interface TradeDao {


    public  static Map<String,TradeModel> tradeMap =new ConcurrentHashMap<>();
    void save(TradeModel trade);
    List<TradeModel> findAll();
    TradeModel findTrade(String tradeId);
}
