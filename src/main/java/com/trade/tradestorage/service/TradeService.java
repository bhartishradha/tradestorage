package com.trade.tradestorage.service;

import com.trade.tradestorage.dao.TradeDao;
import com.trade.tradestorage.dao.TradeRepository;
import com.trade.tradestorage.model.TradeModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	TradeDao tradeDao;

	@Autowired
	TradeRepository tradeRepository;

	public boolean isValid(TradeModel trade) {
		if (validateMaturityDate(trade)) {
			Optional<TradeModel> exsitingTrade = tradeRepository.findById(trade.getTradeId());
			if (exsitingTrade.isPresent()) {
				return validateVersion(trade, exsitingTrade.get());
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param trade
	 * @param oldTrade Test 1: During transmission if the lower version is being
	 *                 received by the store it will reject the trade and throw an
	 *                 exception. If the version is same it will override the
	 *                 existing record.
	 * @return
	 */
	private boolean validateVersion(TradeModel trade, TradeModel oldTrade) {

		if (trade.getVersion() >= oldTrade.getVersion()) {
			return true;
		}
		return false;
	}

	/**
	 * Test2: Store should not allow the trade which has less maturity date then
	 * today date.
	 * 
	 * @param trade
	 * @return
	 */
	private boolean validateMaturityDate(TradeModel trade) {
		return trade.getMaturityDate().isBefore(LocalDate.now()) ? false : true;
	}

	public void persist(TradeModel trade) {
		trade.setCreatedDate(LocalDate.now());
		tradeRepository.save(trade);
	}

	public List<TradeModel> findAll() {
		return tradeRepository.findAll();
	}

	public void updateExpiryFlagOfTrade() {
		tradeRepository.findAll().stream().forEach(t -> {
			if (!validateMaturityDate(t)) {
				t.setExpiredFlag("Y");
				log.info("Trade which needs to updated {}", t);
				tradeRepository.save(t);
			}
		});
	}

}
