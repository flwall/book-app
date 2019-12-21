package com.waflo;

import com.waflo.model.Author;
import com.waflo.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

class DBService {

    private final SessionFactory sessionFactory;
    private static DBService instance;

    static DBService getInstance() {
        if (instance == null) instance = new DBService();
        return instance;
    }

    private DBService() {
        Configuration configuration = new Configuration();

        configuration.configure();
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(Author.class);

        sessionFactory = configuration.buildSessionFactory();

    }
    Session openSession(){
        return sessionFactory.openSession();
    }


}
