package com.back.projectmanagermultitenantback.Models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OrganizationMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
