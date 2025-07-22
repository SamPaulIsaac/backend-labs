package com.sam.multipartFileUpload.book.controller;


import com.sam.springBootMultipartFileUpload.book.entity.Book;
import com.sam.springBootMultipartFileUpload.book.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
@Slf4j
public class BookController {

    private BookService bookService;

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        log.info("Invoked getAllBooks in Book Controller.");
        return bookService.getBookDetails();
    }

    @PostMapping(path = "/save")
    public List<Book> postMultipartBookDetail(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("Invoked postMultipartBookDetail in Book Controller.");
        return bookService.postMultipartBookDetail(file);
    }

}
