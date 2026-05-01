package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.Captain;
import com.narxoz.rpg.guild.GuildHall;
import com.narxoz.rpg.guild.Healer;
import com.narxoz.rpg.guild.Loremaster;
import com.narxoz.rpg.guild.Quartermaster;
import com.narxoz.rpg.guild.Scout;
import com.narxoz.rpg.quest.Quest;
import com.narxoz.rpg.quest.QuestIterator;
import com.narxoz.rpg.quest.QuestLog;
import com.narxoz.rpg.quest.QuestPriority;
import com.narxoz.rpg.quest.RewardSortedQuestIterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Demo: Iterator + Mediator ===");

        Hero warrior = new Hero("Raiden", 120, 30, 15, 12, 80);
        Hero mage = new Hero("Kazuha", 75, 100, 45, 8, 60);
        List<Hero> party = List.of(warrior, mage);

        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Clear the Goblin Camp",      QuestPriority.NORMAL, 200, false));
        questLog.add(new Quest("Escort the Merchant",        QuestPriority.LOW,    100, false));
        questLog.add(new Quest("Break the Tomb Curse",       QuestPriority.HIGH,   500, false));
        questLog.add(new Quest("Rescue the Captured Knight", QuestPriority.URGENT, 800, true));
        questLog.add(new Quest("Map the Darkwood",           QuestPriority.NORMAL, 300, false));
        questLog.add(new Quest("Slay the Shadow Drake",      QuestPriority.HIGH,   650, false));

        GuildHall hall = new GuildHall();
        Quartermaster quartermaster = new Quartermaster("Borin",    hall);
        Scout         scout         = new Scout("Lirien",           hall);
        Healer        healer        = new Healer("Sister Mira",     hall);
        Captain       captain       = new Captain("Commander Vael", hall);
        Loremaster    loremaster    = new Loremaster("Elder Tomas", hall);

        System.out.println("\n=== PART 1: ITERATOR PATTERN ===");

        System.out.println("\n[Iterator 1] Quests in the order they were received:");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            System.out.println("  " + ordered.next().getTitle());
        }

        System.out.println("\n[Iterator 2] Quests from most recent to earliest:");
        QuestIterator reverse = questLog.reverse();
        while (reverse.hasNext()) {
            System.out.println("  " + reverse.next().getTitle());
        }

        System.out.println("\n[Iterator 3] Quests ranked HIGH or above:");
        QuestIterator highOnly = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highOnly.hasNext()) {
            Quest q = highOnly.next();
            System.out.println("  " + q.getTitle() + " (" + q.getPriority() + ")");
        }

        System.out.println("\n[Iterator 4 - Extension] Quests ranked by gold reward:");
        QuestIterator rewardSorted = new RewardSortedQuestIterator(questLog);
        while (rewardSorted.hasNext()) {
            Quest q = rewardSorted.next();
            System.out.println("  " + q.getTitle() + " -> " + q.getRewardGold() + "g");
        }

        System.out.println("\n=== PART 2: MEDIATOR PATTERN ===");

        System.out.println("\n-- Commander issues deployment orders --");
        captain.issueOrder("orders", "All units advance toward the Goblin Camp before sunrise");

        System.out.println("\n-- Scout delivers field intelligence --");
        scout.reportRoute("scouting", "Enemy patrol detected along the eastern ridge");

        System.out.println("\n-- Quartermaster submits supply request --");
        quartermaster.requestSupplies("supplies", "Requesting three days of provisions for six members");

        System.out.println("\n-- Healer raises medical concern --");
        healer.prepareAid("healing", "Preparing antidotes in anticipation of the tomb curse");

        System.out.println("\n-- Emergency alert broadcast --");
        captain.issueOrder("urgent", "Knight abduction confirmed - full mobilization required!");

        System.out.println("\n-- Loremaster reveals arcane findings --");
        captain.issueOrder("lore", "Research the tomb curse origins");
        captain.issueOrder("curse", "Identify counter-ritual for the tomb curse");
        captain.issueOrder("history", "Pull records on Necromancer Vrak");

        System.out.println("\n=== PART 3: COUNCIL ENGINE ===");
        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        System.out.println("\n=== FINAL RESULT ===");
        System.out.println(result);
    }
}
