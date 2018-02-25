package br.com.produzz.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**.
 * Objetivo: Classe generica.
 * Nome: GenericService
 * @since 23/09/2017
 * @version 1.0
 * @author Caius Muniz
 */
public class GenericService {
//	@PersistenceContext
//	private EntityManager emf;
//
//	protected EntityManager getEntityManager() {
//		if (emf == null) {
//			EntityManagerFactory factory = Persistence.createEntityManagerFactory("produzz");
//			emf = factory.createEntityManager();
//		}
//		return emf;
//	}
//
//	public Object buscar(final Class<?> clazz, final Long id) throws SQLException {
//		return this.getEntityManager().find(clazz, id);
//	}
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public void salvar(Object obj) throws SQLException {
//		this.getEntityManager().persist(obj);
//		this.getEntityManager().flush();
//	}
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public void atualizar(Object obj) throws SQLException {
//		this.getEntityManager().merge(obj);
//		this.getEntityManager().flush();
//	}
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public void remove(final Class<?> clazz, final Long id) throws SQLException {
//		this.getEntityManager().remove(this.getEntityManager().find(clazz, id));
//		this.getEntityManager().flush();
//	}

	@SuppressWarnings("unused")
	public String enviaEmail(String email) {
		List<String> msgsErro = new ArrayList<String>();

		return null;
	}
}
