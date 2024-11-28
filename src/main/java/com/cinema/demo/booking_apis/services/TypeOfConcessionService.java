package com.cinema.demo.booking_apis.services;

import com.cinema.demo.booking_apis.repository.ITypeOfConcessionRepository;
import com.cinema.demo.entity.TypeOfConcessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfConcessionService implements ITypeOfConcessionService {
    @Autowired
    private ITypeOfConcessionRepository typeOfConcessionRepository;

    @Override
    public List<TypeOfConcessionEntity> getAllConcessions() {
        return typeOfConcessionRepository.findAll();
    }
}
