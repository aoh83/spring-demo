package scalable.monster.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CustomerController {

  private final CustomerRepository customers;

  public CustomerController(final CustomerRepository customers) {
    this.customers = customers;
  }

  @GetMapping("/customers")
  Collection<Customer> all() {
    return customers.findAll();
  }

  @GetMapping("/customers/{id}")
  Customer one(@PathVariable Long id) {
    return customers.findById(id).orElseThrow(() -> new IllegalArgumentException("customer not found: " + id));
  }

  @PostMapping("/customers")
  Customer newCustomer(@RequestBody  Customer newCustomer) {
    return customers.save(newCustomer);
  }

  @PutMapping("/customers/{id}")
  Customer replaceCustomer(@RequestBody  Customer newCustomer, @PathVariable  Long id) {
    return customers.findById(id)
      .map(customer -> {
        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        return customers.save(customer);
      })
      .orElseGet(() -> {
        newCustomer.setId(id);
        return customers.save(newCustomer);
      });
  }

  @DeleteMapping("/customers/{id}")
  void deleteCustomer(@PathVariable  Long id) {
    customers.deleteById(id);
  }
}
