package com.sam.multipartFileUpload.book.service;

import com.sam.multipartFileUpload.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> getBookDetails();

    List<Book> postMultipartBookDetail(MultipartFile file) throws IOException;
}
