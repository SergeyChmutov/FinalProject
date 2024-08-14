package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    @EqualsAndHashCode.Include
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String image;
    @OneToOne
    @JoinColumn(name = "id")
    private UserAvatar avatar;
}
