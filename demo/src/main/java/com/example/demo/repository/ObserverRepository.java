package com.example.demo.repository;
import com.example.demo.entity.Observer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObserverRepository extends JpaRepository<Observer, Long> {
}
