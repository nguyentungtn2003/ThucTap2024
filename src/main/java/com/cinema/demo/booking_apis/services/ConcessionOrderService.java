package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.repository.IConcessionOrderRepository;
import com.cinema.demo.entity.ConcessionOrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConcessionOrderService implements IConcessionOrderService{
    @Autowired
    private IConcessionOrderRepository concessionOrderRepository;

    @Override
    public ConcessionOrderEntity saveConcessionOrder(ConcessionOrderEntity concessionOrder) {
        return concessionOrderRepository.save(concessionOrder);
    }
}
