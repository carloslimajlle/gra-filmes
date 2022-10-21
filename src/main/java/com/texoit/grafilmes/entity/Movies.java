package com.texoit.grafilmes.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author Carlos Lima on 20/10/2022
 */
@Data
@Entity(name = "movies")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movies {

    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "generator", strategy = "uuid2")
    @Column(nullable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "year")
    private int year;

    @Column(name = "title")
    private String title;

    @Column(name = "studios")
    private String studios;

    @Column(name = "producers")
    private String producers;

    @Column(name = "winner", length = 3)
    private String winner;
}
