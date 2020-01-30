package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Order;

public interface OrderRepository extends MongoRepository<Order,Integer> {

}
