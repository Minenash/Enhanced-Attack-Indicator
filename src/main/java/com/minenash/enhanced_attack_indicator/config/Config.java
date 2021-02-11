package com.minenash.enhanced_attack_indicator.config;

public class Config extends TinyConfig{

    public enum WeaponCoolDownImportance { FIRST, MIDDLE, LAST }

    @Entry public static WeaponCoolDownImportance weaponCoolDownImportance = WeaponCoolDownImportance.MIDDLE;
    @Entry public static boolean showBlockBreaking = true;
    @Entry public static boolean showRangeWeaponDraw = true;
    @Entry public static boolean showItemCooldowns = true;

    @Entry public static boolean disablePickaxesAndShovels = true;
    @Entry public static boolean disableAxes = true;

}
