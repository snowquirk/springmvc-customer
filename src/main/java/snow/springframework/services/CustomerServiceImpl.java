package snow.springframework.services;

import org.springframework.stereotype.Service;
import snow.springframework.domain.Customer;
import snow.springframework.domain.Product;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService{
    private Map<Integer, Customer> customers;

    public CustomerServiceImpl() {
        loadCustomers();
    }


    @Override
    public List<Customer> listAllCustomer() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customers.get(id);

    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        if(customers != null){
            if(customer.getId() == null){
                customer.setId(getNextKey());
            }
            customers.put(customer.getId(), customer);
            return customer;
        } else {
            throw new RuntimeException("Customer can't be null");
        }
    }

    private Integer getNextKey() {
        return Collections.max(customers.keySet()) + 1;
    }

    @Override
    public void deleteCustomer(Integer id) {
        customers.remove(id);
    }

    private void loadCustomers() {

            customers = new HashMap<>();

            Customer customer1 = new Customer();
            customer1.setId(1);
            customer1.setFirstName("Micheal");
            customer1.setLastName("Weston");
            customer1.setAddress("300 queen St");
            customer1.setCity("Melbourne");
            customer1.setState("VIC");
            customer1.setPostcode("3000");
            customer1.setEmail("micheal@burnnotice.com");
            customer1.setPhone("0450550550");

            customers.put(1, customer1);

            Customer customer2 = new Customer();
            customer2.setId(2);
            customer2.setFirstName("Fiona");
            customer2.setLastName("Glenanne");
            customer2.setAddress("1 Garden Ave");
            customer2.setCity("Clayton");
            customer2.setState("VIC");
            customer2.setPostcode("3168");
            customer2.setEmail("fiona@burnnotice.com");
            customer2.setPhone("0409009008");
            customers.put(2, customer2);

            Customer customer3 = new Customer();
            customer3.setId(3);
            customer3.setFirstName("Sam");
            customer3.setLastName("Axe");
            customer3.setAddress("1 Little Collin Road");
            customer3.setCity("Melbourne");
            customer3.setState("VIC");
            customer3.setPostcode("3000");
            customer3.setEmail("sam@burnnotice.com");
            customer3.setPhone("305.426.9832");
            customers.put(3, customer3);


        }
}
