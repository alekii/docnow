package com.docnow.docnow.dao;

import com.docnow.docnow.entity.Doctor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class DoctorDAOHibernateImpl  implements DoctorDAO{

  private final EntityManager entityManager;
   @Autowired
   public DoctorDAOHibernateImpl(EntityManager theEntityManager){

       entityManager = theEntityManager;
   }


    @Override
    public List<Doctor> findAll() {

        //get the current hibernate session
        Session currentSession = entityManager.unwrap(Session.class);


        //create query
        Query<Doctor> theQuery =
                currentSession.createQuery("from Doctor", Doctor.class);

        //execute query and get result list
        // return the results
         return theQuery.getResultList();
    }

  @Override
    public Doctor findById(int theId){
       Session currentSession = entityManager.unwrap(Session.class);
       Doctor doctor = currentSession.get(Doctor.class, theId);
       return doctor;
    }

    @Override
    public void save(Doctor theDoctor){
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(theDoctor);
    }

    @Override
    public void deleteById(int theId){
     Session currentSession  = entityManager.unwrap(Session.class);
     //delete object with primary key
     Query theQuery = currentSession.createQuery("delete from Doctor where id=:doctorId");
     theQuery.setParameter("doctorId", theId);
     theQuery.executeUpdate();
    }
}
