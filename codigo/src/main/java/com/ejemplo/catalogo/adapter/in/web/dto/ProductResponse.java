package com.ejemplo.catalogo.adapter.in.web.dto;

import java.math.BigDecimal;

// What the API RETURNS to the client. Field names are identical to the
// layered project so Postman and requests.http work unchanged.
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {
}

