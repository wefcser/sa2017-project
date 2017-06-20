package scis.repository.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.springframework.transaction.annotation.Transactional;
import scis.model.SC;
import scis.repository.SCRepository;

/**
 * Created by wangyifei on 2017/6/11.
 */

@Repository
@Profile("jpa")
public class JpaSCRepositoryImpl implements SCRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public SC findById(int id) {
        return this.em.find(SC.class, id);
    }

    @Override
    @Transactional
    public void save(SC sc) {
        //System.out.println("save "+sc.getNo());
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
    public Collection<SC> findPart(int no) throws DataAccessException {
        Collection<SC> scs=this.em.createQuery("SELECT sc FROM SC sc").getResultList();
        int size = scs.size();
        SC[] scsArray = new SC[size];
        scs.toArray(scsArray);
        Collection<SC> part = new ArrayList<SC>();
        int i=no*10-10;
        while(i<no*10){
            if(i>=size){
                part.add(new SC());
            }else {
                part.add(scsArray[i]);
            }
            i++;
        }
        return part;
    }

    @Override
    @Transactional
    public void delete(SC sc) throws DataAccessException {
        if (em.contains(sc)) {
            em.remove(sc);
        }
    }

}
