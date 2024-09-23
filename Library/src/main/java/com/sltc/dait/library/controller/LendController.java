package com.sltc.dait.library.controller;

import com.sltc.dait.library.model.Book;
import com.sltc.dait.library.model.Lend;
import com.sltc.dait.library.repository.LendRepo;
import com.sltc.dait.library.service.EmailService;
import com.sltc.dait.library.service.LendService;
import com.sltc.dait.library.service.LendServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class LendController {
    private final LendRepo lendRepo;
    private final LendService lendService;
    private final EmailService mailService;

    @GetMapping("/lendings")
    public List<Lend> getLendings(){
        return lendRepo.findAll();
    }

    @GetMapping("/lendings/overdue")
    public List<Lend> getOverdues(){
        return lendService.getOverdueLendings(new Date());
    }

    @PostMapping("/lendings")
    public void addLending(@RequestBody Lend lend){
        lendService.addLend(lend);
    }

}
