package com.incapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.incapp.beans.Movie;
import com.incapp.beans.MyUser;
import com.incapp.repo.MovieRepo;

@Service
public class MovieService {
	@Autowired
	MovieRepo repo;
	
	public Movie getMovie(String name){
		return repo.getMovie(name);
	}
	
	public List<Movie> getMovies(){
		return repo.getMovies();
	}
	
	public List<Movie> getMovies(String name){
		return repo.getMovies(name);
	}

	public String addMovie(Movie m) {
		return repo.addMovie(m);
	}
	
	public String addMovie(Movie m,MultipartFile image) {
		return repo.addMovie(m,image);
	}
		
	public boolean deleteMovie(String name) {
		return repo.deleteMovie(name);
	}
	
	public String doUpdateMovie(Movie m,String oldName) {
		return repo.doUpdateMovie(m,oldName);
	}
	
	public String doUpdateMovie(Movie m,MultipartFile image,String oldName) {
		return repo.doUpdateMovie(m,image,oldName);
	}
	
	public String movieRating(String rating,String name){
		return repo.movieRating(rating,name);
	}
	
	public byte[] getMovieImage(String name){
		return repo.getMovieImage(name);
	}
	
	public MyUser findByUsername(String username) {
		return repo.findByUsername(username);
	}
	
	public String signUpDetails(MyUser user) {
		return repo.signUpDetails(user);
	}
		
}
