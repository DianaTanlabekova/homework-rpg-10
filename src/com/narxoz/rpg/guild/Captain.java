    package com.narxoz.rpg.guild;

    /**
     * Guild officer responsible for orders and mission coordination.
     */
    public class Captain extends GuildMember {

        public Captain(String name, GuildMediator mediator) {
            super(name, mediator);
        }

        public void issueOrder(String topic, String payload) {
            // TODO: send a command message through the mediator.
            getMediator().dispatch(topic, this, payload);
        }

        @Override
        public void receive(String topic, GuildMember from, String payload) {
            // TODO: react to a guild-hall message without calling another colleague directly.
        }
    }
