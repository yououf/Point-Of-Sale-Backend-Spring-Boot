/*
 * Caisse Enregistreuse App - API REST
 * COded By Alias King - Younes OUFRID !!
 * Mail : oufridyounes@gmail.com
 * MNS team coders
 * */

package com.lillygourmet.cash.register.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * The type User.
 *
 * @author King
 */

@Entity
@Table(	name = "subcategories")
@EntityListeners(AuditingEntityListener.class)
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(max = 155)
    private String nom;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = true)
    private String imagelink;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Category category;

    public SubCategory() {
    }

    public SubCategory(Long id, @NotBlank @Size(max = 155) String nom, @NotBlank @Size(max = 255) String description, @NotBlank @Size(max = 255) String imagelink, Category category) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.imagelink = imagelink;
        this.category = category;
    }

    public SubCategory(@NotBlank @Size(max = 155) String nom, @NotBlank @Size(max = 255) String description, @NotBlank @Size(max = 255) String imagelink, Category category) {
        this.nom = nom;
        this.description = description;
        this.imagelink = imagelink;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", imagelink='" + imagelink + '\'' +
                ", category=" + category +
                '}';
    }
}