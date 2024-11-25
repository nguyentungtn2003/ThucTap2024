package com.cinema.demo.service.impl;


import com.cinema.demo.entity.TypeOfConcessionEntity;
import com.cinema.demo.repository.TypeOfConcessionRepository;
import com.cinema.demo.service.TypeOfConcessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfConcessionServiceImpl implements TypeOfConcessionService {

    @Autowired
    private TypeOfConcessionRepository typeOfConcessionRepository;

    @Override
    public Page<TypeOfConcessionEntity> getAllConcessionTypes(Pageable pageable, String searchKeyword) {
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            return typeOfConcessionRepository.findByProductTypeContainingIgnoreCase(searchKeyword, pageable);
        }
        return typeOfConcessionRepository.findAll(pageable);
    }


    @Override
    public TypeOfConcessionEntity getConcessionTypeById(int id) {
        return typeOfConcessionRepository.findById(id).orElse(null);
    }

    @Override
    public void updateConcessionTypePrice(int id, int price) {
        TypeOfConcessionEntity type = getConcessionTypeById(id);
        if (type != null) {
            type.setPrice(price);
            typeOfConcessionRepository.save(type);
        }
    }

    @Override
    public void deleteConcessionType(int id) {
        typeOfConcessionRepository.deleteById(id);
    }

    @Override
    public void saveConcessionType(TypeOfConcessionEntity type) {
        typeOfConcessionRepository.save(type);
    }

    @Override
    public void updateConcessionType(TypeOfConcessionEntity type) {
        // Kiểm tra nếu ID tồn tại trong cơ sở dữ liệu
        if (type.getConcessionTypeId() != null) {
            // Tìm đối tượng TypeOfConcessionEntity bằng ID
            TypeOfConcessionEntity existingType = typeOfConcessionRepository.findById(type.getConcessionTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Concession type not found with id " + type.getConcessionTypeId()));

            // Cập nhật các thuộc tính cần thiết
            existingType.setProductType(type.getProductType());
            existingType.setQuantity(type.getQuantity());
            existingType.setPrice(type.getPrice());

            // Lưu lại đối tượng đã được cập nhật vào cơ sở dữ liệu
            typeOfConcessionRepository.save(existingType);
        }
    }
}
