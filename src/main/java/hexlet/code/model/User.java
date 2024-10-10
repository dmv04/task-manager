package hexlet.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User implements BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;


    private String firstName;


    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 3)

    @JsonIgnore
    private String passwordDigest;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
