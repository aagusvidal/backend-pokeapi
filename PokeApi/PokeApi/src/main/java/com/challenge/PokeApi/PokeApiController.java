package com.challenge.PokeApi;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.PokeApi.modelos.InformacionPokemonDTO;
import com.challenge.PokeApi.servicios.ServicioPokeApi;

@RestController
@RequestMapping("/api")
public class PokeApiController {

    @Autowired
    private ServicioPokeApi pokeApi;

    @GetMapping("/pokemons")
	public List<InformacionPokemonDTO> pokemones() throws IOException{
		return pokeApi.obtenerPokemones();
	}

    @GetMapping("/pokemon/info")
    public InformacionPokemonDTO getInformacionAdicionalPokemon(@RequestBody(required = false) InformacionPokemonDTO pokemon) throws IOException {
        return pokeApi.obtenerInformacionAdicionalPokemon(pokemon);
    }
}
