package com.challenge.PokeApi.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.PokeApi.modelos.InformacionPokemonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ServicioPokeApi {

    private static String BASE_URL = "https://pokeapi.co/api/v2/";
    private static String idiomaApi = "es";

    public List<InformacionPokemonDTO> obtenerPokemones() throws IOException {
      String jsonString = this.traerDatos("/pokemon");
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonString);
      List<InformacionPokemonDTO> pokemonList = new ArrayList<>();
  
      if (rootNode.has("results")) {
          JsonNode resultsNode = rootNode.get("results");
          for (JsonNode pokemonNode : resultsNode) {
            String name = pokemonNode.get("name").asText();

            String[] partes = (pokemonNode.get("url").asText()).split("/");
            String idPokemon = partes[partes.length - 1];
             
            String jsonPokemonString = this.traerDatos("/pokemon/" + name);

            JsonNode nodePokemons = objectMapper.readTree(jsonPokemonString);

            List<String> abilities = this.obtenerArrayAPartirDeUnNodo(nodePokemons,"abilities","ability");

            String image = nodePokemons.get("sprites").get("front_shiny").asText();

            List<String> types = this.obtenerArrayAPartirDeUnNodo(nodePokemons,"types","type");

            int weight = nodePokemons.get("weight").asInt();

            InformacionPokemonDTO pokemon = new InformacionPokemonDTO(idPokemon, name, abilities, types, weight, image);
            pokemonList.add(pokemon);
          }
      }     
      return pokemonList;
    }
    
    private String traerDatos(String URL) {
        try {
          StringBuilder resultado = new StringBuilder();
          URL url = new URL(BASE_URL + URL);
          URLConnection conexion = url.openConnection();
          BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));		
          String linea;
          
          while ( (linea = rd.readLine() ) != null) 
            resultado.append(linea);

          rd.close();
          return resultado.toString();
        }
        catch(Exception e) {
          return e.getMessage();
        }
    }

    private List<String> obtenerArrayAPartirDeUnNodo(JsonNode nodoPrincipal, String nodoABuscar, String atributo){
      List<String> listado = new ArrayList<>();
      JsonNode nodos = nodoPrincipal.get(nodoABuscar);
      for (JsonNode nodo : nodos) {
        String name = nodo.get(atributo).get("name").asText();
        listado.add(name);
      }
      return listado;
    }

    public InformacionPokemonDTO obtenerInformacionAdicionalPokemon(String id, String name) throws JsonMappingException, JsonProcessingException{
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonPokemonString = this.traerDatos("/pokemon/" + id);
      JsonNode nodePokemons = objectMapper.readTree(jsonPokemonString);
      List<String> moves = this.obtenerArrayAPartirDeUnNodo(nodePokemons,"moves","move");
      List<String> stats = this.obtenerArrayAPartirDeUnNodo(nodePokemons,"stats","stat");
      String height = nodePokemons.get("height").asText();
      InformacionPokemonDTO pokemon = new InformacionPokemonDTO();
      pokemon.setName(name);
      pokemon.setMoves(moves);
      pokemon.setStats(stats);
      pokemon.setHeight(height);
      pokemon.setDescription(this.descripcionPokemon(id));
      return pokemon;
    }

    public String descripcionPokemon(String idPokemon) throws JsonMappingException, JsonProcessingException{
      String descripcion = "";
      ObjectMapper objectMapper = new ObjectMapper();
      String jsonString = this.traerDatos("/characteristic/"+ idPokemon);
      JsonNode rootNode = objectMapper.readTree(jsonString);
      if (rootNode.has("descriptions")) {
        JsonNode resultsNode = rootNode.get("descriptions");
        for (JsonNode pokemonNode : resultsNode) {
          String name = pokemonNode.get("language").get("name").asText();
          if(name.equals(idiomaApi)){
            descripcion =  pokemonNode.get("description").asText();
          }
        }
    } 
    return descripcion;
    }
}