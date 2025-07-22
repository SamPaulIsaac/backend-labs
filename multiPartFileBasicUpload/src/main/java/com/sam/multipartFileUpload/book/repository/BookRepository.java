package com.sam.multipartFileUpload.book.repository;

import com.sam.multipartFileUpload.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
