package com.example.festo.order.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoothInfo {

    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "booth_id"))
    )
    private Long boothId;

    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "owner_id"))
    )
    private Long ownerId;

    public boolean isOwner(Long id) {
        return this.ownerId.equals(id);
    }
}
