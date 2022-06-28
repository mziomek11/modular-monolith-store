package com.mateuszziomek.buildingblocks.domain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.util.UUID;

public class AggregateId {
    private final UUID value;

    public AggregateId(final UUID value) {
        Preconditions.checkNotNull(value, "Aggregate id can't be null");

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregateId)) return false;
        AggregateId aggregateId = (AggregateId) o;
        return Objects.equal(value, aggregateId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}