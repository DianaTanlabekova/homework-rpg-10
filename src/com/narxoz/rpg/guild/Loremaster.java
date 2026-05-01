package com.narxoz.rpg.guild;

public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) {
        super(name, mediator);
        if (mediator instanceof GuildHall hall) {
            hall.addSubscriber("lore", this);
            hall.addSubscriber("curse", this);
            hall.addSubscriber("history", this);
        }
    }

    public void shareLore(String topic, String payload) {
        getMediator().dispatch(topic, this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        System.out.println("[Loremaster " + getName() + "] received on topic='" + topic + "' from " + from.getName() + ": " + payload);
        if ("lore".equals(topic)) {
            System.out.println("  -> Loremaster recording ancient knowledge: " + payload);
        } else if ("curse".equals(topic)) {
            System.out.println("  -> Loremaster identifying curse pattern: " + payload);
        } else if ("history".equals(topic)) {
            System.out.println("  -> Loremaster cross-referencing historical records for: " + payload);
        }
    }
}
