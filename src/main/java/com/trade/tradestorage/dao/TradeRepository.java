package com.trade.tradestorage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trade.tradestorage.model.TradeModel;

@Repository
public interface TradeRepository extends JpaRepository<TradeModel,String> {
}
