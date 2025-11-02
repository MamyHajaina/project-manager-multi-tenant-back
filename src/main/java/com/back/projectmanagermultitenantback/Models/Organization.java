package com.back.projectmanagermultitenantback.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String slug;

    @Column(nullable = false, length = 150)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
