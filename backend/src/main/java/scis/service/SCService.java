package scis.service;

import scis.model.SC;

import java.util.Collection;

/**
 * Created by wangyifei on 2017/6/11.
 */
public interface SCService {
    SC findById(int id);
    Collection<SC> findAllSC();
    Collection<SC> findPartSC(int no);
    void saveSC(SC sc);
    void deleteSC(SC sc);
}
