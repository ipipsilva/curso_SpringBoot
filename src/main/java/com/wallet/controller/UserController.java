package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.response.Response;
import com.wallet.service.UserService;
import com.wallet.util.Bcrypt;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Response<UserDTO>> create(@Valid @RequestBody UserDTO dto, BindingResult result) {

		Response<UserDTO> response = new Response<UserDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		User usuarioSalvo = userService.save(this.convertUserDtoToEntity(dto));

		response.setData(this.convertEntityToDto(usuarioSalvo));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private User convertUserDtoToEntity(UserDTO usuarioDTO) {
		return new User(usuarioDTO.getEmail(), usuarioDTO.getNome(), Bcrypt.getHash(usuarioDTO.getSenha()));
	}

	private UserDTO convertEntityToDto(User usuario) {
		return new UserDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), null);
	}
}