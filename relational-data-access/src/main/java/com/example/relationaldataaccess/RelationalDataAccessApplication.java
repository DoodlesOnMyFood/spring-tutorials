package com.example.relationaldataaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication {

	public static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Bean
	public CommandLineRunner runner(){
		return args -> {
			log.info("Creating Table");

			jdbcTemplate.execute("DROP TABLE customer IF EXISTS");
			jdbcTemplate.execute("CREATE TABLE customer(" +
					"id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

			List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
					.map(name -> name.split(" "))
					.collect(Collectors.toList());

			splitUpNames.forEach(name -> log.info("Inserting record for {}, {}", name[0], name[1]));

			jdbcTemplate.batchUpdate("INSERT INTO customer(first_name, last_name) VALUES (?, ?)", splitUpNames);

			log.info("Querying for records where first name is 'Josh'");
			jdbcTemplate.query("SELECT id, first_name, last_name FROM customer where first_name = ?", new Object[]{"Josh"},
					(rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name")))
				.forEach(customer -> log.info(customer.toString()));
		};
	}

}
