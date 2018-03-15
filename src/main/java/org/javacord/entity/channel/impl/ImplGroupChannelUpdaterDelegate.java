package org.javacord.entity.channel.impl;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.javacord.entity.channel.GroupChannel;
import org.javacord.entity.channel.GroupChannelUpdaterDelegate;
import org.javacord.util.rest.RestEndpoint;
import org.javacord.util.rest.RestMethod;
import org.javacord.util.rest.RestRequest;

import java.util.concurrent.CompletableFuture;

/**
 * The implementation of {@link GroupChannelUpdaterDelegate}.
 */
public class ImplGroupChannelUpdaterDelegate implements GroupChannelUpdaterDelegate {

    /**
     * The channel to update.
     */
    protected final GroupChannel channel;

    /**
     * The name to update.
     */
    protected String name = null;

    /**
     * Creates a new group channel updater delegate.
     *
     * @param channel The channel to update.
     */
    public ImplGroupChannelUpdaterDelegate(GroupChannel channel) {
        this.channel = channel;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public CompletableFuture<Void> update() {
        boolean patchChannel = false;
        ObjectNode body = JsonNodeFactory.instance.objectNode();
        if (name != null) {
            body.put("name", name);
            patchChannel = true;
        }
        if (patchChannel) {
            return new RestRequest<Void>(channel.getApi(), RestMethod.PATCH, RestEndpoint.CHANNEL)
                    .setUrlParameters(channel.getIdAsString())
                    .setBody(body)
                    .execute(result -> null);
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }

}