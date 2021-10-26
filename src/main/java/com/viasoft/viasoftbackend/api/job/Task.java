package com.viasoft.viasoftbackend.api.job;

import com.viasoft.viasoftbackend.api.model.NfModel;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.io.IOException;

public class Task implements Tasklet {

    private List<NfModel> getNfModelFromHTML() throws IOException {

        List<NfModel> nfArray = new ArrayList<>();
        Document doc = Jsoup.connect("http://www.nfe.fazenda.gov.br/portal/disponibilidade.aspx").get();
        Element table = doc.getElementById("ctl00_ContentPlaceHolder1_gdvDisponibilidade2");
        Elements tableRow = table.getElementsByTag("tr");

        for(Element tr : tableRow){
            NfModel nfToBePersisted = new NfModel();
            if(tr.getElementsByTag("td").size() != 0) {
                String uf = tr.getElementsByTag("td").get(0).text();
                nfToBePersisted.setUf(uf);
                //nfToBePersisted.setVerificationTime(LocalDateTime.now());
                String attributeImage = tr.getElementsByTag("td").get(5).getElementsByTag("img").get(0).attr("src");
                if(attributeImage.equals("imagens/bola_verde_P.png")){
                    nfToBePersisted.setStatus(true);
                }
                else{
                    nfToBePersisted.setStatus(false);
                }
            }
            nfArray.add(nfToBePersisted);
        }
        return nfArray;
    }

    private String persistData(NfModel nf) throws JSONException, URISyntaxException, IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("uf", nf.getUf());
        json.put("status", nf.isStatus());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/nf"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = HttpClient.newBuilder().build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.toString();
    }

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception{

        List<NfModel> nfArrayToBePersisted = this.getNfModelFromHTML();
        System.out.println("Tarefa Iniciada!");
        for(NfModel nf : nfArrayToBePersisted){
            if(nf.getUf() != null){
                String response = this.persistData(nf);
                System.out.println(response);
                System.out.println("Nf: " + nf.toString());
            }
        }

        System.out.println("Tarefa finalizada!");
        return RepeatStatus.FINISHED;
    }
}
