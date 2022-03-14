package com.example.walletapi.services;


import com.example.walletapi.dto.*;
import com.example.walletapi.models.*;
import com.example.walletapi.repositories.CustomerRepository;
import com.example.walletapi.repositories.WalletRepository;
import com.example.walletapi.security.MyUserDetailService;
import com.example.walletapi.security.MyUserDetails;
import com.example.walletapi.services.exception.types.ResourceNotFound;
import com.example.walletapi.util.AuthenticationRequest;
import com.example.walletapi.util.AuthenticationResponse;
import com.example.walletapi.util.JwtUtil;
import java.awt.color.ProfileDataException;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{
    //generating a random alphanumerical string for the wallet to start and end with
    private static final Random random = new Random();
    private static final int leftLimit = 48;
    private static final int rightLimit = 122;
    private static final int targetStringLength = 2;


    private static final String generatedString = random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(targetStringLength)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();


    private final EmailValidator emailValidator;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;
    private WalletResponseDto walletResponseDto;
    private final MyUserDetailService userDetailsService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final WalletRepository walletRepository;

    public CustomerServiceImpl(EmailValidator emailValidator, ModelMapper modelMapper, AuthenticationManager authenticationManager, JwtUtil jwtUtils, MyUserDetailService userDetailsService, CustomerRepository customerRepository, PasswordEncoder bCryptPasswordEncoder, WalletRepository walletRepository) {
        this.emailValidator = emailValidator;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.walletRepository = walletRepository;
    }

    // find users by email
    @Override
    public CustomerResponse getWalletUsers(String email){
        var customer = customerRepository.findCustomersByEmail(email);
        return CustomerResponse.builder().message("Customer info").fullName(customer.getFullName()).acc(customer.getWallets().getAcc())
                .email(customer.getEmail()).build();
    }
// customer registration and wallet creation
    @Override
    public CustomerResponse register(CustomerDto personRequest) throws IOException {
        boolean isValidEmail = emailValidator.test(personRequest.getEmail());
        if(!isValidEmail){
            return CustomerResponse.builder().message("Not a valid email").build();
        }

        boolean userExists = customerRepository.findByEmail(personRequest.getEmail()).isPresent();
        if(userExists){
            return CustomerResponse.builder().message("email taken").build();
        }

        boolean userNameExists = customerRepository.findByUserName(personRequest.getUserName()).isPresent();
        if(userNameExists){
            return CustomerResponse.builder().message("userName is taken").build();
        }


        Customer person = new Customer();
        modelMapper.map(personRequest, person);

        final String encodedPassword = bCryptPasswordEncoder.encode(personRequest.getPassword());
        person.setPassword(encodedPassword);
        person.setLevel(KycLevel.NORMAL);
        Wallet wallet = new Wallet();
        wallet.setCurrency(Currency.NAIRA_NGN);
        wallet.setName(person.getFullName());
        wallet.setAcc(generateAcc());
        Wallet savedWallet = walletRepository.save(wallet);
        person.setWallets(savedWallet);
        customerRepository.save(person);
        return CustomerResponse.builder().fullName(person.getFullName()).acc(savedWallet.getAcc())
                .email(person.getEmail()).message("Successful").build();
    }

/* using nanoTime to generate digits for the wallet acc
 and appending it to the random string already created */
    private static String generateAcc() {
        String n = generatedString + System.nanoTime() + generatedString + "";
        return n.substring(0, 10);
    }
// customer login business logic
    @Override
    public ResponseEntity<AuthenticationResponse> loginUser(AuthenticationRequest req) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(),
                    req.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final MyUserDetails person = userDetailsService.loadUserByUsername(req.getUsername());
            List<String> roles = person.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            log.info("{} {}", roles.size(),roles);

            final String jwt = jwtUtils.generateToken(person);
            final AuthenticationResponse res = new AuthenticationResponse();

            String role =null;
            for (String r : roles) {
                if (r!=null) role = r;
            }
            res.setJwt(jwt);
            res.setRole(role);
            return ResponseEntity.ok().body(res);
            } catch (Exception e) {
            throw new Exception("incorrect username or password!", e);
        }
    }
// get profile of the current logged in user
    @Override
    public CustomerResponse getInfo(Authentication auth) {
        Customer person = customerRepository.findByUserName(auth.getName())
                .orElseThrow(()-> new ResourceNotFound("Person Not Found"));
        System.out.println(person.toString());
        CustomerResponse personInfoResponse = CustomerResponse.builder().message("your wallet profile")
                .fullName(person.getFullName()).acc(person.getWallets().getAcc())
                .email(person.getEmail()).build();
        modelMapper.map(person, personInfoResponse);
        return personInfoResponse;

    }

    public String getUserNameFromSecurityContext(){
        MyUserDetails loggedInuser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  loggedInuser.getUsername();
   }
   //using spring security to get the principal
    public Customer getUserFromSecurityContext(){
       Customer customer = customerRepository.findByUserName(getUserNameFromSecurityContext())
               .orElseThrow(() -> new ProfileDataException("customer not found"));
        return  customer;
    }
}
