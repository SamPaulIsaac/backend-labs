package com.sam.multipartFileUpload.book.service.impl;

import com.sam.multipartFileUpload.book.config.SpringBootMultipartFileUploadFeature;
import com.sam.multipartFileUpload.book.dto.BookDto;
import com.sam.multipartFileUpload.book.entity.Book;
import com.sam.multipartFileUpload.book.repository.BookRepository;
import com.sam.multipartFileUpload.book.service.BookService;
import com.sam.springBootMultipartFileUpload.book.utils.CsvUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    @Override
    public List<Book> getBookDetails() {
        log.info("Invoked getBookDetails in Book Service Impl");
        return bookRepository.findAll();
    }

    @Override
    public List<Book> postMultipartBookDetail(MultipartFile file) {
        try {
            List<BookDto> bookDtoList = new ArrayList<>();
            log.info("Invoked postMultipartBookDetail in Book Service Impl");

            if (SpringBootMultipartFileUploadFeature.CSV_READER.isActive()) {
                log.info("Togglz feature => CSV UTILS is enabled.");
                bookDtoList = readAndSaveUsingCsvUtils(file);
            } else if (SpringBootMultipartFileUploadFeature.INPUT_STREAM.isActive()) {
                log.info("Togglz feature => FILE INPUT STREAM is enabled.");
                bookDtoList = readAndSaveUsingInputStream(file);
            }
            List<Book> books = bookDtoList.stream().map(dto -> modelMapper.map(dto, Book.class))
                    .collect(Collectors.toList());
            return bookRepository.saveAll(books);

        } catch (IOException exception) {
            throw new RuntimeException("An error occurred during postMultipartBookDetail: " + exception.getLocalizedMessage());
        }
    }

    private List<BookDto> readAndSaveUsingCsvUtils(MultipartFile file) throws IOException {
        log.info("Invoked readAndSaveUsingCsvUtils in Book Service Impl.");
        return CsvUtils.read(BookDto.class, file.getInputStream());
    }

    private List<BookDto> readAndSaveUsingInputStream(MultipartFile file) throws IOException {
        log.info("Invoked readAndSaveUsingInputStream in Book Service Impl.");
        try (BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));

             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.builder()
                             .setHeader()
                             .setSkipHeaderRecord(true)
                             .build())
        ) {

            List<BookDto> bookDtoList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                BookDto bookDto = new BookDto(
                        csvRecord.get("Name"),
                        csvRecord.get("Author"),
                        Double.parseDouble(csvRecord.get("Price")),
                        Integer.parseInt(csvRecord.get("PublishedYear")));
                bookDtoList.add(bookDto);
            }
            return bookDtoList;
        }
    }
}
