package com.propertyservice.property_service.domain.property;

import com.propertyservice.property_service.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "building_remark")
public class BuildingRemark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_remark_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "building_remark", nullable = false, length = 255)
    private String remark;

    @Builder
    public BuildingRemark(Building building, String remark) {
        this.building = building;
        this.remark = remark;
    }
}
