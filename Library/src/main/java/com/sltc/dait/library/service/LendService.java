package com.sltc.dait.library.service;

import com.sltc.dait.library.model.Book;
import com.sltc.dait.library.model.Lend;

import java.util.Date;
import java.util.List;

public interface LendService {
    public void addLend(Lend lend);

    List<Lend> getOverdueLendings(Date byDate);
}
