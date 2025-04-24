package com.auction.repository;

import com.auction.model.Bid;
import com.auction.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByLotOrderByTimestampDesc(Lot lot);
}