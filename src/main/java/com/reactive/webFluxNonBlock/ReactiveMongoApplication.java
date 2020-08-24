package com.reactive.webFluxNonBlock;

import com.reactive.webFluxNonBlock.repository.UserRepository;
import com.reactive.webFluxNonBlock.model.Employee;
import com.reactive.webFluxNonBlock.model.User;
import com.reactive.webFluxNonBlock.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.stream.Stream;


@SpringBootApplication
//@EnableReactiveElasticsearchRepositories
public class ReactiveMongoApplication {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Bean
	CommandLineRunner employees(EmployeeRepository  employeeRepository) {

		return args -> {
			employeeRepository
					.deleteAll()
			.subscribe(null, null, () -> {

				Stream.of(new Employee("1",
						"Peter", "2300"),new Employee("2",
						"Sam", "13000"),new Employee("3",
						"Ryan", "20000"),new Employee("4",
						"Chris", "53000")
						)
						.forEach(employee -> {
				employeeRepository
						.save(employee)
						.subscribe(System.out::println);

						});

			})
			;
		};

	}


	public static void main(String[] args) {
		SpringApplication.run(ReactiveMongoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CommandLineRunner start(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			User user = new User("user", passwordEncoder.encode("user"));
			userRepository.save(user).subscribe();

			userRepository.findAll().log().subscribe(u -> log.info("user: {}", u));
		};
	}
}
