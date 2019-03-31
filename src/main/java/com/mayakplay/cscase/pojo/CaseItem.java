/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package com.mayakplay.cscase.pojo;

import net.minecraft.item.ItemStack;

public class CaseItem {
    private ItemStack item;
    private int rarity;

    public CaseItem(ItemStack item, int rarity) {
        this.item = item;
        this.rarity = rarity;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public int getRarity() {
        return this.rarity;
    }
}

