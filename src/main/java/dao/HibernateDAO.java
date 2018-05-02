package dao;

import modele.BaseModele;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@SuppressWarnings("All")
public class HibernateDAO implements InterfaceDAO{
    private SessionFactory factory;

    public void save(BaseModele modele, Session session){
        session.save(modele);
    }

    public void update(BaseModele modele, Session session){
        session.update(modele);
    }

    public void delete(BaseModele modele, Session session){
        session.delete(modele);
    }

    public List<BaseModele> list(Criteria criteria){
        return criteria.list();
    }

    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne, Session session) throws Exception {
        try{
            int minSet = (page - 1) * nbDonne + 1;
            int maxSet = page * nbDonne;
            Criteria criteria = session.createCriteria(modele.getClass());
            criteria.setFirstResult(minSet);
            criteria.setMaxResults(maxSet);
            if(!strict) criteria.add(Restrictions.disjunction());
            criteria.add(Example.create(modele));
            return list(criteria);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict, int page, int nbDonne) throws Exception {
        try(Session session = factory.openSession()){
            return findAll(modele, strict, page, nbDonne, session);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele, boolean strict) throws Exception {
        return findAll(modele, strict, 1, 20);
    }

    @Override
    public List<BaseModele> findAll(BaseModele modele) throws Exception {
        return findAll(modele, true, 1, 20);
    }

    public void findById(BaseModele modele, Session session){
        Criteria criteria = session.createCriteria(modele.getClass());
        criteria.add(Restrictions.eq("id", modele.getId()));
        List<BaseModele> modeles = criteria.list();
    }

    @Override
    public void findById(BaseModele modele) {

    }

    @Override
    public void delete(BaseModele modele) throws Exception {
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            delete(modele, session);
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void update(BaseModele modele) throws Exception {
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            update(modele, session);
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void save(BaseModele modele) throws Exception {
        Transaction transaction = null;
        try(Session session = factory.openSession()){
            transaction = session.beginTransaction();
            save(modele, session);
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String getRequeteFindAll() {
        throw new NotImplementedException();
    }

    @Override
    public String getRequeteFindAll(BaseModele modele) {
        throw new NotImplementedException();
    }

    @Override
    public String getRequeteFindById(BaseModele modele) {
        throw new NotImplementedException();
    }

    @Override
    public String getRequeteDelete(BaseModele modele) {
        throw new NotImplementedException();
    }

    @Override
    public List<BaseModele> findAll(){ throw new NotImplementedException(); }
}
