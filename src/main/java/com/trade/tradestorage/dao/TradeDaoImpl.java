package com.trade.tradestorage.dao;

import org.springframework.stereotype.Repository;

import com.trade.tradestorage.model.TradeModel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TradeDaoImpl implements TradeDao{
    @Override
    public void save(TradeModel trade) {
        trade.setCreatedDate(LocalDate.now());
        tradeMap.put(trade.getTradeId(),trade);
    }

    @Override
    public List<TradeModel> findAll() {
         return tradeMap.values().stream().
                 collect(Collectors.toList());
    }

    @Override
    public TradeModel findTrade(String tradeId) {
        return tradeMap.get(tradeId);
    }
}
