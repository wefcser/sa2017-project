package scis.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

import scis.model.SC;

/**
 * Created by wangyifei on 2017/6/11.
 */

@Repository
@Profile("jpa")
public class JpaSCRepositoryImpl implements SCRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public SC findById(int id) {
        return this.em.find(SC.class, id);
    }

    @Override
    public void save(SC sc) {
        if (sc.getId() == null) {
            this.em.persist(sc);
        } else {
            this.em.merge(sc);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<SC> findAll() throws DataAccessException {
        return this.em.createQuery("SELECT sc FROM SC sc").getResultList();
    }

    @Override
    public void delete(SC sc) throws DataAccessException {
        //this.em.remove(this.em.contains(pet) ? pet : this.em.merge(pet));
        String petId = sc.getId().toString();
        this.em.createQuery("DELETE FROM SC sc WHERE id=" + petId).executeUpdate();
        if (em.contains(sc)) {
            em.remove(sc);
        }
    }

}
