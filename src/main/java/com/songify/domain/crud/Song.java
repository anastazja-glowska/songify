package com.songify.domain.crud;

import jakarta.persistence.*;
import lombok.*;
import com.songify.util.BaseEntity;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "song",
indexes = @Index(
        name = "idx_song_name",
        columnList = "name"
))
 public class Song extends BaseEntity {
    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
    sequenceName = "song_id_seq",
    allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage songLanguage;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Genre genre;


   Song(String name) {
      this.name = name;
   }

   Song(String name, Instant releaseDate, Long duration, SongLanguage songLanguage) {
      this.name = name;
      this.releaseDate = releaseDate;
      this.duration = duration;
      this.songLanguage = songLanguage;
   }
}
