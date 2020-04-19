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

import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;

@RestController
@RequestMapping("user-wallet")
public class UserWalletController {

	@Autowired
	private UserWalletService service;

	@PostMapping
	public ResponseEntity<Response<UserWalletDTO>> create(@Valid @RequestBody UserWalletDTO dto, BindingResult result) {

		Response<UserWalletDTO> response = new Response<UserWalletDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setData(convertToDto(service.save(convertToEntity(dto))));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private UserWallet convertToEntity(UserWalletDTO dto) {

		User user = new User();
		user.setId(dto.getUsers());

		Wallet wallet = new Wallet();
		wallet.setId(dto.getWallet());

		UserWallet userWallet = new UserWallet();

		userWallet.setUsers(user);
		userWallet.setWallet(wallet);

		return userWallet;
	}

	private UserWalletDTO convertToDto(UserWallet userWallet) {

		UserWalletDTO dto = new UserWalletDTO();

		dto.setId(userWallet.getId());
		dto.setUsers(userWallet.getUsers().getId());
		dto.setWallet(userWallet.getWallet().getId());

		return dto;
	}
}
