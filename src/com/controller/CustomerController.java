package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Customer;
import com.service.CustomerService;

@Controller
@RequestMapping("customer")
public class CustomerController {

	// need to inject the customer service layer
	@Autowired
	private CustomerService customerService;

	// GET

	@GetMapping("/list")
	public String listCustomers(Model model) {

		// get customers from the dao
		List<Customer> customers = customerService.getCustomers();

		// add the customers to the model (model will be accessed by the jsp)
		model.addAttribute("customers", customers);

		return "list-customers";
	}

	@GetMapping("showFormForAdd")
	public String showFormForAdd(Model model) {

		// create an instance of the Customer class
		Customer customer = new Customer();

		// pass data to the view
		model.addAttribute("customer", customer);

		return "showFormForAdd";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int customerId, Model model) {

		// get the customer from the db
		Customer customer = customerService.getCustomer(customerId);

		// set the customer as a model attr to pre-populate the form
		model.addAttribute("customer", customer);

		// send over to the form
		return "showFormForAdd";
	}

	// POST

	@PostMapping("saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {

		// save the customer data using customer service
		customerService.saveCustomer(customer);

		// reroute to home page
		return "redirect:/customer/list";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int customerId) {

		// save the customer data using customer service
		customerService.deleteCustomer(customerId);

		// reroute to home page
		return "redirect:/customer/list";
	}

}
