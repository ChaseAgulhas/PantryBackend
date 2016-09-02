package com.pantry.app.client;

import com.google.gson.Gson;
import com.pantry.app.domain.customer.Customer;
import com.pantry.app.repository.customer.CustomerRepository;
import com.pantry.app.service.customer.Impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by cfebruary on 2016/09/03.
 */
@RestController
public class CustomerController implements Serializable{

    //Inject service
    @Autowired
    private CustomerServiceImpl customerService;

    //Create a customer
    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public ResponseEntity<Customer>createCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder ucBuilder)
    {
        System.out.println("Creating request for: " + newCustomer.toString());

        Customer customer = customerService.create(newCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customer/{emailAddress}").buildAndExpand(newCustomer.getEmailAddress()).toUri());

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    //Retrieve a single customer
    @RequestMapping(value = "/customer/{emailAddress}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer>getCustomer(@PathVariable("emailAddress") String emailAddress)
    {
        System.out.println("Fetching customer with email: " + emailAddress);

        Customer request = customerService.readById(emailAddress);
        if(request == null)
        {
            System.out.println("Customer with email: " + emailAddress + " NOT FOUND");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Customer>(request, HttpStatus.OK);
    }

    //Retrieve all employees
    @RequestMapping(value = "/customers/", method = RequestMethod.GET)
    public ResponseEntity<Set<Customer>>getCustomers()
    {
        Set<Customer> requests = customerService.readAll();
        if(requests.isEmpty())
        {
            return new ResponseEntity<Set<Customer>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Set<Customer>>(requests, HttpStatus.OK);
    }

    //Update an employee
    @RequestMapping(value = "/customer/{emailAddress}", method = RequestMethod.PUT)
    public ResponseEntity<Customer>updateCustomer(@PathVariable("emailAddress")String emailAddress, @RequestBody String customer)
    {
        Customer currentRequest = (Customer) customerService.readById(emailAddress);

        if(currentRequest == null)
        {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        Gson gson = new Gson();
        Customer updatedCustomer = new Customer.Builder()
                .copy(currentRequest)
                .name(gson.fromJson(customer, Customer.class).getName())
                .surname(gson.fromJson(customer, Customer.class).getSurname())
                .dateOfBirth(gson.fromJson(customer, Customer.class).getDateOfBirth())
                .build();
        customerService.update(updatedCustomer);
        return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
    }

    //Delete a customer
    @RequestMapping(value ="/customer/{emailAddress}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer>deleteEmployee(@PathVariable("emailAddress")String emailAddress)
    {
        Customer request = customerService.readById(emailAddress);
        if(request == null)
        {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        customerService.delete(request);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
}
