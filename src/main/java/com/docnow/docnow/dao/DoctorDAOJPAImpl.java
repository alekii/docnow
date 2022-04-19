package com.docnow.docnow.dao;

import com.docnow.docnow.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class DoctorDAOJPAImpl implements DoctorDAO{

    private EntityManager entityManager;

   @Autowired
    public DoctorDAOJPAImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Doctor> findAll() {

        //create Query
        Query theQuery = entityManager.createQuery("from Doctor");
        List<Doctor> doctors= theQuery.getResultList();
        return doctors;
    }

    @Override
    public Doctor findById(int theId) {
       //get Doctor
        Doctor doctor = entityManager.find(Doctor.class,theId);

        //return Doctor
        return doctor;
    }

    @Override
    public void save(Doctor theDoctor) {
       Doctor dbDoctor = entityManager.merge(theDoctor);
      //if id=0 save else update
        // update with id from db
        theDoctor.setId(dbDoctor.getId());
    }

    @Override
    public void deleteById(int theId) {
         Query deleteQuery = entityManager.createQuery("delete from Doctor where id=:doctorId");
         deleteQuery.setParameter("doctorId", theId);
         deleteQuery.executeUpdate();
    }
}
