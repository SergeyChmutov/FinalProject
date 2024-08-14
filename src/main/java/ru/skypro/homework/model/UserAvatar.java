package ru.skypro.homework.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "avatars")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserAvatar {

    @Id
    @GeneratedValue
    private Integer id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;
    @OneToOne
    @JoinColumn
    private User user;
}
