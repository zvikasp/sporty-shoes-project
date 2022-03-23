package controller;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import entity.Account;
import entity.Customer;
import entity.Product;
import form.ProductForm;
import form.SignUpForm;
import model.CustomerInfo;
import model.OrderDetailInfo;
import model.OrderInfo;
import pagination.PaginationResult;
import validator.ProductFormValidator;

@Controller
@Transactional
public class AdminController {

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private ProductFormValidator productFormValidator;

	@InitBinder
	public void myInitBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		if (target.getClass() == ProductForm.class) {
			dataBinder.setValidator(productFormValidator);
		}
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute("signUpForm", new SignUpForm());
		return "signupForm";
	}

	@PostMapping("/userRegister")
	public String processRegister(SignUpForm signUpForm) {

		Customer customer = new Customer();
		customer.setUserId(signUpForm.getUserId());
		customer.setName(signUpForm.getName());
		customer.setEmail(signUpForm.getEmail());
		customer.setPhone(signUpForm.getPhone());
		customer.setAddress(signUpForm.getAddress());
		boolean newUser = customerDAO.save(customer);
		if (newUser) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(signUpForm.getPassword());
			Account account = new Account();
			account.setEncrytedPassword(encodedPassword);
			account.setUserName(customer.getUserId());
			account.setActive(newUser);
			account.setUserRole("ROLE_CUSTOMER");
			accountDAO.save(account);
		}

		return "userRegister";
	}

	@RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = { "/admin/changePassword" }, method = RequestMethod.GET)
	public String changePassword(Model model) {
		return "changePassword";
	}

	@RequestMapping(value = { "/changePassword" }, method = RequestMethod.POST)
	public String processChangePassword(HttpServletRequest request, HttpServletResponse response, Model model,
			RedirectAttributes ra, @AuthenticationPrincipal Authentication authentication) throws ServletException {
		return "changePassword";
	}

	@RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userDetails", userDetails);
		return "accountInfo";
	}

	@RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
	public String orderList(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;
		PaginationResult<OrderInfo> paginationResult //
				= orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationResult", paginationResult);
		return "orderList";
	}

	@RequestMapping(value = { "/admin/userList" }, method = RequestMethod.GET)
	public String userList(Model model, //
			@RequestParam(value = "page", defaultValue = "1") String pageStr) {
		int page = 1;
		try {
			page = Integer.parseInt(pageStr);
		} catch (Exception e) {
		}
		final int MAX_RESULT = 5;
		final int MAX_NAVIGATION_PAGE = 10;
		PaginationResult<CustomerInfo> paginationResult //
				= customerDAO.listCustomerInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		model.addAttribute("paginationResult", paginationResult);
		return "userList";
	}

	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
	public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		ProductForm productForm = null;
		if (code != null && code.length() > 0) {
			Product product = productDAO.findProduct(code);
			if (product != null) {
				productForm = new ProductForm(product);
			}
		}
		if (productForm == null) {
			productForm = new ProductForm();
			productForm.setNewProduct(true);
		}
		model.addAttribute("productForm", productForm);
		return "product";
	}

	@RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
	public String productSave(Model model, //
			@ModelAttribute("productForm") @Validated ProductForm productForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "product";
		}
		try {
			productDAO.save(productForm);
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			String message = rootCause.getMessage();
			model.addAttribute("errorMessage", message);
			return "product";
		}
		return "redirect:/productList";
	}

	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
	public String orderView(Model model, @RequestParam("orderId") String orderId) {
		OrderInfo orderInfo = null;
		if (orderId != null) {
			orderInfo = this.orderDAO.getOrderInfo(orderId);
		}
		if (orderInfo == null) {
			return "redirect:/admin/orderList";
		}
		List<OrderDetailInfo> details = this.orderDAO.listOrderDetailInfos(orderId);
		orderInfo.setDetails(details);
		model.addAttribute("orderInfo", orderInfo);
		return "order";
	}
}
