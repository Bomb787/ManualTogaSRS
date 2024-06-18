package io.github.bomb787.a320.entities.a320;

import net.minecraft.util.math.MathHelper;

public class Hydraulics {

    public final SubSystem GREEN = new SubSystem(100);
    public final SubSystem BLUE = new SubSystem(60);
    public final SubSystem YELLOW = new SubSystem(75);

   protected class SubSystem {

       private static final float FUNNY_CONSTANT = 0.0556f;

        //Max hydraulic pressure in PSI
        private static final short MAX_PRESS = 3000;
        //Percentage of hydraulic fluid
        private int fluidAmount = 100;
        private int capacity;
        private boolean isLeaking;
        //How bad is the leak? 0 means not leaking, 100 means it's all gone in one tick.
        private int leakingSpeed;
        private boolean isPowered;
        //Percentage of overheat status, >=100 means overheating.
        private float pumpOverheat;
        //Should the fault light on the overhead turn on?
        private boolean fault;
        private int pressure;
        private int targetPressure;
        //Ticks where the
        private int pressureDifferenceTime;

       protected SubSystem(int capacity) {
           this.capacity = capacity;
       }

        /**
         * Turns the hydraulic system on and off.
         * @param on Do you want to turn the system on?
         * @param targetPressure 3000 for normal operations, 2500 on the blue system if powered by PTU.
         */
        public void setPower(boolean on, int targetPressure) {
            if(on) {
                this.isPowered = true;
            }
            else
                this.isPowered = false;
        }

        /**
         * Sets the leaking status of the system.
         * @param leaking Is this hydraulic system leaking?
         */
        public void setLeaking(boolean leaking) {
            if(leaking)
                this.isLeaking = true;
            else
                this.isLeaking = false;
        }

        /**
         * Sets the leaking status of the system.
         * @param on Do you want to turn the system on?
         */
        public void setLeakingSpeed(int speed) {
            this.leakingSpeed = MathHelper.clamp(speed, 0, 100);
        }

        public void tick() {
            if(this.isLeaking && this.fluidAmount > 0) {
                if(this.isPowered) {
                    this.fluidAmount-=this.leakingSpeed;
                } else {
                    //Applies a curve of (x^2)/100 where x is the speed of the leak if the system is unpowered.
                    this.fluidAmount-=(this.leakingSpeed * this.leakingSpeed) / 100;
                }
                this.fluidAmount = Math.max(this.fluidAmount, 0);
            }
            if(this.isPowered) {
                if(this.fluidAmount < 50) {
                    this.pumpOverheat+=(FUNNY_CONSTANT-(this.fluidAmount * this.fluidAmount)/45000f);
                    if(this.pumpOverheat>=100 || this.fluidAmount <= 0.035 * this.capacity) {
                        this.fault = true;
                        this.isPowered = false;
                    }
                } else {
                    /*if(this.pressure < this.targetPressure) {
                        this.pressure+=150;
                    }
                    this.pressure = Math.min(this.pressure, this.targetPressure);*/
                }
            } else {
                /*this.targetPressure = 0;
                if(this.pressure > 0) {
                    this.pressure-=150;
                }
                this.pressure = Math.max(this.pressure, 0);*/
            }

        }

       /**
        * Sets the desired pressure for the hydraulic system.
        * @param pressure Desired pressure.
        */
       public void setTargetPressure(int pressure) {
            this.targetPressure = pressure;
       }

    }

    protected class PTU {

    }

}