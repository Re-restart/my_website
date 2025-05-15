package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Observer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;  // 仅在留言时填写
    @Column(columnDefinition = "TEXT")
    private String message;  // 留言内容
    private boolean isMessage; // 标记是否为留言记录
    private LocalDateTime observeTime;
    private String ipAddress;
    // 需要添加setter方法
    public void setObserveTime(LocalDateTime observeTime) {
        this.observeTime = observeTime;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void writeMessage(String message) {
        this.message = message;
    }
    public void setMessage(boolean isMessage) {
        this.isMessage = isMessage;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    // Getter方法
    public String getNickname() {
        return nickname;
    }
    public String getMessage() {
        return message;
    }
    public boolean setMessage() {
        return isMessage;
    }
    public LocalDateTime getObserveTime() {
        return observeTime;
    }
    public String getIpAddress() {
        return ipAddress;
    }

}
