package com.narxoz.rpg.guild;

public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void requestSupplies(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Quartermaster " + getName() + "] received on topic='" + topic + "' from " + from.getName() + ": " + payload);
        if ("orders".equals(topic)) {
            System.out.println("  -> Quartermaster preparing gear for mission: " + payload);
        } else if ("supplies".equals(topic)) {
            System.out.println("  -> Quartermaster logging supply request: " + payload);
        } else if ("rewards".equals(topic)) {
            System.out.println("  -> Quartermaster cataloguing reward: " + payload);
        }
    }
}
