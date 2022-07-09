package com.mateuszziomek.modularmonolithstore.modules.user.integration;

import com.mateuszziomek.modularmonolithstore.buildingblocks.infrastructure.message.TestMessageBus;
import com.mateuszziomek.modularmonolithstore.integration.event.UserRegisteredIntegrationEvent;
import com.mateuszziomek.modularmonolithstore.modules.user.UserModule;
import com.mateuszziomek.modularmonolithstore.modules.user.application.command.register.RegisterCommand;
import com.mateuszziomek.modularmonolithstore.modules.user.application.query.getdetailsuser.GetDetailsUserQuery;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserRegisterTest {
    @Test
    void userCanBeRegistered() {
        // Arrange
        var messageBus = new TestMessageBus();
        var sut = UserModule.initialize(messageBus);
        var uuid = UUID.randomUUID();

        // Act
        var result = sut.dispatchCommand(new RegisterCommand(uuid, "username", "password"));
        sut.processMessages(10);

        // Assert
        assertThat(result.isSuccess()).isTrue();

        assertThat(messageBus.publishedMessages.length()).isEqualTo(1);
        var event = (UserRegisteredIntegrationEvent) messageBus.publishedMessages.get(0);
        assertThat(event).isInstanceOf(UserRegisteredIntegrationEvent.class);
        assertThat(event.userId()).isEqualTo(uuid);
        assertThat(event.username()).isEqualTo("username");

        var getUserQueryResult = sut.dispatchQuery(new GetDetailsUserQuery(uuid));
        var cart = getUserQueryResult.get().get();
        assertThat(cart.id()).isEqualTo(uuid);
    }

    @Test
    void usernameMustNotBeInUse() {
        // Arrange
        var messageBus = new TestMessageBus();
        var sut = UserModule.initialize(messageBus);
        var uuid = UUID.randomUUID();
        sut.dispatchCommand(new RegisterCommand(UUID.randomUUID(), "username", "password"));
        sut.processMessages(10);
        messageBus.clearPublishedMessages();

        // Act
        var result = sut.dispatchCommand(new RegisterCommand(uuid, "username", "password"));
        sut.processMessages(10);

        // Assert
        assertThat(result.isFailure()).isTrue();

        assertThat(messageBus.publishedMessages.length()).isZero();

        var getUserQueryResult = sut.dispatchQuery(new GetDetailsUserQuery(uuid));
        assertThat(getUserQueryResult.isSuccess()).isTrue();
        assertThat(getUserQueryResult.get().isEmpty()).isTrue();
    }
}