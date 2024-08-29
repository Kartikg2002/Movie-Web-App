package com.incapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.incapp.beans.Movie;
import com.incapp.beans.MyUser;
import com.incapp.service.MovieService;

@RestController
public class MovieController {
	@Autowired
	MovieService service;

	@RequestMapping("/")
	public String homePage() {
		return "Welcome to Movie RESTful Web Service";
	}

	// get movie by name
	@GetMapping("/movie/{name}")
	public Movie getMovie(@PathVariable("name") String name) {
		return service.getMovie(name);
	}

	// get all movies
	@GetMapping("/movies")
	public List<Movie> getMovies() {
		return service.getMovies();
	}

	// get all movies by name (like query)
	@GetMapping("/movies/{name}")
	public List<Movie> getMovies(@PathVariable("name") String name) {
		return service.getMovies(name);
	}

	// add movie
	@PostMapping(value = "/movie")
	public String addMovie(@RequestBody Movie m) {
		return service.addMovie(m);
	}

	// add movie with image
	@PostMapping(value = "/movie_img")
	public String addMovie(@RequestPart("movie") Movie m, @RequestPart("image") MultipartFile image) {
		return service.addMovie(m, image);
	}

	// delete the movie by name
	@DeleteMapping("/movie/{name}")
	public boolean deleteMovie(@PathVariable("name") String name) {
		return service.deleteMovie(name);
	}

	// update the movie
	@PutMapping("/movie")
	public String updateMovie(@RequestPart("movie") Movie m, @RequestPart("oldName") String oldName) {
		return service.doUpdateMovie(m, oldName);
	}

	// update the movie with image
	@PutMapping(value = "/movie_img")
	public String addMovie(@RequestPart("movie") Movie m, @RequestPart("image") MultipartFile image,
			@RequestPart("oldName") String oldName) {
		return service.doUpdateMovie(m, image, oldName);
	}

	// rate the movie by name
	@GetMapping("/movieRating/{rating}/{name}")
	public String movieRating(@PathVariable("rating") String rating, @PathVariable("name") String name) {
		return service.movieRating(rating, name);
	}

	// get movie image by name
	@GetMapping("/movieImage/{name}")
	public byte[] getMovieImage(@PathVariable("name") String name) {
		return service.getMovieImage(name);
	}

	//authentication
	@GetMapping("/findByUsername/{username}")
	public MyUser findByUsername(@PathVariable("username") String username) {
		return service.findByUsername(username);
	}
	
	//signUpDetails
	@PostMapping("/signUpDetails")
	public String signUpDetails(@RequestBody MyUser user){
		return service.signUpDetails(user);	
	}

}
