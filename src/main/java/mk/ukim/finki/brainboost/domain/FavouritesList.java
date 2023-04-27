package mk.ukim.finki.brainboost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.brainboost.domain.enumerations.FavouriteListStatus;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FavouritesList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    private FavouriteListStatus status;

    public FavouritesList(User user) {
        this.user = user;
        this.courses = new ArrayList<>();
        this.status = FavouriteListStatus.CREATED;
    }
}
