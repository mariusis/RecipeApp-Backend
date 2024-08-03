package com.RecipeSharing.RecipeSharing.recipe;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    int authorId;
    int likes;
    public Recipe(String name,String description,int authorId,int likes){
        this.name = name;
        this.description = description;
        this.authorId = authorId;
        this.likes = likes;

    }


}

