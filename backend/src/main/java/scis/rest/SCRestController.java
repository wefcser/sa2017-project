package scis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import scis.service.SCService;
import scis.model.SC;

import java.util.Collection;

/**
 * Created by wangyifei on 2017/6/11.
 */
@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/scis")
public class SCRestController {
    @Autowired
    private SCService scService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<SC>> getSC(){
        Collection<SC> SCs = this.scService.findAllSC();
        if(SCs.isEmpty()){
            return new ResponseEntity<Collection<SC>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<SC>>(SCs, HttpStatus.OK);
    }
}
