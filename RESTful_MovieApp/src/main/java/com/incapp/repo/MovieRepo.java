package com.incapp.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import com.incapp.beans.Movie;
import com.incapp.beans.MyUser;

@Repository
public class MovieRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// get Movie by name
	public Movie getMovie(String name) {
		class MovieMapper implements RowMapper {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setDuration(rs.getString("duration"));
				m.setReleaseDate(rs.getString("releasedate"));
				m.setCategory(rs.getString("category"));
				m.setDescription(rs.getString("description"));
				m.setRating(rs.getString("rating"));
				return m;
			}
		}
		try {
			final String query = "select * from movies where name = '" + name + "'";
			Movie m = (Movie) jdbcTemplate.queryForObject(query, new MovieMapper());
			return m;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	// get all Movies
	public List<Movie> getMovies() {
		class MovieMapper implements RowMapper {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setDuration(rs.getString("duration"));
				m.setReleaseDate(rs.getString("releasedate"));
				m.setCategory(rs.getString("category"));
				m.setDescription(rs.getString("description"));
				m.setRating(rs.getString("rating"));
				return m;
			}
		}
		try {
			final String query = "select * from movies";
			List<Movie> m = jdbcTemplate.query(query, new MovieMapper());
			return m;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	// get all Movies by name
	public List<Movie> getMovies(String name) {
		class MovieMapper implements RowMapper {
			public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
				Movie m = new Movie();
				m.setName(rs.getString("name"));
				m.setDuration(rs.getString("duration"));
				m.setReleaseDate(rs.getString("releasedate"));
				m.setCategory(rs.getString("category"));
				m.setDescription(rs.getString("description"));
				m.setRating(rs.getString("rating"));
				return m;
			}
		}
		try {
			final String query = "select * from movies where name like '%" + name + "%'";
			List<Movie> m = jdbcTemplate.query(query, new MovieMapper());
			return m;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	// add movie
	public String addMovie(Movie m) {
		try {
			String query = "insert into movies (name,duration,releasedate,category,description,rating) values(?,?,?,?,?,?)";
			jdbcTemplate.update(query, new Object[] { m.getName(), m.getDuration(), m.getReleaseDate(), m.getCategory(),
					m.getDescription(), "No" });
			return "Movie Added Successfully!";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Movie Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	// add movie with image
	public String addMovie(Movie m, MultipartFile image) {
		try {
			if (image.getSize() == 0) {
				String query = "insert into movies (name,duration,releasedate,category,description,rating) values(?,?,?,?,?,?)";
				jdbcTemplate.update(query, new Object[] { m.getName(), m.getDuration(), m.getReleaseDate(),
						m.getCategory(), m.getDescription(), "No" });
			} else {
				String query = "insert into movies values(?,?,?,?,?,?,?)";
				jdbcTemplate.update(query, new Object[] { m.getName(), m.getDuration(), m.getReleaseDate(),
						m.getCategory(), m.getDescription(), image.getInputStream(), "No" });
			}
			return "Movie Added Successfully!";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Movie Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	// delete movie by name
	public boolean deleteMovie(String name) {
		String query = "delete from movies where name = '" + name + "'";
		int x = jdbcTemplate.update(query);
		if (x != 0)
			return true;
		else
			return false;
	}

	// update the movie
	public String doUpdateMovie(Movie m, String oldName) {
		try {
			String query = "update movies set name=? , duration=? , releasedate=? , category=?, description=? where name=?";
			jdbcTemplate.update(query, new Object[] { m.getName(), m.getDuration(), m.getReleaseDate(), m.getCategory(),
					m.getDescription(), oldName });
			return "Movie Updated Successfully";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Movie Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	// update the movie with image
	public String doUpdateMovie(Movie m, MultipartFile image, String oldName) {
		try {
			String query = "update movies set name=? , duration=? , releasedate=? , category=?, description=?, movieposter=?  where name=?";
			jdbcTemplate.update(query, new Object[] { m.getName(), m.getDuration(), m.getReleaseDate(), m.getCategory(),
					m.getDescription(), image.getInputStream(), oldName });
			return "Movie Updated Successfully";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Movie Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	// rate the movie by name
	public String movieRating(String rating, String name) {
		try {
			String query = "update movies set rating=? where name=?";
			jdbcTemplate.update(query, new Object[] { rating, name });
			return "Your Rating Added Successfully";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Movie Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	// get image by name
	public byte[] getMovieImage(String name) {
		class MovieMapper implements RowMapper {
			public byte[] mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getBytes("movieposter");
			}
		}
		try {
			final String query = "select movieposter from movies where name = '" + name + "'";
			byte[] image = (byte[]) jdbcTemplate.queryForObject(query, new MovieMapper());
			return image;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	//findByUsername
	public MyUser findByUsername(String username) {
		String query = "SELECT * FROM users WHERE username = ?";
		class DataMapper implements RowMapper {
			public MyUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				MyUser user = new MyUser();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getBoolean("enabled"));
				user.setRole(rs.getString("role"));
				return user;
			}
		}
		try {
			MyUser u = (MyUser) jdbcTemplate.queryForObject(query, new DataMapper(), new Object[] { username });
			return u;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	//store sign up details
	public String signUpDetails(MyUser user) {
		try {
			String query = "INSERT INTO users (username, password, enabled, role) VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.isEnabled(), user.getRole());
			return "SignUp Details Added Successfully!";
		} catch (org.springframework.dao.DuplicateKeyException ex) {
			return "Username Already Exist!";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}
	
	
//	// admin is valid or not
//		public String adminLogin(String uname, String password) {
//			class MovieMapper implements RowMapper {
//				public LinkedHashMap<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
//					LinkedHashMap<String, String> loginDetail = new LinkedHashMap<String, String>();
//					loginDetail.put("uname", rs.getString("username"));
//					loginDetail.put("password", rs.getString("password"));
//					return loginDetail;
//				}
//			}
//			try {
//				final String query = "select * from users where username = '" + uname + "' AND password = '" + password
//						+ "' ";
//				List<LinkedHashMap<String, String>> loginDetail = jdbcTemplate.query(query, new MovieMapper());
//
//				for (LinkedHashMap<String, String> detail : loginDetail) {
//					if (detail.get("uname").contains(uname) && detail.get("password").contains(password)) {
//						return "Login SuccessFully";
//					}
//				}
//				return "Invalid Credentials !!!!!";
//			} catch (EmptyResultDataAccessException ex) {
//				return null;
//			}
//		}

}
