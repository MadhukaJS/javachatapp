package com.chatapp.database;

import entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

public class Database {

    public EntityManagerFactory emf;
    public EntityManager em;
    public EntityTransaction et;
    public Session se;

    public Database() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
        et = em.getTransaction();
        se = em.unwrap(Session.class);
    }

    public void insert(UserEntity userEntity){
        try {
            et.begin();
            em.persist(userEntity);
            et.commit();
        }finally {
            if (et.isActive()){
                et.rollback();
            }

        }
    }

    public UserEntity search(String username) {
        UserEntity user;
        try {
            user = em.find(UserEntity.class, username);


        } finally {
            if (et.isActive()) {
                et.rollback();
            }
        }

        return user;

    }

    public UserEntity UserPro(int userId) {
        UserEntity user;
        try {
            user = em.find(UserEntity.class, userId);
        } finally {
            if (et.isActive()) {
                et.rollback();
            }
        }
        return user;
    }




}
