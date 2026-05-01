package com.narxoz.rpg.guild;

public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void prepareAid(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Healer " + getName() + "] received on topic='" + topic + "' from " + from.getName() + ": " + payload);
        if ("healing".equals(topic)) {
            System.out.println("  -> Healer preparing potions and bandages for: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("  -> Healer rushing to emergency triage! " + payload);
        }
    }
}
