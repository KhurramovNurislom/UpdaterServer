package uz.lb.updaterserver.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.lb.updaterserver.enums.GeneralStatus;

import java.io.Serializable;
import java.util.Date;


@Data
@Builder
@Entity(name = "Domain")
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class Domain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, name = "domain")
    String domain;

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
