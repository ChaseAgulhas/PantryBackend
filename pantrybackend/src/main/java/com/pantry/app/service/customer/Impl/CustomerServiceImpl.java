package com.pantry.app.service.customer.Impl;

import com.pantry.app.domain.customer.Customer;
import com.pantry.app.repository.customer.CustomerRepository;
import com.pantry.app.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by cfebruary on 2016/09/03.
 */
@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer create(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public Customer readById(String s) {
        return customerRepository.findOne(s);
    }

    @Override
    public Set<Customer> readAll() {

        Set<Customer>result = new HashSet<>();
        while(customerRepository.findAll().iterator().hasNext())
        {
            result.add(customerRepository.findAll().iterator().next());
        }
        return result;
    }

    @Override
    public Customer update(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public void delete(Customer entity) {
        customerRepository.delete(entity);
    }
}
