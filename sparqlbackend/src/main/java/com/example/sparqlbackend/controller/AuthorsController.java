package com.example.sparqlbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
        HashSet<String> book=new HashSet<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet resultSet = queryExecution.execSelect();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String ime= String.valueOf(solution.get("ime"));
                String[] parts = ime.split("@");
                String imebook = parts[0];
                book.add(imebook);
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
                String birthDateLiteral = String.valueOf(birthDateNode.asLiteral());
                System.out.println(birthDateLiteral);
                String[] parts = birthDateLiteral.split("\\^\\^");
                String birthDate = parts[0];
                jsonObject.put("birthDate", birthDate);
            }
            if (deathDateNode != null) {
                String deathDateLiteral = String.valueOf(deathDateNode.asLiteral());
                String[] parts = deathDateLiteral.split("\\^\\^");
                String deathDate = parts[0];
                jsonObject.put("deathDate", deathDate);
            }
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }

    @PostMapping("/searchbook")
    public ResponseEntity<String> getBook(@RequestBody String namejson) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(namejson);
        String name = jsonNode.get("name").asText();
        System.out.println(name);
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?authorLabel ?country ?abstract ?label ?language ?releaseDate ?genres WHERE{ dbr:The_Adventures_of_Tom_Sawyer   dbp:genre ?genre; dbo:author?author ; dbo:abstract ?abstract; rdfs:label ?label; dbp:releaseDate ?releaseDate; dbp:country ?country; dbp:language ?language; dbp:genre ?genre. ?author rdfs:label ?authorLabel. ?genre rdfs:label ?genres FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANGMATCHES(LANG(?authorLabel), 'en')) FILTER(LANGMATCHES(LANG(?genres), 'en')) FILTER(LANGMATCHES(LANG(?label), 'en'))}";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            jsonObject.put("label", solution.get("label"));
            jsonObject.put("releaseDate", solution.get("releaseDate"));
            jsonObject.put("country", solution.get("country"));
            jsonObject.put("language", solution.get("language"));
            jsonObject.put("abstract", solution.get("abstract"));
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
    @GetMapping("/romanticism")
    public ResponseEntity<String> Romanticism() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?bname ?abstract ?label ?birth ?death WHERE{ ?move dbo:movement dbr:Romanticism . ?move rdfs:label ?bname  .  ?move dbo:birthDate ?birth  .   ?move dbo:deathDate ?death . dbr:Romanticism   dbo:abstract ?abstract; rdfs:label ?label FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANGMATCHES(LANG(?bname), 'en')) FILTER(LANGMATCHES(LANG(?label), 'en'))}\n";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        List<String> authors=new ArrayList<>();
        List<String> birth=new ArrayList<>();
        List<String> death=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            String abstractt= String.valueOf(solution.get("abstract"));
            String abniza [];
            abniza=abstractt.split("@");
            jsonObject.put("abstract", abniza[0]);
            String label= String.valueOf(solution.get("label"));
            String labelniza [];
            labelniza=label.split("@");
            jsonObject.put("label", labelniza[0]);
            while (result.hasNext()) {
                QuerySolution solutionn = result.nextSolution();
                String ime= String.valueOf(solutionn.get("bname"));
                String [] niza;
                niza=ime.split("@");
                authors.add(niza[0]);
            }
            jsonObject.put("authors", authors);
            ResultSet resultt = queryExecution.execSelect();
            while (resultt.hasNext()) {
                QuerySolution solutionnn = resultt.nextSolution();
                String b= String.valueOf(solutionnn.get("birth"));
                birth.add(b.substring(0, 10));
            }
            jsonObject.put("birth", birth);
            ResultSet resulttt = queryExecution.execSelect();
            while (resulttt.hasNext()) {
                QuerySolution solutionnnn = resulttt.nextSolution();
                String d= String.valueOf(solutionnnn.get("birth"));
                death.add(d.substring(0,10));
            }
            jsonObject.put("death", death);
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
    @GetMapping("/literaryrealism")
    public ResponseEntity<String> Literary_realism() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?bname ?abstract ?label ?birth ?death WHERE{ ?move dbo:movement dbr:Literary_realism . ?move rdfs:label ?bname  .  ?move dbo:birthDate ?birth  .   ?move dbo:deathDate ?death . dbr:Literary_realism   dbo:abstract ?abstract; rdfs:label ?label FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANGMATCHES(LANG(?bname), 'en')) FILTER(LANGMATCHES(LANG(?label), 'en'))}\n";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        List<String> authors=new ArrayList<>();
        List<String> birth=new ArrayList<>();
        List<String> death=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            String abstractt= String.valueOf(solution.get("abstract"));
            String abniza [];
            abniza=abstractt.split("@");
            jsonObject.put("abstract", abniza[0]);
            String label= String.valueOf(solution.get("label"));
            String labelniza [];
            labelniza=label.split("@");
            jsonObject.put("label", labelniza[0]);
            while (result.hasNext()) {
                QuerySolution solutionn = result.nextSolution();
                String ime= String.valueOf(solutionn.get("bname"));
                String [] niza;
                niza=ime.split("@");
                authors.add(niza[0]);
            }
            jsonObject.put("authors", authors);
            ResultSet resultt = queryExecution.execSelect();
            while (resultt.hasNext()) {
                QuerySolution solutionnn = resultt.nextSolution();
                String b= String.valueOf(solutionnn.get("birth"));
                birth.add(b.substring(0, 10));
            }
            jsonObject.put("birth", birth);
            ResultSet resulttt = queryExecution.execSelect();
            while (resulttt.hasNext()) {
                QuerySolution solutionnnn = resulttt.nextSolution();
                String d= String.valueOf(solutionnnn.get("birth"));
                death.add(d.substring(0,10));
            }
            jsonObject.put("death", death);
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
    @GetMapping("/modernism")
    public ResponseEntity<String> modernism() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?bname ?abstract ?label ?birth ?death WHERE{ ?move dbo:movement dbr:Literary_modernism . ?move rdfs:label ?bname  .  ?move dbo:birthDate ?birth  .   ?move dbo:deathDate ?death . dbr:Literary_modernism   dbo:abstract ?abstract; rdfs:label ?label FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANGMATCHES(LANG(?bname), 'en')) FILTER(LANGMATCHES(LANG(?label), 'en'))}\n";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        List<String> authors=new ArrayList<>();
        List<String> birth=new ArrayList<>();
        List<String> death=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            String abstractt= String.valueOf(solution.get("abstract"));
            String abniza [];
            abniza=abstractt.split("@");
            jsonObject.put("abstract", abniza[0]);
            String label= String.valueOf(solution.get("label"));
            String labelniza [];
            labelniza=label.split("@");
            jsonObject.put("label", labelniza[0]);
            while (result.hasNext()) {
                QuerySolution solutionn = result.nextSolution();
                String ime= String.valueOf(solutionn.get("bname"));
                String [] niza;
                niza=ime.split("@");
                authors.add(niza[0]);
            }
            jsonObject.put("authors", authors);
            ResultSet resultt = queryExecution.execSelect();
            while (resultt.hasNext()) {
                QuerySolution solutionnn = resultt.nextSolution();
                String b= String.valueOf(solutionnn.get("birth"));
                birth.add(b.substring(0, 10));
            }
            jsonObject.put("birth", birth);
            ResultSet resulttt = queryExecution.execSelect();
            while (resulttt.hasNext()) {
                QuerySolution solutionnnn = resulttt.nextSolution();
                String d= String.valueOf(solutionnnn.get("birth"));
                death.add(d.substring(0,10));
            }
            jsonObject.put("death", death);
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
    @GetMapping("/postmodernism")
    public ResponseEntity<String> postmodernism() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?bname ?abstract ?label ?birth ?death WHERE{ ?move dbo:movement dbr:Postmodern_literature . ?move rdfs:label ?bname  .  ?move dbo:birthDate ?birth  .   ?move dbo:deathDate ?death . dbr:Postmodern_literature   dbo:abstract ?abstract; rdfs:label ?label FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANGMATCHES(LANG(?bname), 'en')) FILTER(LANGMATCHES(LANG(?label), 'en'))}\n";
        Query sparqlQuery = QueryFactory.create(query);
        QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
        System.out.println("ok");
        JSONObject jsonObject = new JSONObject();
        List<String> authors=new ArrayList<>();
        List<String> birth=new ArrayList<>();
        List<String> death=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            String abstractt= String.valueOf(solution.get("abstract"));
            String abniza [];
            abniza=abstractt.split("@");
            jsonObject.put("abstract", abniza[0]);
            String label= String.valueOf(solution.get("label"));
            String labelniza [];
            labelniza=label.split("@");
            jsonObject.put("label", labelniza[0]);
            while (result.hasNext()) {
                QuerySolution solutionn = result.nextSolution();
                String ime= String.valueOf(solutionn.get("bname"));
                String [] niza;
                niza=ime.split("@");
                authors.add(niza[0]);
            }
            jsonObject.put("authors", authors);
            ResultSet resultt = queryExecution.execSelect();
            while (resultt.hasNext()) {
                QuerySolution solutionnn = resultt.nextSolution();
                String b= String.valueOf(solutionnn.get("birth"));
                birth.add(b.substring(0, 10));
            }
            jsonObject.put("birth", birth);
            ResultSet resulttt = queryExecution.execSelect();
            while (resulttt.hasNext()) {
                QuerySolution solutionnnn = resulttt.nextSolution();
                String d= String.valueOf(solutionnnn.get("birth"));
                death.add(d.substring(0,10));
            }
            jsonObject.put("death", death);
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
}
