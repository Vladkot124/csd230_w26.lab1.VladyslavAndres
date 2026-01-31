package csd230.lab1.controllers;

import csd230.lab1.entities.OrderEntity;
import csd230.lab1.repositories.OrderEntityRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderEntityRepository orderRepo;

    public OrderController(OrderEntityRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Long id, Model model) {
        OrderEntity order = orderRepo.findById(id).orElseThrow();
        model.addAttribute("order", order);
        return "orderDetails";
    }
}
