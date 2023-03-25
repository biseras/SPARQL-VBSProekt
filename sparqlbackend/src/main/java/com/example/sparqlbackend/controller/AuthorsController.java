package com.example.sparqlbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorsController {
    @PostMapping("/searchauthor")
    public ResponseEntity<String> getAuthor(@RequestBody String namejson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(namejson);
        String name = jsonNode.get("name").asText();
        System.out.println(name);
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> PREFIX dct: <http://purl.org/dc/terms/> SELECT DISTINCT ?ime ?birthname ?abstract ?birthDate ?birthPlace ?deathDate ?deathPlace ?thumbnail WHERE { ?books dbo:author dbr:"+name+" . ?books rdfs:label ?ime . dbr:"+name+" dbo:abstract ?abstract ;dbp:birthName ?birthname ; dbp:birthDate ?birthDate ; dbp:birthPlace ?birthPlace ; dbo:deathDate ?deathDate ; dbp:deathPlace ?deathPlace ; dbo:thumbnail ?thumbnail . FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANG(?ime) = 'en')}";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        List<String> book=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String ime= String.valueOf(solution.get("ime"));
                book.add(ime);
            }
            jsonObject.put("book", book);
            QueryExecution eq = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
            ResultSet result = eq.execSelect();
            QuerySolution solution = result.nextSolution();
            jsonObject.put("searchname", name);
            jsonObject.put("deathPlace", solution.get("deathPlace"));
            jsonObject.put("thumbnail", solution.get("thumbnail"));
            jsonObject.put("birthPlace", solution.get("birthPlace"));
            jsonObject.put("abstract", solution.get("abstract"));
            jsonObject.put("birthname", solution.get("birthname"));
            RDFNode birthDateNode = solution.get("birthDate");
            RDFNode deathDateNode = solution.get("deathDate");
            if (birthDateNode != null) {
                Literal birthDateLiteral = birthDateNode.asLiteral();
                if (birthDateLiteral != null) {
                    String birthDateString = birthDateLiteral.getLexicalForm();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date birthDate = sdf.parse(birthDateString.substring(0, 10));
                        jsonObject.put("birthDate", birthDate);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            if (deathDateNode != null) {
                Literal deathDateLiteral = deathDateNode.asLiteral();
                if (deathDateLiteral != null) {
                    String deathDateString = deathDateLiteral.getLexicalForm();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date deathDate = sdf.parse(deathDateString.substring(0, 10));
                        jsonObject.put("deathDate", deathDate);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
}
