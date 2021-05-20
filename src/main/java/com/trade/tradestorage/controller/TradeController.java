package com.trade.tradestorage.controller;

import com.trade.tradestorage.exception.InvalidTradeException;
import com.trade.tradestorage.model.TradeModel;
import com.trade.tradestorage.service.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradeController {
    @Autowired
    TradeService tradeService;
   
    @PostMapping("/trade")
    public ResponseEntity<String> tradeValidateStore(@RequestBody TradeModel trade){
       if(tradeService.isValid(trade)) {
           tradeService.persist(trade);
       }else{
          
           throw new InvalidTradeException(trade.getTradeId()+"  Trade Id is not found");
       }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/trade")
    public List<TradeModel> findAllTrades(){
        return tradeService.findAll();
    }
}
