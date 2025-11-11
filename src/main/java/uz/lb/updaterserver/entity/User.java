package uz.lb.updaterserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.lb.updaterserver.enums.GeneralStatus;
import uz.lb.updaterserver.enums.RoleEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Entity(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(
        value = JsonInclude.Include.NON_EMPTY,
        content = JsonInclude.Include.NON_NULL)

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false, name = "login")
    String login;

    @JsonIgnore
    @Column(nullable = false, name = "password")
    String password;

    @JsonIgnore
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    RoleEnum role = RoleEnum.ROLE_USER;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Application> applications;

    @JsonIgnore
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    GeneralStatus status = GeneralStatus.ACTIVE;

    @JsonIgnore
    @Column(name = "visible")
    @Builder.Default
    Boolean visible = Boolean.TRUE;


    @Column(name = "created_by_user_id")
    String createdByUserId;

    @Column(name = "updated_by_user_id")
    String updatedByUserId;

    @JsonIgnore
    @CreationTimestamp
    Date createdAt;

    @JsonIgnore
    @UpdateTimestamp
    Date updatedAt;


}
