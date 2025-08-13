package testExampleAPI;

import testExampleAPI.model.Ability;
import testExampleAPI.model.Pokemon;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;

import static testExampleAPI.ServiceEndPoint.BASE_URL;

public class ApiTest {

    private final ServiceEndPoint serviceEndPoint = new ServiceEndPoint();
    public int LimitPokemon = 100;
    public String pokemonName = "raichu";

    @Test
    public void getPokemonListTest_MethodOne() {
        // Extrae la lista de nombres de los Pokémon
        List<String> pokemonNames =
        given()
                        .baseUri(BASE_URL)
        .when()
                        .get("pokemon?limit=" + LimitPokemon)
        .then()
                        .statusCode(200)
                        .extract()
                        .jsonPath()
                        .getList("results.name");


        System.out.println("Pokémon list:");
        pokemonNames.forEach(System.out::println);

        assertTrue(pokemonNames != null && !pokemonNames.isEmpty(), "Pokemon list should not be empty");
        assertTrue(pokemonNames.contains("raichu"), "Pokemon list should contain 'raichu'");
    }

    @Test
    public void getPokemonListTest_MethodTwo() {
        List<Pokemon> pokemones = serviceEndPoint.getPokemones();
        assertNotNull(pokemones, "Pokemon list should not be null");
        assertTrue(pokemones.size() > 0, "Pokemon list should not be empty");
        pokemones.stream().limit(LimitPokemon).forEach(p -> System.out.println(p.getName()));
    }

    @Test
    public void getPokemonAbilitiesAndValidateInAbilityEndpointTest() {

        Pokemon raichu = serviceEndPoint.getPokemonDetails2(pokemonName);

        // Validaciones básicas
        assertNotNull(raichu, "Raichu should not be null");
        assertEquals(pokemonName, raichu.getName(), "Pokemon name should be raichu");
        assertNotNull(raichu.getAbilities(), "Raichu abilities should not be null");
        assertFalse(raichu.getAbilities().isEmpty(), "Raichu should have at least one ability");

        // Print Raichu abilities
        System.out.println("Habilidades de " + pokemonName + ":");
        raichu.getAbilities().forEach(a -> {
            if (a != null && a.getAbility() != null) {
                System.out.println("- " + a.getAbility().getName());
            }
        });

        // Validar nombres y URLs de habilidades
        raichu.getAbilities().forEach(a -> {
            assertNotNull(a.getAbility().getName(), "Ability name should not be null");
            assertFalse(a.getAbility().getName().isEmpty(), "Ability name should not be empty");
            assertNotNull(a.getAbility().getUrl(), "Ability URL should not be null");
            assertFalse(a.getAbility().getUrl().isEmpty(), "Ability URL should not be empty");
            assertNotNull(a.isIs_hidden(), "is_hidden should not be null");
            assertTrue(a.getSlot() > 0, "Slot should be greater than 0");
        });

        // No hay habilidades duplicadas
        List<String> abilityNamesList = raichu.getAbilities()
                .stream()
                .map(a -> a.getAbility().getName())
                .collect(Collectors.toList());
        Set<String> abilityNamesSet = new HashSet<>(abilityNamesList);
        assertEquals(abilityNamesList.size(), abilityNamesSet.size(), "There should be no duplicate abilities");

        // Validar que todas las habilidades existen en el endpoint de habilidades
        List<Ability> allAbilities = serviceEndPoint.getAbilities();
        Set<String> abilityNames = allAbilities.stream()
                .map(Ability::getName)
                .collect(Collectors.toSet());

        abilityNamesList.forEach(ability ->
                assertTrue(abilityNames.contains(ability),
                        "Ability " + ability + " should exist in abilities endpoint"));
    }
}