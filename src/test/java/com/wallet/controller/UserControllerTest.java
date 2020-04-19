package com.wallet.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

	private static final String SENHA = "12345678";
	private static final String NOME = "Igor Silva";
	private static final String EMAIL = "teste@teste.com.br";
	private static final String URL = "/user";
	private static final Long ID = 1l;
	private static final String EMAIL_INVALIDO = "teste.com.br";

	@MockBean
	private UserService service;

	@Autowired
	private MockMvc mvc;

	@Test
	public void testSave() throws Exception {

		BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());

		// @formatter:off
		 mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(null, NOME, EMAIL, SENHA))
				 .contentType(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON))
		 .andExpect(status().isCreated())
		 .andExpect(jsonPath("$.data.id").value(ID))
		 .andExpect(jsonPath("$.data.nome").value(NOME))
		 .andExpect(jsonPath("$.data.email").value(EMAIL))
		 .andExpect(jsonPath("$.data.senha").value(SENHA));
		// @formatter:on
	}

	@Test
	public void testSaveInvalidUser() throws JsonProcessingException, Exception {

		BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());

		// @formatter:off
		 mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(null, NOME, EMAIL_INVALIDO, SENHA))
				 .contentType(MediaType.APPLICATION_JSON)
				 .accept(MediaType.APPLICATION_JSON))
		 .andExpect(status().isBadRequest())
		 .andExpect(jsonPath("$.errors[0]").value("E-mail invalido!"));
		// @formatter:on
	}

	public User getMockUser() {
		User user = new User(EMAIL, NOME, SENHA);
		user.setId(ID);
		return user;
	}

	public String getJsonPayload(Long id, String nome, String email, String senha) throws JsonProcessingException {
		UserDTO usuarioDTO = new UserDTO(id, nome, email, senha);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(usuarioDTO);
	}
}