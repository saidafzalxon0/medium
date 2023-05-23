package uz.nt.mediumclone.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follows {

    @Id
    @GeneratedValue(generator = "followsIdSeq")
    @SequenceGenerator(name = "follows_id_seq", sequenceName = "follows_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne(targetEntity = User.class)
    private User follower;
    @ManyToOne(targetEntity = User.class)
    private User following;
}
