package scis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import scis.service.SCService;
import scis.model.SC;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Created by wangyifei on 2017/6/11.
 */
@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/sc")
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

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SC> addSC(@RequestBody @Valid SC sc, BindingResult bindingResult){
        System.out.println("-------------------here1");
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (sc == null)){
            System.out.println("-------------------error");
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<SC>(headers, HttpStatus.BAD_REQUEST);
        }
        this.scService.saveSC(sc);
        System.out.println("-------------------here2");
        //headers.setLocation(ucBuilder.path("/sc/{id}").buildAndExpand(sc.getId()).toUri());
        return new ResponseEntity<SC>(sc, headers, HttpStatus.CREATED);
    }
}
