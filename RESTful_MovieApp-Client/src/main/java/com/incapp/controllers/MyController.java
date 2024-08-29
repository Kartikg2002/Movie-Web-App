package com.incapp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.incapp.beans.Movie;
import com.incapp.beans.MyUser;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyController {
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	RestTemplate restTemplate = new RestTemplate();
	String URL = "http://localhost:8787";
	

	@ModelAttribute
	public void commonValue(ModelMap m) {
		m.addAttribute("appName", "Movie Web App");
	}

	// home
	@RequestMapping(value = { "/", "home" })
	public String home(ModelMap mm) {
		String API = "/movies";
		List<Movie> m = restTemplate.getForObject(URL + API, List.class);
		mm.addAttribute("movies", m);
		return "home";
	}

	// add Movie with or without image
	@PostMapping("addMovie")
	public String addMovie(@ModelAttribute Movie m, @RequestPart("image") MultipartFile image, ModelMap mm) {

		if (image.getSize() != 0) {
			String API = "/movie_img";

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
			data.add("image", convert(image));
			data.add("movie", m);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity(data, header);

			ResponseEntity<String> result;
			result = restTemplate.postForEntity(URL + API, requestEntity, String.class);

//			if(result.getStatusCode() == HttpStatus.OK) {
//				m.addAttribute("result",b.getName()+" Added Succesfully");
//			}else {
//				m.addAttribute("result",b.getName()+" AlreadyExist");
//			}
			mm.addAttribute("result", m.getName() + " " + result.getBody());
		} else {
			String API = "/movie";

			String result = restTemplate.postForObject(URL + API, m, String.class);
			mm.addAttribute("result", m.getName() + " " + result);
		}
		return "AdminHome";
	}

	// view all movie
	@RequestMapping("viewMovies")
	public String viewMovies(ModelMap mm) {
		String API = "/movies";
		List<Movie> m = restTemplate.getForObject(URL + API, List.class);
		mm.addAttribute("movies", m);
		return "ViewMovies";
	}

	// search movie
	@GetMapping("searchMovie")
	public String searchMovie(String name, ModelMap mm) {
		String API = "/movies/" + name;
		List<Movie> m = restTemplate.getForObject(URL + API, List.class);
		mm.addAttribute("movies", m);
		return "ViewMovies";
	}

	// delete movie
	@PostMapping("deleteMovie")
	public String deleteMovie(String name, ModelMap mm) {
		String API = "/movie/" + name;
		HttpEntity<String> requestEntity = new HttpEntity<String>("");
		ResponseEntity<Boolean> r = restTemplate.exchange(URL + API, HttpMethod.DELETE, requestEntity, Boolean.class);

		if (r.getBody()) {
			mm.addAttribute("result", name + " Movie Deleted Successfully!");
		} else {
			mm.addAttribute("result", name + " Movie Does Not Exist!");
		}

		API = "/movies";
		List<Movie> m = restTemplate.getForObject(URL + API, List.class);
		mm.addAttribute("movies", m);
		return "ViewMovies";

	}

	// update web page
	@GetMapping("updateMovie")
	public String updateMovie(String name, ModelMap mm) {
		String API = "/movie/" + name;
		Movie m = restTemplate.getForObject(URL + API, Movie.class);
		mm.addAttribute("movie", m);
		return "UpdateMovie";
	}

//	update movie
	@PostMapping("doUpdateMovie")
	public String doUpdateMovie(@ModelAttribute Movie m, @RequestPart("image") MultipartFile image,
			@RequestParam("oldName") String oldName, ModelMap mm) {
		ResponseEntity<String> result;

		if (image.getSize() != 0) {
			String API = "/movie_img";

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
			data.add("movie", m);
			data.add("image", convert(image));
			data.add("oldName", oldName);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity(data, header);

			result = restTemplate.exchange(URL + API, HttpMethod.PUT, requestEntity, String.class);

//			if(result.getStatusCode() == HttpStatus.OK) {
//				m.addAttribute("result",b.getName()+" Added Succesfully");
//			}else {
//				m.addAttribute("result",b.getName()+" AlreadyExist");
//			}

			mm.addAttribute("result", m.getName() + " " + result.getBody());
		} else {
			String API = "/movie";

			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
			data.add("movie", m);
			data.add("oldName", oldName);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity(data, header);

			result = restTemplate.exchange(URL + API, HttpMethod.PUT, requestEntity, String.class);

			mm.addAttribute("result", m.getName() + " " + result.getBody());
		}

		if (result.getBody().contains("Already Exist!")) {
			String API = "/movie/" + oldName;
			m = restTemplate.getForObject(URL + API, Movie.class);
			mm.addAttribute("movie", m);
		}
		return "UpdateMovie";
	}

	// get movie image
	@GetMapping("getMovieImage")
	public void getMovieImage(String name, HttpServletResponse response) throws IOException {
		String API = "/movieImage/" + name;
		byte[] image = restTemplate.getForObject(URL + API, byte[].class);
		if (image == null) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("Movie.jpg");
			image = is.readAllBytes();
		}
		response.getOutputStream().write(image);
	}

	// admin web page
	@GetMapping("/adminHome")
	public String adminHome() {
		return "AdminHome";
	}

	// more details
	@PostMapping("moreDetails")
	public String moreDetails(@RequestParam("name") String name, ModelMap mm) {
		String API = "/movie/" + name;
		Movie m = restTemplate.getForObject(URL + API, Movie.class);
		mm.addAttribute("movie", m);
		return "moreDetail";
	}

	// movie rating
	@PostMapping("movieRating")
	public String movieRating(@RequestParam("rating") String rating, @RequestParam("name") String name, ModelMap mm) {
		String API = "/movieRating/" + rating + "/" + name;
		String result = restTemplate.getForObject(URL + API, String.class);

		API = "/movie/" + name;
		Movie m = restTemplate.getForObject(URL + API, Movie.class);

		mm.addAttribute("result", result);
		mm.addAttribute("movie", m);

		return "moreDetail";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signUp")
	public String signUp() {
		return "signUp";
	}
	
	@PostMapping("/signUp")
	public String signUp(MyUser user,ModelMap m) {
		String API = "/signUpDetails";
		user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypting password
        user.setEnabled(true); // Default to enabled
        user.setRole("ROLE_USER"); // Default role
        String result = restTemplate.postForObject(URL+API,user,String.class);
        m.addAttribute("result",result);
		return "login";
	}

	// convert MultipartFile to FileSystemResource
	public static FileSystemResource convert(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new FileSystemResource(convFile);
	}
	// end
}
