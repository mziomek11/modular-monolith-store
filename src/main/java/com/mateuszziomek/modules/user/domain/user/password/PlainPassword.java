package com.mateuszziomek.modules.user.domain.user.password;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.mateuszziomek.modules.user.domain.user.Username;

public class PlainPassword {
    private final String value;

    public PlainPassword(final String value) {
        Preconditions.checkNotNull(value, "Password can't be null");
        Preconditions.checkArgument(value.isBlank(), "Password can't be blank");

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username)) return false;
        PlainPassword plainPassword = (PlainPassword) o;
        return Objects.equal(value, plainPassword.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
