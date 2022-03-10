package com.example.walletapi.security;

import com.example.walletapi.models.Customer;
import com.example.walletapi.repositories.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    CustomerRepository customerRepository;

    @Autowired
    public MyUserDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findPersonByUserName(userName);
        customer.orElseThrow(()-> new UsernameNotFoundException("Not Found: " + userName));
        return customer.map(MyUserDetails::new).get();
    }
}
