package com.narxoz.rpg.guild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuildHall implements GuildMediator {

    private final Map<String, List<GuildMember>> membersByTopic = new HashMap<>();
    private int totalDispatches = 0;
    private int totalNotifications = 0;

    @Override
    public void register(GuildMember member) {
        if (member instanceof Quartermaster) {
            addSubscriber("supplies", member);
            addSubscriber("orders", member);
            addSubscriber("rewards", member);
        } else if (member instanceof Scout) {
            addSubscriber("scouting", member);
            addSubscriber("orders", member);
            addSubscriber("urgent", member);
        } else if (member instanceof Healer) {
            addSubscriber("healing", member);
            addSubscriber("urgent", member);
        } else if (member instanceof Captain) {
            addSubscriber("orders", member);
            addSubscriber("scouting", member);
            addSubscriber("supplies", member);
            addSubscriber("healing", member);
            addSubscriber("urgent", member);
        }
    }

    @Override
    public void dispatch(String topic, GuildMember from, String payload) {
        totalDispatches++;
        List<GuildMember> subscribers = subscribersFor(topic);
        for (GuildMember member : subscribers) {
            if (member != from) {
                member.receive(topic, from, payload);
                totalNotifications++;
            }
        }
    }

    public int getTotalDispatches() {
        return totalDispatches;
    }

    public int getTotalNotifications() {
        return totalNotifications;
    }

    protected void addSubscriber(String topic, GuildMember member) {
        membersByTopic.computeIfAbsent(topic, key -> new ArrayList<>()).add(member);
    }

    protected List<GuildMember> subscribersFor(String topic) {
        return membersByTopic.getOrDefault(topic, List.of());
    }
}