package com.library_management.api.controller;

import com.library_management.api.dto.BorrowDTO;
import com.library_management.api.model.Borrow;
import com.library_management.api.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    BorrowService borrowService;

    @GetMapping("/all")
    @Operation(summary = "Endpoint to fetch all borrowings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all borrowings",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Borrow.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Borrows found",
                    content = @Content) })
    public List<BorrowDTO> getAllBorrowings(){
        return borrowService.getAllBorrowings();
    }
    
    @GetMapping("/title")
    @Operation(summary = "Endpoint to fetch all borrowings for a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all borrowings",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Borrow.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid book name",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Borrows found",
                    content = @Content) })
    public List<BorrowDTO> getBorrowingByBookName(@RequestParam String title){
        return borrowService.getBorrowingByBookTitle(title);
    }


    @GetMapping("/user/{id}")
    @Operation(summary = "Endpoint to fetch all borrowings for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all borrowings",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Borrow.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid userId",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Borrows found",
                    content = @Content) })
    public List<BorrowDTO> getBorrowingByUser(@PathVariable Long id){
        return borrowService.getBorrowingByUser(id);
    }

    @PostMapping("/create")
    @Operation(summary = "Endpoint to create a borrowing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "create a borrow",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Borrow.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> createBorrowing(@RequestBody Borrow borrow){
        borrowService.createBorrowing(borrow.getBookId(), borrow.getUserId(), borrow.getStartDate(), borrow.getEndDate());
        return ResponseEntity.ok().body("User created successfully");
    }

    @PostMapping("/delete")
    @Operation(summary = "Endpoint to delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete a borrowing",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = Borrow.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> deleteBorrowing(@RequestParam Long borrowId) {
        borrowService.deleteBorrowing(borrowId);
        return ResponseEntity.ok().body("User deleted successfully");
    }

}
