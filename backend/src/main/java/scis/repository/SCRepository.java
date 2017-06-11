package scis.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

import scis.model.SC;

/**
 * Created by wangyifei on 2017/6/11.
 */

public interface SCRepository {

    SC findById(int id) throws DataAccessException;
    Collection<SC> findAll() throws DataAccessException;
    void save(SC sc) throws DataAccessException;
    void delete(SC sc) throws DataAccessException;
}