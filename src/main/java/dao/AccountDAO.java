package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import entity.Account;

@Transactional
@Repository
public class AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Account findAccount(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.find(Account.class, userName);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void save(Account account) {
		Session session = this.sessionFactory.getCurrentSession();
		String userName = account.getUserName();
		Account existingAccount = null;
		if (userName != null) {
			existingAccount = this.findAccount(userName);
		}
		if (existingAccount == null) {
			session.persist(account);
			session.flush();
		}
	}
}
