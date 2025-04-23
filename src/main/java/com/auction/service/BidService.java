package com.auction.service;

import com.auction.model.Bid;
import com.auction.model.Lot;
import com.auction.repository.BidRepository;
import com.auction.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private LotRepository lotRepository;

    @Transactional
    public Bid placeBid(Bid bid) {
        Lot lot = lotRepository.findById(bid.getLot().getId()).orElseThrow();
        if (lot.getStatus().equals("ENDED") || lot.getStatus().equals("CANCELLED")) {
            throw new IllegalStateException("Auction has ended or been cancelled.");
        }
        if (bid.getUser().getId().equals(lot.getOwner().getId())) {
            throw new IllegalArgumentException("Cannot bid on your own lot.");
        }
        if (bid.getBidAmount().compareTo(lot.getCurrentPrice().add(lot.getMinBidStep())) < 0) {
            throw new IllegalArgumentException("Bid amount too low.");
        }
        lot.setCurrentPrice(bid.getBidAmount());
        lotRepository.save(lot);
        return bidRepository.save(bid);
    }

    @Transactional(readOnly = true)
    public List<Bid> getBidsByLotId(Long lotId) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new EntityNotFoundException("Lot not found"));
        return bidRepository.findByLotOrderByBidTimeDesc(lot);
    }

    public List<Bid> getBidsByLot(Lot lot) {
        return bidRepository.findByLotOrderByBidTimeDesc(lot);
    }
}