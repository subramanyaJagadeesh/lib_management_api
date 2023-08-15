package com.library_management.api.Controller;

import com.library_management.api.Model.Book;
import com.library_management.api.Model.User;
import com.library_management.api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/all")
    @Operation(summary = "Endpoint to fetch all users of a type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all users of one type",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Book.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Users found",
                    content = @Content) })
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) Integer category) {
        Optional<Integer> bookCategory;
        if(Objects.nonNull(category))
            bookCategory = Optional.of(category);
        else
            bookCategory = Optional.empty();
        return ResponseEntity.ok().body(bookService.getAllBooks(bookCategory));
    }

    @GetMapping("/:id")
    @Operation(summary = "Endpoint to fetch all books of a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all books of a category",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Book.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Users found",
                    content = @Content) })
    public ResponseEntity<Book> getBook(@RequestParam Long id) {
        return ResponseEntity.ok().body(bookService.getBook(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Endpoint to create a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a book",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Book.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> createBook(@RequestBody Book book){
        bookService.createBook(book.getTitle(), book.getAuthor(), book.getCategory(), book.getInventorySize(), book.getPublishedDate());
        return ResponseEntity.ok().body("User created successfully");
    }

    @PostMapping("/delete")
    @Operation(summary = "Endpoint to delete a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete a book",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Book.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> deleteBook(@RequestParam() Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().body("Book deleted successfully");
    }
}
