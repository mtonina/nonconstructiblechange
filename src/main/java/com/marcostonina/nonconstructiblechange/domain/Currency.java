package com.marcostonina.nonconstructiblechange.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.List;


public record Currency(

        @NotBlank(message = "Name must be defined")
        String name,

        @NotBlank(message = "Code must be defined")
        @Size(min = 2, max = 3, message = "Currency code should be between 2 and 3 characters")
        String code,

        @NotNull(message = "Coin list cannot be null")
        @Size(min = 1, message = "Coin list should have at least one value")
        List<Integer> coins
) {
}
