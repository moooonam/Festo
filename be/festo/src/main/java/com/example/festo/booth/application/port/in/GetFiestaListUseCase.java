package com.example.festo.booth.application.port.in;

import com.example.festo.booth.adapter.in.web.model.FiestaResponse;

import java.util.List;

public interface GetFiestaListUseCase {
    List<FiestaResponse.Owner> getFiestaListByOwner(Long ownerId);
}
