package dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import entity.Customer;
import model.CustomerInfo;
import pagination.PaginationResult;

@Transactional
@Repository
public class CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Customer findCustomer(String email) {
		try {
			String sql = "Select e from " + Customer.class.getName() //
					+ " e Where e.email =:email ";
			Session session = this.sessionFactory.getCurrentSession();
			Query<Customer> query = session.createQuery(sql, Customer.class);
			query.setParameter("email", email);
			return (Customer) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean save(Customer customer) {
		Session session = this.sessionFactory.getCurrentSession();
		String email = customer.getEmail();
		Customer oldCustomer = null;
		if (email != null) {
			oldCustomer = this.findCustomer(email);
		}
		if (oldCustomer == null) {
			session.persist(customer);
			session.flush();
			return true;
		}
		return false;
	}

	public PaginationResult<CustomerInfo> listCustomerInfo(int page, int maxResult, int maxNavigationPage) {
		String sql = "Select new " + CustomerInfo.class.getName()//
				+ " (cust.name, cust.address, cust.email," //
				+ " cust.phone)" + " from " + Customer.class.getName() //
				+ " cust order by cust.name asc";

		Session session = this.sessionFactory.getCurrentSession();
		Query<CustomerInfo> query = session.createQuery(sql, CustomerInfo.class);
		return new PaginationResult<CustomerInfo>(query, page, maxResult, maxNavigationPage);
	}

}
