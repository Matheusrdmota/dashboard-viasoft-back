package com.viasoft.viasoftbackend.api.controller;

import com.viasoft.viasoftbackend.api.model.NfModel;
import com.viasoft.viasoftbackend.api.model.StringResponse;
import com.viasoft.viasoftbackend.api.service.NfService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nf")
public class NfController {
    @Autowired
    private NfService nfService;

    @PostMapping
    public ResponseEntity<NfModel> createNf(@RequestBody NfModel nfModel){
        return ResponseEntity.ok(nfService.create(nfModel));
    }

    @GetMapping
    public ResponseEntity<List<NfModel>> listAllNf(){
        return ResponseEntity.ok(nfService.list());
    }

    @GetMapping("/{uf}")
    public ResponseEntity<NfModel> listByUf(@PathVariable String uf){
        return ResponseEntity.ok(nfService.listbyUf(uf));
    }

    @GetMapping("/dt")
    public ResponseEntity<List<NfModel>> listByVerificationTime(@RequestParam("date") String date){
        return ResponseEntity.ok(nfService.listByDate(date));
    }

    @GetMapping("/indisponibility")
    public ResponseEntity<StringResponse> getUfWithMoreIndisponibility() throws JSONException {
        StringResponse response = new StringResponse(nfService.getUfWithMoreIndisponibility());
        return ResponseEntity.ok(response);
    }
}
