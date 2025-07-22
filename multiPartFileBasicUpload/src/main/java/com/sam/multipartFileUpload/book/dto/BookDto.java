package com.sam.multipartFileUpload.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @NotBlank(message = "Name is mandatory.")
    private String name;

    @NotBlank(message = "Author is mandatory.")
    private String author;

    @NotNull(message = "Price is mandatory.")
    @Min(value = 1, message = "Price must be between greater than 0")
    private Double price;

    @NotNull(message = "publishedYear.")
    @Min(value = 1, message = "Price must be between greater than 0")
    private Integer publishedYear;
}
