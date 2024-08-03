package com.RecipeSharing.RecipeSharing.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> findAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Iterable<Recipe> iterable = recipeRepository.findAll();
        iterable.forEach(recipes::add);


        return recipes;
    }
}
