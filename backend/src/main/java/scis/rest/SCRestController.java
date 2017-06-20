package scis.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import scis.service.SCService;
import scis.model.SC;
import scis.model.Page;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
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

    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Page>> getPage(){
        System.out.println("-----------------------------get");
        Collection<SC> SCs = this.scService.findAllSC();
        int s =SCs.size();
        int no = 0;
        if(s == 0){
            no = 1;
        } else if(s%10 == 0){
            no = s/10;
        } else {
            no = s/10 +1;
        }
        Collection<Page> pages = new ArrayList<Page>();
        for(int i=1;i<=no;i++){
            Page p=new Page();
            p.setNo(i);
            pages.add(p);
        }
        return new ResponseEntity<Collection<Page>>(pages, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<SC>> getSC(){
        System.out.println("-----------------------------get");
        Collection<SC> SCs = this.scService.findAllSC();
        if(SCs.isEmpty()){
            return new ResponseEntity<Collection<SC>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<SC>>(SCs, HttpStatus.OK);
    }

    @RequestMapping(value = "/{pageNo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<SC>> getSCPage(@PathVariable("pageNo") int pageNo){
        System.out.println("-----------------------------gatPage"+pageNo);
        Collection<SC> SCs = this.scService.findPartSC(pageNo);
        System.out.println("-----------------------------findPart");
        if(SCs.isEmpty()){
            return new ResponseEntity<Collection<SC>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<SC>>(SCs, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SC> addSC(@RequestBody @Valid SC sc, BindingResult bindingResult){
        System.out.println("-----------------------------post");
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (sc == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<SC>(headers, HttpStatus.BAD_REQUEST);
        }
        this.scService.saveSC(sc);
        //headers.setLocation(ucBuilder.path("").buildAndExpand(sc.getId()).toUri());
        return new ResponseEntity<SC>(sc, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{scId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SC> updateSC(@PathVariable("scId") int scId, @RequestBody @Valid SC sc, BindingResult bindingResult){
        System.out.println("-----------------------------put");
        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();
        if(bindingResult.hasErrors() || (sc == null)){
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<SC>(headers, HttpStatus.BAD_REQUEST);
        }
        SC currentSC = this.scService.findById(scId);
        if(currentSC == null){
            System.out.println("null");
            return new ResponseEntity<SC>(HttpStatus.NOT_FOUND);
        }
        currentSC.setNo(sc.getNo());
        currentSC.setName(sc.getName());
        currentSC.setDepart(sc.getDepart());
        currentSC.setCourse(sc.getCourse());
        currentSC.setGrade(sc.getGrade());
        this.scService.saveSC(currentSC);
        return new ResponseEntity<SC>(currentSC, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{scId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Transactional
    public ResponseEntity<Void> deleteSC(@PathVariable("scId") int scId){
        SC sc = this.scService.findById(scId);
        if(sc == null){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        this.scService.deleteSC(sc);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
