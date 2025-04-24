package com.auction.service;

import com.auction.model.Lot;
import com.auction.model.LotStatus;
import com.auction.repository.LotRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LotService {
    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public Lot createLot(Lot lot) {
        lot.setStatus(LotStatus.ACTIVE);
        lot.setCurrentBid(lot.getStartingPrice());
        return lotRepository.save(lot);
    }

    public Optional<Lot> getLotById(Long id) {
        return lotRepository.findById(id);
    }

    public Lot updateLot(Lot lot) {
        return lotRepository.save(lot);
    }

    public void deleteLot(Long id) {
        lotRepository.deleteById(id);
    }

    public List<Lot> getActiveLots() {
        return lotRepository.findByStatus(LotStatus.ACTIVE);
    }
}