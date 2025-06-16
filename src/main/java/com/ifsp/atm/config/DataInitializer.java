package com.ifsp.atm.config;

import com.ifsp.atm.model.Category;
import com.ifsp.atm.model.Currency;
import com.ifsp.atm.model.User;
import com.ifsp.atm.repository.CategoryRepository;
import com.ifsp.atm.repository.CurrencyRepository;
import com.ifsp.atm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private CurrencyRepository currencyRepository;
    
    @Autowired
    private AuthService authService;
    
    @Override
    public void run(String... args) throws Exception {
        initializeCurrencies();
        initializeCategories();
        initializeUsers();
    }
    
    private void initializeCurrencies() {
        if (currencyRepository.count() == 0) {
            currencyRepository.save(new Currency(null, "BRL", "Real Brasileiro", "R$"));
            currencyRepository.save(new Currency(null, "USD", "Dólar Americano", "$"));
            currencyRepository.save(new Currency(null, "EUR", "Euro", "€"));
        }
    }
    
    private void initializeCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Alimentação", "Gastos com comida e bebida", "#FF6B6B"));
            categoryRepository.save(new Category(null, "Transporte", "Gastos com transporte", "#4ECDC4"));
            categoryRepository.save(new Category(null, "Saúde", "Gastos com saúde e medicamentos", "#45B7D1"));
            categoryRepository.save(new Category(null, "Educação", "Gastos com educação", "#96CEB4"));
            categoryRepository.save(new Category(null, "Lazer", "Gastos com entretenimento", "#FFEAA7"));
            categoryRepository.save(new Category(null, "Moradia", "Gastos com moradia", "#DDA0DD"));
            categoryRepository.save(new Category(null, "Vestuário", "Gastos com roupas", "#98D8C8"));
            categoryRepository.save(new Category(null, "Outros", "Outros gastos", "#F7DC6F"));
        }
    }
    
    private void initializeUsers() {
        try {
            // Admin PJ
            User adminPJ = new User();
            adminPJ.setUsername("admin_pj");
            adminPJ.setPassword("123456");
            adminPJ.setEmail("admin.pj@teste.com");
            adminPJ.setUserType(User.UserType.ADMIN);
            adminPJ.setPersonType(User.PersonType.PJ);
            adminPJ.setCnpj("12345678000195");
            adminPJ.setCompanyName("Empresa Admin LTDA");
            authService.registerUser(adminPJ);
            
            // Premium CLT
            User premiumCLT = new User();
            premiumCLT.setUsername("premium_clt");
            premiumCLT.setPassword("123456");
            premiumCLT.setEmail("premium.clt@teste.com");
            premiumCLT.setUserType(User.UserType.PREMIUM);
            premiumCLT.setPersonType(User.PersonType.CLT);
            premiumCLT.setCpf("12345678901");
            premiumCLT.setFullName("João Premium Silva");
            authService.registerUser(premiumCLT);
            
            // Free CLT
            User freeCLT = new User();
            freeCLT.setUsername("free_clt");
            freeCLT.setPassword("123456");
            freeCLT.setEmail("free.clt@teste.com");
            freeCLT.setUserType(User.UserType.FREE);
            freeCLT.setPersonType(User.PersonType.CLT);
            freeCLT.setCpf("98765432109");
            freeCLT.setFullName("Maria Free Santos");
            authService.registerUser(freeCLT);
            
        } catch (RuntimeException e) {
            // Usuários já existem
            System.out.println("Usuários de exemplo já existem");
        }
    }
}
