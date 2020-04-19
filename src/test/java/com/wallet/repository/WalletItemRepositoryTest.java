package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class WalletItemRepositoryTest {

	private static final Date DATE = new Date();
	private static final TypeEnum TYPE = TypeEnum.EN;
	private static final String DESCRIPTION = "Conta de luz";
	private static final BigDecimal VALUE = BigDecimal.valueOf(107.25);

	private Long savedWalletItemId = null;
	private Long saveWalletId = null;

	@Autowired
	private WalletItemRepository repository;

	@Autowired
	private WalletRepository walletRepository;

	@BeforeAll
	public void setUp() {

		Wallet wallet = new Wallet();
		wallet.setNome("Carteira de teste");
		wallet.setValue(BigDecimal.valueOf(1000));

		walletRepository.save(wallet);

		WalletItem walletItem = new WalletItem(wallet, DATE, TYPE, DESCRIPTION, VALUE);

		repository.save(walletItem);

		saveWalletId = wallet.getId();
		savedWalletItemId = walletItem.getId();
	}

	@AfterAll
	public void tearDown() {
		repository.deleteAll();
		walletRepository.deleteAll();
	}

	@Test
	public void testSave() {

		Wallet wallet = new Wallet();
		wallet.setNome("Carteira 5");
		wallet.setValue(BigDecimal.valueOf(500));
		walletRepository.save(wallet);

		WalletItem walletItem = new WalletItem(wallet, DATE, TYPE, DESCRIPTION, VALUE);

		WalletItem response = repository.save(walletItem);

		assertNotNull(response);
		assertEquals(response.getDescription(), DESCRIPTION);
	}

	@Test
	public void testSaveInvalidWalletItem() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			WalletItem walletItem = new WalletItem(null, null, TYPE, null, VALUE);
			repository.save(walletItem);
		});
	}

	@Test
	public void testUpdateWalletItem() {

		Optional<WalletItem> walletItem = repository.findById(savedWalletItemId);
		String novaDescricao = "trocando descrição";

		WalletItem walletBanco = walletItem.get();

		walletBanco.setDescription(novaDescricao);

		repository.save(walletBanco);

		assertEquals(novaDescricao, repository.findById(savedWalletItemId).get().getDescription());
	}

	@Test
	public void testDeleteWalletItem() {
		Optional<Wallet> wallet = walletRepository.findById(saveWalletId);

		WalletItem walletItem = new WalletItem(wallet.get(), DATE, TYPE, DESCRIPTION, VALUE);

		repository.save(walletItem);

		repository.deleteById(walletItem.getId());

		Optional<Wallet> walletBusca = walletRepository.findById(walletItem.getId());

		assertFalse(walletBusca.isPresent());
	}
}