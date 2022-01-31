package com.example.demoSpring.controller;
// add service for return varsion of API
/*
 * le API possono avere più versioni
 *
 * Utilizziamo il JSON
 * si utilizzano sempre i doppi apici
 *
 * Questa classe genera un web service,
 * Tipi di Web service: si creano metodi che sono esposti in rete
 * permettono di integrare diverse applicazioni anche eterogenei.
 * è un linguaggio universale, qualsiasi linguaggio può comunicare attraverso
 * Webservice. -> approccio alla trasmissione di dati online.
 * creare interfacce di programmazione delle applicazioni (API)
 * che consentono la comunicazione dei dati tra applicazioni web.
 *
 * Differenze:
 * 1. SOAP = protocollo, simple object access protocol
 * 2. REST = Representational State Transfer. Insieme di principi architettonici
 *
 */
import com.example.demoSpring.model.Voce;
import com.example.demoSpring.repository.VociRepo;
import com.example.demoSpring.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController //annotations are libraries
public class RubricaApiController {
    ArrayList<Voce> voci = new ArrayList<>();

    @Autowired //dependency injection automagicamente
    VociRepo
            vociRepo;


    //http://localhost/api/v1/version
    //@CrossOrigin()
    //@GetMapping("/api/v1/version")
    /*public String getApiVersion(){
        return "{ \"version\": \"0.1\"}";
    }*/

    @CrossOrigin()
    @GetMapping("/api/v1/voci") //metodo GET protocollo HTTP sulla collection
    public ArrayList<Voce> getVoci() {
        return (ArrayList<Voce>) vociRepo.findAll(); //restituisce tutti gli elementi che stanno dentro la repository
    }

    @CrossOrigin()
    @PostMapping("/api/v1/voce") //metodo POST protocollo HTTP
    public void addVoce(@RequestBody Voce voce){
        vociRepo.save(voce);
    }

    //http://localhost/api/v1/voci/3 voce di id 3
    @CrossOrigin
    @GetMapping("/api/v1/voci/{id}") //metodo GET protocollo HTTP
    public Voce getVoce(@PathVariable long id) {
        Optional<Voce> opt = vociRepo.findById(id); //oggetto opzionale "se esiste/se non esiste" genera un'ecc in caso in cui non esiste l'oggetto
        return opt.get();
    }
    @CrossOrigin
    @DeleteMapping("/api/v1/voci/{id}") //metodo DELETE protocollo HTTP
    public void deleteVoce(@PathVariable long id) {
        Optional<Voce> opt = vociRepo.findById(id);
        vociRepo.delete(opt.get());
    }

    @CrossOrigin
    @PutMapping("/api/v1/voci/{id}") //metodo PUT protocollo HTTP
    public void editVoce(@PathVariable long id, @RequestBody Voce voce) {
        voce.setId(id);
        vociRepo.save(voce);
    }

    //http://localhost/api/v1/search/?key=chiave
    @CrossOrigin()
    @GetMapping("/api/v1/search")
    public ArrayList<Voce> search(@RequestParam(value = "key", defaultValue = "") String key){
        ArrayList<Voce> al = vociRepo.findByNomeContainingOrCognomeContainingOrTelefonoContaining(key,key,key);
        return al;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElementException(NoSuchElementException e){
        ErrorResponse er = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<ErrorResponse>(er, HttpStatus.NOT_FOUND);
    }
}
