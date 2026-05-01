package com.narxoz.rpg.guild;

public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) {
        super(name, mediator);
    }

    public void issueOrder(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Captain " + getName() + "] received on topic='" + topic + "' from " + from.getName() + ": " + payload);
        if ("scouting".equals(topic)) {
            System.out.println("  -> Captain reviewing scout report and adjusting battle plan.");
        } else if ("supplies".equals(topic)) {
            System.out.println("  -> Captain approving supply allocation: " + payload);
        } else if ("healing".equals(topic)) {
            System.out.println("  -> Captain noting casualty report: " + payload);
        } else if ("urgent".equals(topic)) {
            System.out.println("  -> Captain sounding the alarm! " + payload);
        }
    }
}