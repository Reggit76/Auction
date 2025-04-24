package com.auction.service;

import com.auction.dto.BidRequest;
import com.auction.dto.BidResponse;
import com.auction.model.Bid;
import com.auction.model.Lot;
import com.auction.model.LotStatus;
import com.auction.model.User;
import com.auction.repository.BidRepository;
import com.auction.repository.LotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService {
    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    public BidService(BidRepository bidRepository, LotRepository lotRepository) {
        this.bidRepository = bidRepository;
        this.lotRepository = lotRepository;
    }

    @Transactional
    public BidResponse placeBid(BidRequest bidRequest) {
        Lot lot = lotRepository.findById(bidRequest.lotId())
                .orElseThrow(() -> new IllegalArgumentException("Lot not found"));

        validateBid(lot, bidRequest.userId(), bidRequest.bidAmount());

        Bid bid = new Bid();
        bid.setLot(lot);
        bid.setUser(new User(bidRequest.userId()));
        bid.setBidAmount(bidRequest.bidAmount());

        updateLotPrice(lot, bidRequest.bidAmount());
        Bid savedBid = bidRepository.save(bid);
        return convertToDto(savedBid);
    }

    private void validateBid(Lot lot, Long userId, BigDecimal bidAmount) {
        if (!lot.getStatus().equals(LotStatus.ACTIVE)) {
            throw new IllegalStateException("Lot is not active");
        }
        if (userId.equals(lot.getOwner().getId())) {
            throw new IllegalArgumentException("Cannot bid on your own lot");
        }
        BigDecimal minRequired = lot.getCurrentBid().add(lot.getMinBidStep());
        if (bidAmount.compareTo(minRequired) < 0) {
            throw new IllegalArgumentException("Minimum bid required: " + minRequired);
        }
    }

    private void updateLotPrice(Lot lot, BigDecimal newPrice) {
        lot.setCurrentBid(newPrice);
        lotRepository.save(lot);
    }

    @Transactional(readOnly = true)
    public List<BidResponse> getBidsByLotId(Long lotId) {
        Lot lot = lotRepository.findById(lotId)
                .orElseThrow(() -> new IllegalArgumentException("Lot not found"));
        return bidRepository.findByLotOrderByTimestampDesc(lot)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private BidResponse convertToDto(Bid bid) {
        return new BidResponse(
                bid.getId(),
                bid.getLot().getId(),
                bid.getUser().getId(),
                bid.getBidAmount(),
                bid.getTimestamp()
        );
    }
}