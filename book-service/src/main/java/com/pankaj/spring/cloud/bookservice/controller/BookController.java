package com.pankaj.spring.cloud.bookservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pankaj.spring.cloud.bookservice.Book;
import com.pankaj.spring.cloud.common.auth.BaseAuthController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/protected/book")
public class BookController extends BaseAuthController {

	private List<Book> bookList = Arrays.asList(new Book(1L, "Baeldung goes to the market", "Tim Schimandle"),
			new Book(2L, "Baeldung goes to the park", "Slavisa"));

	@ApiOperation(value = "View a list of available books", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("all")
	public List<Book> findAllBooks() {
		return bookList;
	}

	@ApiOperation(value = "View a specific book", response = Book.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@GetMapping("/{bookId}")
	public Book findBook(@PathVariable Long bookId) {
		return bookList.stream().filter(b -> b.getId().equals(bookId)).findFirst().orElse(null);
	}

}
