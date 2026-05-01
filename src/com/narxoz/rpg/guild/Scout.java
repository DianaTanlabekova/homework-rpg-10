package com.narxoz.rpg.guild;

public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void reportRoute(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Scout " + getName() + "] received on topic='" + topic + "' from " + from.getName() + ": " + payload);
        if ("orders".equals(topic)) {
            System.out.println("  -> Scout preparing reconnaissance for: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("  -> Scout dispatching emergency patrol! " + payload);
        }
    }
}