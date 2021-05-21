package com.minenash.enhanced_attack_indicator.config;

public class Config extends TinyConfig{

    public enum WeaponCoolDownImportance { FIRST, MIDDLE, LAST }
    public enum CooldownType { ATTACK(true,false), MINE(false,true), BOTH(true,true), NEITHER(false,false);
        public boolean attack, mine;
        CooldownType(boolean attack, boolean mine) {
            this.attack = attack;
            this.mine = mine;
        }
    }

    @Entry public static WeaponCoolDownImportance weaponCoolDownImportance = WeaponCoolDownImportance.MIDDLE;
    @Entry public static boolean showRangeWeaponDraw = true;
    @Entry public static boolean showSpecialItemCooldowns = true;

    @Entry public static CooldownType noCooldownItemsCooldown = CooldownType.MINE;
    @Entry public static CooldownType pickaxeAndShovelCooldown = CooldownType.MINE;
    @Entry public static CooldownType axeCooldown = CooldownType.ATTACK;

}
