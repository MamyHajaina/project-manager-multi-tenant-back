package com.back.projectmanagermultitenantback.Models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProjectMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false) @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
}
