/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 */
package com.mayakplay.cscase.network;

import com.mayakplay.cscase.network.Recieve;
import com.mayakplay.cscase.pojo.Case;
import com.mayakplay.cscase.pojo.CaseItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PacketsDecoder {
    public static List<Case> getCases() {
        ArrayList<Case> cases = new ArrayList<Case>();
        String[] decode = Recieve.CASES_LIST.split(",");
        for (int i = 0; i < decode.length / 3; ++i) {
            String name = decode[i * 3];
            int price = Integer.parseInt(decode[i * 3 + 1]);
            String texture = decode[i * 3 + 2];
            cases.add(new Case(name, price, texture));
        }
        return cases;
    }

    public static List<CaseItem> getCaseItemsList() {
        ArrayList<CaseItem> items = new ArrayList<CaseItem>();
        String[] decode = Recieve.CURRENT_CASE_ITEMS_LIST.split(",");
        for (int i = 0; i < decode.length / 3; ++i) {
            int id = Integer.parseInt(decode[i * 3]);
            int meta = Integer.parseInt(decode[i * 3 + 1]);
            int rarity = Integer.parseInt(decode[i * 3 + 2]);
            ItemStack is = new ItemStack(Item.getItemById((int)id));
            is.setItemDamage(meta);
            items.add(new CaseItem(is, rarity));
        }
        return items;
    }

    public static List<CaseItem> getRandomItemsForRoll() {
        int decode;
        ArrayList<CaseItem> itemsFin = new ArrayList<CaseItem>();
        ArrayList<CaseItem> items1 = new ArrayList<CaseItem>();
        ArrayList<CaseItem> items2 = new ArrayList<CaseItem>();
        ArrayList<CaseItem> items3 = new ArrayList<CaseItem>();
        ArrayList<CaseItem> items4 = new ArrayList<CaseItem>();
        ArrayList<CaseItem> items5 = new ArrayList<CaseItem>();
        block7 : for (decode = 0; decode < PacketsDecoder.getCaseItemsList().size(); ++decode) {
            switch (PacketsDecoder.getCaseItemsList().get(decode).getRarity()) {
                case 1: {
                    items1.add(PacketsDecoder.getCaseItemsList().get(decode));
                    continue block7;
                }
                case 2: {
                    items2.add(PacketsDecoder.getCaseItemsList().get(decode));
                    continue block7;
                }
                case 3: {
                    items3.add(PacketsDecoder.getCaseItemsList().get(decode));
                    continue block7;
                }
                case 4: {
                    items4.add(PacketsDecoder.getCaseItemsList().get(decode));
                    continue block7;
                }
                case 5: {
                    items5.add(PacketsDecoder.getCaseItemsList().get(decode));
                }
            }
        }
        for (decode = 0; decode < 47; ++decode) {
            itemsFin.add((CaseItem)items1.get(PacketsDecoder.randInt(0, items1.size() - 1)));
        }
        for (decode = 0; decode < 7; ++decode) {
            itemsFin.add((CaseItem)items2.get(PacketsDecoder.randInt(0, items2.size() - 1)));
        }
        for (decode = 0; decode < 3; ++decode) {
            itemsFin.add((CaseItem)items3.get(PacketsDecoder.randInt(0, items3.size() - 1)));
        }
        for (decode = 0; decode < 1; ++decode) {
            itemsFin.add((CaseItem)items4.get(PacketsDecoder.randInt(0, items4.size() - 1)));
        }
        for (decode = 0; decode < 1; ++decode) {
            itemsFin.add((CaseItem)items5.get(PacketsDecoder.randInt(0, items5.size() - 1)));
        }
        Collections.shuffle(itemsFin);
        String[] var8 = Recieve.WON_ITEM.split(",");
        ItemStack is = new ItemStack(Item.getItemById((int)Integer.parseInt(var8[0])), Integer.parseInt(var8[2]));
        is.setItemDamage(Integer.parseInt(var8[1]));
        itemsFin.add(57, new CaseItem(is, Integer.parseInt(var8[3])));
        return itemsFin;
    }

    public static int randInt(int min, int max) {
        int randomNum = new Random().nextInt(max - min + 1) + min;
        return randomNum;
    }

    public static float randFloat(float min, float max) {
        return min + new Random().nextFloat() * (max - min);
    }
}

