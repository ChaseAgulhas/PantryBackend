package com.pantry.app.repository.customer;

import com.pantry.app.domain.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * Created by cfebruary on 2016/09/03.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{
}
