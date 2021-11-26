package com.cimspace.e_library.service;

import com.cimspace.e_library.book_properties.FileStorageException;
import com.cimspace.e_library.book_properties.MyFileNotFoundException;
import com.cimspace.e_library.book_properties.book_properties;
import com.cimspace.e_library.domain.Book;
import com.cimspace.e_library.domain.Category;
import com.cimspace.e_library.entity.AuthorDTO;
import com.cimspace.e_library.entity.BookDTO;
import com.cimspace.e_library.domain.Author;
import com.cimspace.e_library.entity.CategoryDTO;
import com.cimspace.e_library.repos.AuthorRepository;
import com.cimspace.e_library.repos.BookRepository;
import com.cimspace.e_library.repos.CategoryRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final Path bookstore;
    private String bookuri;
    private String bookextension;

    public BookService(final BookRepository bookRepository,book_properties book_properties,
            final CategoryRepository categoryRepository, final AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.bookstore = Paths.get(book_properties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    public String storeBook(MultipartFile file, String extension, String uri) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        bookextension = extension;
        this.bookuri = uri;

        Boolean correctextension = false;

        String[] mediatypes = {"doc","docx","epub","html","ibook",".pdb","txt","pdf"};

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            System.out.println(extension);

            for (String word : mediatypes){
                if (extension.equals(word)){
                    correctextension = true;
                }

            }
            System.out.println(extension);
            if (!correctextension){
                throw new FileUploadException("Sorry, extension is not accepted");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.bookstore.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource downloadBook(String fileName){
        try {
            Path filePath = this.bookstore.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(book -> mapToDTO(book, new BookDTO()))
                .collect(Collectors.toList());
    }
    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public BookDTO get(final String bookId) {
        return bookRepository.findById(bookId)
                .map(book -> mapToDTO(book, new BookDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String create(final BookDTO bookDTO) {
        final Book book = new Book();
        mapToEntity(bookDTO, book);
        return bookRepository.save(book).getBookId();
    }

    public void update(final String bookId, final BookDTO bookDTO) {
        final Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(bookDTO, book);
        bookRepository.save(book);
    }

    public void delete(final String bookId) {
        bookRepository.deleteById(bookId);
    }

    private BookDTO mapToDTO(final Book book, final BookDTO bookDTO) {
        bookDTO.setBookId(book.getBookId());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setDateOfPublication(book.getDateOfPublication());
        bookDTO.setBookuri(bookuri);
        bookDTO.setExtension(bookextension);
        bookDTO.setCategoryBooks(new CategoryDTO(book.getCategoryBooks().getCategoryId(), book.getCategoryBooks().getName()));
//        bookDTO.setBookAuthors(new AuthorDTO(book.getBookAuthors().get));
        bookDTO.setBookAuthors(book.getBookAuthors() == null ? null : book.getBookAuthors().stream()
                .map(author -> new AuthorDTO(author.getAuthorId(), author.getName()))
                .collect(Collectors.toSet()));
        return bookDTO;
    }

    private Book mapToEntity(final BookDTO bookDTO, final Book book) {
        book.setBookId(bookDTO.getBookId());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setDateOfPublication(bookDTO.getDateOfPublication());
        book.setBookUri(bookDTO.getBookuri());
        book.setBookextenstion(bookDTO.getExtension());
        if (bookDTO.getCategoryBooks() != null && (book.getCategoryBooks() == null || !book.getCategoryBooks().getCategoryId().equals(bookDTO.getCategoryBooks()))) {
            final Category categoryBooks = categoryRepository.findById(bookDTO.getCategoryBooks().getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "categoryBooks not found"));
            book.setCategoryBooks(categoryBooks);
        }
        if (bookDTO.getBookAuthors() != null) {
            final List<Author> bookAuthors =
                    authorRepository.findAllById(bookDTO.getBookAuthors().stream()
                            .map(authorDTO -> authorDTO.getAuthorId())
                            .collect(Collectors.toList()));
            if (bookAuthors.size() != bookDTO.getBookAuthors().size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of bookAuthors not found");
            }
            book.setBookAuthors(bookAuthors.stream().collect(Collectors.toSet()));
        }
        return book;
    }

    public List<BookDTO> getHomeBooks(){
        return findAll().stream().limit(8).collect(Collectors.toList());
    }

//    final private List<BookDTO> books = findAll();

//    public Page<BookDTO> findPaginated(Pageable pageable) {
//        int pageSize = pageable.getPageSize();
//        int currentPage = pageable.getPageNumber();
//        int startItem = currentPage * pageSize;
//        List<BookDTO> list;
//
//        if (books.size() < startItem) {
//            list = Collections.emptyList();
//        } else {
//            int toIndex = Math.min(startItem + pageSize, books.size());
//            list = books.subList(startItem, toIndex);
//        }
//
//        Page<BookDTO> bookPage = new PageImpl<BookDTO>(list, PageRequest.of(currentPage, pageSize), books.size());
//
//        return bookPage;
//    }

}
