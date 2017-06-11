package scis.service;

import scis.model.SC;

import java.util.Collection;

/**
 * Created by wangyifei on 2017/6/11.
 */
public interface SCService {
    SC findById(Long id);
    Collection<SC> findAllSC();
    void saveSC(SC sc);
    void deleteSC(SC sc);
}
