package dao;

import modele.BaseModele;
import org.hibernate.SessionFactory;

import java.util.List;

public class HibernateDAO implements InterfaceDAO{
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception {
        return null;
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception {
        return null;
    }

    @Override
    public List<BaseModele> findAll(BaseModele baseModele) throws Exception {
        return null;
    }

    @Override
    public List<BaseModele> findAll() throws Exception {
        return null;
    }

    @Override
    public void findById(BaseModele modele) throws Exception {

    }

    @Override
    public void delete(BaseModele modele) throws Exception {

    }

    @Override
    public void update(BaseModele modele) throws Exception {

    }

    @Override
    public void save(BaseModele modele) throws Exception {

    }

    @Override
    public String getRequeteFindAll() {
        return null;
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) throws Exception {
        return null;
    }

    @Override
    public String getRequeteFindById(BaseModele modele) throws Exception {
        return null;
    }

    @Override
    public String getRequeteDelete(BaseModele modele) throws Exception {
        return null;
    }
}
