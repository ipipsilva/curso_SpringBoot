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

import com.wallet.dto.WalletDTO;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.WalletService;

@RestController
@RequestMapping("wallet")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@PostMapping
	public ResponseEntity<Response<WalletDTO>> create(@Valid @RequestBody WalletDTO wallet, BindingResult result) {

		Response<WalletDTO> response = new Response<WalletDTO>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(erro -> response.getErrors().add(erro.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		response.setData(converteWalletEntityToDto(walletService.save(converteWalletDtoToEntity(wallet))));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private Wallet converteWalletDtoToEntity(WalletDTO dto) {
		Wallet wallet = new Wallet();
		wallet.setNome(dto.getNome());
		wallet.setValue(dto.getValue());
		return wallet;
	}

	private WalletDTO converteWalletEntityToDto(Wallet wallet) {
		WalletDTO walletDTO = new WalletDTO();

		walletDTO.setId(wallet.getId());
		walletDTO.setNome(wallet.getNome());
		walletDTO.setValue(wallet.getValue());

		return walletDTO;
	}
}