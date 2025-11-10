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

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)

public class Version implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "version", nullable = false)
    String version;

    @Column(name = "url",nullable = false, unique = true)
    String url;

    @Column(name = "hash",nullable = false, unique = true)
    String hash;

    @Column(name = "release_notes",nullable = false)
    String releaseNotes;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    Application application;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    Attachment attachment;

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
    Long createdByUserId;

    @Column(name = "updated_by_user_id")
    Long updatedByUserId;

    @JsonIgnore
    @CreationTimestamp
    Date createdAt;

    @JsonIgnore
    @UpdateTimestamp
    Date updatedAt;

}
