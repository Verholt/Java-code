package com.dsv.road.masterdata.idgenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class IdTypeManager {
	EntityManagerFactory emf;
	@PersistenceContext
	EntityManager em;

	public IdTypeManager() {
		emf = Persistence.createEntityManagerFactory("master_data_service-ejb");
		em = emf.createEntityManager();
	}

	public IdType getIdType(String type) {
		return em.find(IdType.class, type);
	}
	public IdType insertIdType(IdType idType) {
		em.persist(idType);
		return idType;
	}
}
