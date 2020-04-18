package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.User;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;
	
	private static final String EMAIL = "igor.silva@gmail.com";

	@BeforeAll
	public void setUp() {

		User usuario = new User();

		usuario.setNome("Teste de SetUp");
		usuario.setEmail(EMAIL);
		usuario.setSenha("12345678");

		repository.save(usuario);
	}

	@AfterAll
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void testSave() {

		User usuario = new User();

		usuario.setNome("Teste");
		usuario.setEmail("teste@teste.com");
		usuario.setSenha("12345678");

		User response = repository.save(usuario);

		assertNotNull(response);
	}
	
	@Test
	public void testFindByEmail() {
		Optional<User> user =  this.repository.findByEmailEquals(EMAIL);
		
		assertTrue(user.isPresent());
		assertEquals(user.get().getEmail(), EMAIL);
	}
}