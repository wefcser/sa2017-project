package scis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scis.repository.SCRepository;
import scis.model.SC;

import java.util.Collection;

/**
 * Created by wangyifei on 2017/6/11.
 */
@Service
public class SCServiceImpl implements SCService{

    private SCRepository scRepository;

    @Autowired
    public SCServiceImpl(SCRepository scRepository){
        this.scRepository = scRepository;
    }

    @Override
    public SC findById(int id) {
        return scRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<SC> findAllSC(){
        return scRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<SC> findPartSC(int no){
        return scRepository.findPart(no);
    }

    @Override
    public void saveSC(SC sc) {
        scRepository.save(sc);
    }

    @Override
    @Transactional
    public void deleteSC(SC sc) throws DataAccessException {
        scRepository.delete(sc);
    }
}
