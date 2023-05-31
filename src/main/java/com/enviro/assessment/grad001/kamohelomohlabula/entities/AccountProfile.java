package com.enviro.assessment.grad001.kamohelomohlabula.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_profile", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"})})
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode
public class AccountProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "image_link")
    private String httpImageLink;

}
