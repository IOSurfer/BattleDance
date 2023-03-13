package com.bjtu.battledance;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum EnemyState implements State<Enemy> {
    DEFEND(){
        @Override
        public void enter(Enemy entity) {

        }

        @Override
        public void update(Enemy entity) {
            entity.denfend();
            if(entity.getDeltaTime()>500) {
                entity.setDeltaTime(0);
                if (!entity.isInRange() && entity.isHealthLow()) {
                    entity.getStateMachine().changeState(RANGE);
                } else if (!entity.isInRange()) {
                    entity.getStateMachine().changeState(RANGE);
                } else if (entity.isHealthLow() && entity.isUnderAttack()) {
                    entity.getStateMachine().changeState(ATTACK);
                }
            }
        }

        @Override
        public void exit(Enemy entity) {

        }

        @Override
        public boolean onMessage(Enemy entity, Telegram telegram) {
            return false;
        }
    },
    ATTACK(){
        @Override
        public void enter(Enemy entity) {

        }

        @Override
        public void update(Enemy entity) {
            entity.attack();
            if(entity.getDeltaTime()>500) {
                entity.setDeltaTime(0);
                if (!entity.isInRange()) {
                    entity.getStateMachine().changeState(RANGE);
                } else if (entity.isHealthLow() && entity.isUnderAttack()) {
                    entity.getStateMachine().changeState(DEFEND);
                } else if (entity.isUnderAttack()) {
                    entity.getStateMachine().changeState(ATTACK);
                }
            }
        }

        @Override
        public void exit(Enemy entity) {

        }

        @Override
        public boolean onMessage(Enemy entity, Telegram telegram) {
            return false;
        }
    },
    RANGE(){
        @Override
        public void enter(Enemy entity) {

        }

        @Override
        public void update(Enemy entity) {
            entity.range();
            if(entity.isInRange()){
                entity.getStateMachine().changeState(ATTACK);
            }
            if(entity.isUnderAttack()){
                entity.getStateMachine().changeState(ATTACK);
            }

        }

        @Override
        public void exit(Enemy entity) {

        }

        @Override
        public boolean onMessage(Enemy entity, Telegram telegram) {
            return false;
        }
    },
    ESCAPE() {
        @Override
        public void enter(Enemy entity) {

        }

        @Override
        public void update(Enemy entity) {
            entity.escape();
            if(entity.isUnderAttack()){
                entity.getStateMachine().changeState(ATTACK);
            }
            else if(!entity.isInRange()){
                entity.getStateMachine().changeState(RANGE);
            }
        }

        @Override
        public void exit(Enemy entity) {

        }

        @Override
        public boolean onMessage(Enemy entity, Telegram telegram) {
            return false;
        }
    }
}
