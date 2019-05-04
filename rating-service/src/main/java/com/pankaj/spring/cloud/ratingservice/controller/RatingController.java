package com.pankaj.spring.cloud.ratingservice.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pankaj.spring.cloud.ratingservice.Rating;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	private List<Rating> ratingList = Arrays.asList(
	        new Rating(1L, 1L, 2),
	        new Rating(2L, 1L, 3),
	        new Rating(3L, 2L, 4),
	        new Rating(4L, 2L, 5)
	    );
	 
	@ApiOperation(value = "View a specific rating by book id", response = Rating.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	    @GetMapping("")
	    public List<Rating> findRatingsByBookId(@RequestParam Long bookId) {
	        return bookId == null || bookId.equals(0L) ? Collections.EMPTY_LIST : ratingList.stream().filter(r -> r.getBookId().equals(bookId)).collect(Collectors.toList());
	    }
	 
	@ApiOperation(value = "View all ratings", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	    @GetMapping("/all")
	    public List<Rating> findAllRatings() {
	        return ratingList;
	    }

}
