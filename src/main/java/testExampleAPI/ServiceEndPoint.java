package testExampleAPI;

import testExampleAPI.model.Ability;
import testExampleAPI.model.Pokemon;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public class ServiceEndPoint {

    public static final String BASE_URL = "https://pokeapi.co/api/v2/";

    public List<Pokemon> getPokemones() {
        Response response = RestAssured.get(BASE_URL + "pokemon?limit=2500");
        return response.jsonPath().getList("results", Pokemon.class);
    }

    public Pokemon getPokemonDetails2(String pokemonName) {
        Response response = RestAssured.get(BASE_URL + "pokemon/" + pokemonName);
        System.out.println("Full Info Raichu ="+response.asString());
        return response.as(Pokemon.class);
    }

    public List<Ability> getAbilities() {
        Response response = RestAssured.get(BASE_URL + "ability?limit=100");
        return response.jsonPath().getList("results", Ability.class);
    }
}