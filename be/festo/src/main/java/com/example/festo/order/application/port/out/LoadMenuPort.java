package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.Menu;

public interface LoadMenuPort {

    Menu loadMenu(Long menuId);
}
