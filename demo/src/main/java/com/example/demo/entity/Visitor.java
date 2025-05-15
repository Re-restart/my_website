package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import java.time.LocalDateTime;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Version;

@Entity
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version  // 添加乐观锁版本字段
    private Integer version;
    private String ipAddress;
    private LocalDateTime visitTime;

    // 需要添加setter方法
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }
}
