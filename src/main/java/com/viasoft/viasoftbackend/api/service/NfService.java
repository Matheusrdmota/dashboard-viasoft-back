package com.viasoft.viasoftbackend.api.service;

import com.viasoft.viasoftbackend.api.model.NfModel;
import com.viasoft.viasoftbackend.api.repository.NfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NfService {
    @Autowired
    private NfRepository repository;

    public NfModel create(NfModel nfModel){
        return repository.save(nfModel);
    }

    public List<NfModel> list(){
        return repository.findCurrentStatus();
    }

    public NfModel listbyUf(String uf){
        return repository.findByUf(uf)
                .orElseThrow(() -> new NoSuchElementException());
    }

    public List<NfModel> listByDate(String date){
        return repository.findByVerificationTime(date)
                .orElseThrow(() -> new NoSuchElementException());
    }

    public String getUfWithMoreIndisponibility(){
        return repository.findUfWithMoreIndisponibility()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
