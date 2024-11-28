package com.cinema.demo.booking_apis.services;

import com.cinema.demo.entity.ConcessionOrderEntity;

public interface IConcessionOrderService {
    ConcessionOrderEntity saveConcessionOrder(ConcessionOrderEntity concessionOrder);
}
