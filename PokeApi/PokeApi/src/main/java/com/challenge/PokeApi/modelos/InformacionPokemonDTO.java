package com.challenge.PokeApi.modelos;

import java.util.List;

public class InformacionPokemonDTO {

    private String idPokemon;
    private String name;
    private List<String> abilities;
    private List<String> types;
    private List<String> stats;
    private double weight;
    private String image;
    private List<String> moves;
    private String description;
    private String height;

    public InformacionPokemonDTO(){}
    
    public InformacionPokemonDTO(String idPokemon, String name ,  List<String> abilities, List<String> types, double weight,String image){
        this.idPokemon = idPokemon;
        this.name = name;
        this.abilities = abilities;
        this. types = types;
        this.weight = weight;
        this.image = image;
    }
    
    public List<String> getAbilities() {
        return abilities;
    }
    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public List<String> getTypes() {
        return types;
    }
    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getMoves() {
        return moves;
    }

    public void setMoves(List<String> moves) {
        this.moves = moves;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(String idPokemon) {
        this.idPokemon = idPokemon;
    }

    public List<String> getStats() {
        return stats;
    }

    public void setStats(List<String> stats) {
        this.stats = stats;
    }
    
    
}
