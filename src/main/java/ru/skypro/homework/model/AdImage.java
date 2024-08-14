package ru.skypro.homework.model;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "images")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String path;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    @ToString.Exclude
    private byte[] data;
    @OneToOne(mappedBy = "image")
    private Ad ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdImage adImage = (AdImage) o;
        return fileSize == adImage.fileSize
                && Objects.equals(path, adImage.path)
                && Objects.equals(mediaType, adImage.mediaType)
                && Objects.deepEquals(data, adImage.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, fileSize, mediaType, Arrays.hashCode(data));
    }
}