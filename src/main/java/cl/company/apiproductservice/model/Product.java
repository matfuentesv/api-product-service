package cl.company.apiproductservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "No puede ingresar un name vacio")
    @NotNull(message = "No puede ingresar un name nulo")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "No puede ingresar un description vacio")
    @NotNull(message = "No puede ingresar un description nulo")
    private String description;

    @Column(name = "price")
    @NotNull(message = "No puede ingresar un price nulo")
    private int price;

    @Column(name = "stock")
    @NotNull(message = "No puede ingresar un stock nulo")
    private int stock;

}
