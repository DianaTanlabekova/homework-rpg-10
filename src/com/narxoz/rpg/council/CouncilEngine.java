package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.guild.GuildMember;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import java.util.List;

public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        System.out.println("\n--- CouncilEngine: Beginning War Council ---");
        System.out.println("Party:");
        for (Hero hero : party) {
            System.out.println("  " + hero);
        }

        int questsTraversed = 0;

        System.out.println("\n[CouncilEngine - Iterator 1] Ordered traversal:");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.println("  Planning quest: " + q.getTitle() + " | " + q.getPriority() + " | " + q.getRewardGold() + "g");
            hall.dispatch("orders", new AnonymousMember("Council", hall), "Assigning quest: " + q.getTitle());
            if (q.isUrgent() || q.getPriority() == QuestPriority.URGENT) {
                hall.dispatch("urgent", new AnonymousMember("Council", hall), "Urgent: " + q.getTitle());
            }
        }

        System.out.println("\n[CouncilEngine - Iterator 2] Priority >= HIGH:");
        QuestIterator highPriority = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highPriority.hasNext()) {
            Quest q = highPriority.next();
            System.out.println("  High-priority: " + q.getTitle());
            hall.dispatch("supplies", new AnonymousMember("Council", hall), "Extra supplies for: " + q.getTitle());
            hall.dispatch("healing", new AnonymousMember("Council", hall), "Medical prep for: " + q.getTitle());
        }

        int messagesRouted = 0;
        int membersNotified = 0;
        if (hall instanceof GuildHall guildHall) {
            messagesRouted = guildHall.getTotalDispatches();
            membersNotified = guildHall.getTotalNotifications();
        }

        System.out.println("\n--- CouncilEngine: War Council Complete ---");
        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }

    private static class AnonymousMember extends GuildMember {
        public AnonymousMember(String name, GuildMediator mediator) {
            super(name, mediator);
        }
        @Override
        public void receive(String topic, GuildMember from, String payload) {}
    }
}