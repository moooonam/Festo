package com.example.festo.booth.application.port.out;

import com.example.festo.booth.adapter.in.web.model.FiestaResponse;

import java.util.List;

public interface LoadFiestaListPort {
    List<FiestaResponse.Owner> loadFiestaListByOwnerId(Long ownerId);
}
