package com.example.sparqlbackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> PREFIX dct: <http://purl.org/dc/terms/> SELECT DISTINCT ?ime ?abstract ?birthDate ?birthPlace ?deathDate ?deathPlace ?thumbnail WHERE { ?books dbo:author dbr:"+name+" . ?books rdfs:label ?ime . dbr:"+name+" dbo:abstract ?abstract ; dbp:birthDate ?birthDate ; dbp:birthPlace ?birthPlace ; dbo:deathDate ?deathDate ; dbp:deathPlace ?deathPlace ; dbo:thumbnail ?thumbnail . FILTER(LANGMATCHES(LANG(?abstract), 'en')) FILTER(LANG(?ime) = 'en')}";
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
        String nameget = jsonNode.get("name").asText();
        String name = nameget.replace(" ", "_");
        System.out.println(name);
        String SPARQLEndpoint = "https://dbpedia.org/sparql";
        List<String>listfind=new ArrayList<>();
        listfind.add("dbo:abstract");
        listfind.add("rdfs:label");
        listfind.add("dbp:releaseDate");
        listfind.add("dbp:country");
        listfind.add("dbp:language");
        JSONObject jsonObject = new JSONObject();
        for (int i=0; i<listfind.size(); i++) {
            String [] split=listfind.get(i).split(":");
            try{
                String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?"+split[1]+" WHERE{ dbr:"+name+" "+listfind.get(i)+ "?"+split[1]+" FILTER(LANGMATCHES(LANG(?"+split[1]+"), 'en'))}";
                Query sparqlQuery = QueryFactory.create(query);
                QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
                ResultSet result = e.execSelect();
                QuerySolution solution = result.nextSolution();
                RDFNode addtojson=solution.get(split[1]);
                String addtojsonValue = addtojson.toString().replaceAll("@en", "");
                jsonObject.put(split[1], addtojsonValue);
            }
            catch (Exception e) {
                e.printStackTrace(); // Print the exception details for debugging purposes
                jsonObject.put(split[1], " "); // Set a default value in case of failure
            }
        }
        List<String>listfindsec=new ArrayList<>();
        listfindsec.add("dbo:author");
        listfindsec.add("dbp:genre");
        List<String>listfindmap=new ArrayList<>();
        listfindmap.add("authors");
        listfindmap.add("genres");
        for (int i=0; i<listfindsec.size(); i++) {
            String [] split=listfindsec.get(i).split(":");
            try{
                String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX dbp: <http://dbpedia.org/property/> PREFIX dbo: <http://dbpedia.org/ontology/> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbr: <http://dbpedia.org/resource/> SELECT DISTINCT ?"+listfindmap.get(i)+" WHERE{ dbr:"+name+" "+listfindsec.get(i)+"?"+split[1]+". ?"+split[1]+" rdfs:label ?"+listfindmap.get(i)+". FILTER(LANGMATCHES(LANG(?"+listfindmap.get(i)+"), 'en'))}";
                Query sparqlQuery = QueryFactory.create(query);
                QueryExecution e = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery);
                ResultSet result = e.execSelect();
                QuerySolution solution = result.nextSolution();
                RDFNode addtojson=solution.get(listfindmap.get(i));
                String addtojsonValue = addtojson.toString().replaceAll("@en", "");
                jsonObject.put(listfindmap.get(i), addtojsonValue);
            }
            catch (Exception e) {
                e.printStackTrace();
                jsonObject.put(listfindmap.get(i), " ");
            }
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
        Set<String> authors=new HashSet<>();
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
                String b= String.valueOf(solutionn.get("birth"));
                String d= String.valueOf(solutionn.get("death"));
                String [] niza;
                niza=ime.split("@");
                if(authors.add(niza[0]))
                {
                    birth.add(b.substring(0, 10));
                    death.add(d.substring(0,10));
                }
            }
            jsonObject.put("authors", authors);
            jsonObject.put("birth", birth);
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
        Set<String> authors=new HashSet<>();
        List<String> birth=new ArrayList<>();
        List<String> death=new ArrayList<>();
        try (QueryExecution queryExecution = QueryExecutionFactory.sparqlService(SPARQLEndpoint, sparqlQuery)) {
            ResultSet result = queryExecution.execSelect();
            QuerySolution solution = result.nextSolution();
            String abstractt = String.valueOf(solution.get("abstract"));
            String abniza[];
            abniza = abstractt.split("@");
            jsonObject.put("abstract", abniza[0]);
            String label = String.valueOf(solution.get("label"));
            String labelniza[];
            labelniza = label.split("@");
            jsonObject.put("label", labelniza[0]);
            while (result.hasNext()) {
                QuerySolution solutionn = result.nextSolution();
                String ime = String.valueOf(solutionn.get("bname"));
                String b = String.valueOf(solutionn.get("birth"));
                String d = String.valueOf(solutionn.get("death"));
                String[] niza;
                niza = ime.split("@");
                if (authors.add(niza[0])) {
                    birth.add(b.substring(0, 10));
                    death.add(d.substring(0, 10));
                }
            }
            jsonObject.put("authors", authors);
            jsonObject.put("birth", birth);
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
        Set<String> authors=new HashSet<>();
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
                String ime = String.valueOf(solutionn.get("bname"));
                String b = String.valueOf(solutionn.get("birth"));
                String d = String.valueOf(solutionn.get("death"));
                String[] niza;
                niza = ime.split("@");
                if (authors.add(niza[0])) {
                    birth.add(b.substring(0, 10));
                    death.add(d.substring(0, 10));
                }
            }
            jsonObject.put("authors", authors);
            jsonObject.put("birth", birth);
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
        Set<String> authors=new HashSet<>();
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
                String ime = String.valueOf(solutionn.get("bname"));
                String b = String.valueOf(solutionn.get("birth"));
                String d = String.valueOf(solutionn.get("death"));
                String[] niza;
                niza = ime.split("@");
                if (authors.add(niza[0])) {
                    birth.add(b.substring(0, 10));
                    death.add(d.substring(0, 10));
                }
            }
            jsonObject.put("authors", authors);
            jsonObject.put("birth", birth);
            jsonObject.put("death", death);
        }
        return ResponseEntity.ok().body(jsonObject.toString());
    }
}
