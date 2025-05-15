package com.example.demo.controller;

import com.example.demo.entity.Observer;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.repository.ObserverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.entity.Visitor;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final VisitorRepository visitorRepo;
    private final ObserverRepository observerRepo;


    @Autowired
    public HomeController(VisitorRepository visitorRepo, ObserverRepository observerRepo) {
        this.visitorRepo = visitorRepo;
        this.observerRepo = observerRepo;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        // 记录访问者
        Visitor visitor = new Visitor();
        visitor.setIpAddress(request.getRemoteAddr());
        visitor.setVisitTime(LocalDateTime.now());
        visitorRepo.save(visitor);

        model.addAttribute("visitorCount", visitorRepo.count());
        return "index";
    }

    @GetMapping("/guestbook")
    public String guestbook(Model model) {
        // 统计留言数量
        model.addAttribute("observerCount", observerRepo.count());
        model.addAttribute("messages", observerRepo.findAll());
        return "guestbook";
    }

    @PostMapping("/guestbook/add")
    public String submitMessage(
            @RequestParam String nickname,
            @RequestParam String message,
            HttpServletRequest request) {
        Observer observer = new Observer();
        observer.setObserveTime(LocalDateTime.now());
        observer.setNickname(nickname);
        observer.writeMessage(message); // 使用正确的setter方法
        observer.setIpAddress(request.getRemoteAddr()); // 添加IP记录
        observer.setMessage(true); // 设置留言标记

        observerRepo.save(observer);
        return "redirect:/guestbook";
    }

}
