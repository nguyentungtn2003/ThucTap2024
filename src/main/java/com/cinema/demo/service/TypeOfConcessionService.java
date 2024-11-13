package com.cinema.demo.service;

import com.cinema.demo.entity.TypeOfConcessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TypeOfConcessionService {
    Page<TypeOfConcessionEntity> getAllConcessionTypes(Pageable pageable, String searchKeyword);
    TypeOfConcessionEntity getConcessionTypeById(int id);
    void updateConcessionTypePrice(int id, int price);
    void deleteConcessionType(int id);
    void saveConcessionType(TypeOfConcessionEntity type);

    void updateConcessionType(TypeOfConcessionEntity type);
}
