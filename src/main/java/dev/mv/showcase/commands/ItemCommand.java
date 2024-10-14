package dev.mv.showcase.commands;

import dev.mv.showcase.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ItemCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                switch (args[0]) {
                    case "VoidAspect":
                        giveVoidAspect(player, 8, 48);
                        return false;
                    case "VoidAspectUpgrade":
                        giveVoidAspect(player, 12, 64);
                        return false;
                    case "Terminator":
                        giveTerminator(player, "0.5s");
                        return false;
                    case "TerminatorUpgrade":
                        giveTerminator(player, "0.15s");
                        return false;
                    case "TU1":
                        giveTerminator(player, "0.05s");
                        return false;
                    case "TU2":
                        giveTerminator(player, "0s");
                        return false;
                    case "Shortbow":
                        giveShortbow(player, "0.5s");
                        return false;
                    case "ShortbowUpgrade":
                        giveShortbow(player, "0.15s");
                        return false;
                    case "ShieldRipper":
                        giveShieldRipper(player, "1s", 1);
                        return false;
                    case "ShieldRipperUpgrade":
                        giveShieldRipper(player, "0.5s", 2);
                        return false;
                    case "ShieldShredder":
                        giveShieldShredder(player, "0.2s", 4);
                        return false;
                    default:
                        break;
                }
                String name = "give" + args[0];
                try {
                    this.getClass().getMethod(name, Player.class).invoke(this, player);
                } catch (Exception e) {
                    player.sendMessage(Utils.chat("&cAn error occurred!"));
                }
            }
        }
        return false;
    }

    public void giveBoomBoots(Player player) {
        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setDisplayName(Utils.chat("&6Boom boots"));
        String lore = "&7Health: &a+10\n" +
            "\n" +
            "&6Ability: Exploding Boots &e&lSNEAK\n" +
            "&7Create an explosion dealing\n" +
            "&c10 &7damage to mobs within\n" +
            "&b3 &7blocks of you.\n" +
            "&8Cooldown: &a0.5s\n" +
            "\n" +
            "&6&lLEGENDARY";
        bootsMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        bootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        bootsMeta.spigot().setUnbreakable(true);
        boots.setItemMeta(bootsMeta);
        player.getInventory().addItem(boots);
    }

    public void giveStarSword(Player player) {
        ItemStack sword = new ItemStack(Material.WOOD_SWORD, 1);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(Utils.chat("&dSword of the stars"));
        String lore = "&7Damage: &c+1000\n" +
            "\n" +
            "&d&lMYTHIC";
        swordMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        swordMeta.spigot().setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
    }

    public void giveIdiotSword(Player player) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(Utils.chat("&fSword of the idiots"));
        String lore = "&7Damage: &c+0\n" +
            "\n" +
            "&f&lCOMMON";
        swordMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        swordMeta.spigot().setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
    }

    public void giveSharpCarrot(Player player) {
        ItemStack sword = new ItemStack(Material.CARROT_ITEM, 1);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(Utils.chat("&9Sharp carrot"));
        String lore = "&7Damage: &c+15\n" +
            "\n" +
            "&9&lRARE";
        swordMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        swordMeta.spigot().setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
    }

    public void giveVoidAspect(Player player, int range, int ether) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SPADE, 1);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.setDisplayName(Utils.chat("&6Aspect of the Void"));
        String lore = "&7Damage: &c+5\n" +
            "&7Teleport Range: &a+" + range + "\n" +
            "&7Ether Range: &a+" + ether + "\n" +
            "\n" +
            "&6Ability: Instant Transmission &e&lRIGHT CLICK\n" +
            "&7Teleport the &ateleport range &7of blocks\n" +
            "&7ahead of you, and get &fspeed &7level &a3\n" +
            "&7for &a5s&7.\n" +
            "\n" +
            "&6Ability: Ether Transmission &e&lSNEAK RIGHT CLICK\n" +
            "&7Teleport to your targeted block\n" +
            "&7up to the &aether range &7ahead of\n" +
            "&7you.\n" +
            "&8Cooldown: &a0.25s\n" +
            "\n" +
            "&6&lLEGENDARY";
        swordMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        swordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        swordMeta.spigot().setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        player.getInventory().addItem(sword);
    }

    public void giveTerminator(Player player, String cooldown) {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Terminator"));
        String lore = "&7Damage: &c+50\n" +
            "&7Shot Cooldown: &a" + cooldown + "\n" +
            "\n" +
            "&dItem Quality: Shortbow\n" +
            "&7Instantly shoots.\n" +
            "\n" +
            "&6Ability: Triple Shot\n" +
            "&7Shoots 3 arrows instead of 1.\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveDTerminator(Player player) {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dTerminator &6✪✪✪✪✪&c➎"));
        String lore = "&7Damage: &c+13682\n" +
            "&7Shot Cooldown: &a0.15s\n" +
            "\n" +
            "&dItem Quality: Shortbow\n" +
            "&7Instantly shoots.\n" +
            "\n" +
            "&6Ability: Triple Shot\n" +
            "&7Shoots 3 arrows instead of 1.\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveShortbow(Player player, String cooldown) {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&9Shortbow"));
        String lore = "&7Damage: &c+15\n" +
            "&7Shot Cooldown: &a" + cooldown + "\n" +
            "\n" +
            "&dItem Quality: Shortbow\n" +
            "&7Instantly shoots.\n" +
            "\n" +
            "&9&lRARE";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveShieldRipper(Player player, String cooldown, int ticks) {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Shield Ripper"));
        String lore = "&7Damage: &c+0\n" +
            "&7Shot Cooldown: &a" + cooldown + "\n" +
            "&7Ticks Removed: &a" + ticks + "\n" +
            "\n" +
            "&dItem Quality: Shortbow\n" +
            "&7Instantly shoots.\n" +
            "\n" +
            "&6Ability: Shield Ripper\n" +
            "&7Upon landing a hit, reduce the\n" +
            "&7invincibility frames of the target\n" +
            "&7by the &aticks removed&7.\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveShieldShredder(Player player, String cooldown, int ticks) {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dShield Shredder"));
        String lore = "&7Damage: &c+0\n" +
            "&7Shot Cooldown: &a" + cooldown + "\n" +
            "&7Ticks Removed: &a" + ticks + "\n" +
            "\n" +
            "&dItem Quality: Shortbow\n" +
            "&7Instantly shoots.\n" +
            "\n" +
            "&6Ability: Triple Shot\n" +
            "&7Shoots 3 arrows instead of 1.\n" +
            "\n" +
            "&6Ability: Shield Ripper\n" +
            "&7Upon landing a hit, reduce the\n" +
            "&7invincibility frames of the target\n" +
            "&7by the &aticks removed&7.\n" +
            "\n" +
            "&d&lMYTHIC";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHyperion(Player player) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion"));
        String lore = "&7Damage: &c+20\n" +
            "\n" +
            "&eUnrefined: Wither scrolls can be applied.\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHyperionS(Player player) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion"));
        String lore = "&7Damage: &c+20\n" +
            "\n" +
            "&6Ability: Shadow Warp &e&lRIGHT CLICK\n" +
            "&7Creates a spacial distortion &a10 &7blocks\n" +
            "&7ahead of you, teleporting you forward into it.\n" +
            "&8Cooldown: &a10s\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHyperionI(Player player) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion"));
        String lore = "&7Damage: &c+20\n" +
            "\n" +
            "&6Ability: Implosion &e&lRIGHT CLICK\n" +
            "&7Deals &c20 &7damage to enemies\n" +
            "&7within 5 blocks\n" +
            "&8Cooldown: &a10s\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHyperionW(Player player) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion"));
        String lore = "&7Damage: &c+20\n" +
            "\n" +
            "&6Ability: Wither Shield &e&lRIGHT CLICK\n" +
            "&7Reduces damage taken by &c10% &7 for &a5s\n" +
            "&7Also grants a shield that gives &a50% &7of\n" +
            "&7your max health as &eabsorption &7, after &a5s&7,\n" +
            "&a50% &7of the remaining absorption is converted\n" +
            "&7into healing.\n" +
            "&8Cooldown: &a10s\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHyperionWI(Player player) {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion"));
        String lore = "&7Damage: &c+20\n" +
            "\n" +
            "&6Ability: Wither Impact &e&lRIGHT CLICK\n" +
            "&7Teleports &a10 &7blocks ahead of you. Then\n" +
            "&7implode dealing &c40 &7damage to enemies within\n" +
            "&a7 &7blocks. Also applies the &bwither shield &7ability\n" +
            "&7giving an absorption shield and reducing damage\n" +
            "&7by &a20%\n &7for &a5s&7, which is then converted\n" +
            "&7into healing.\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveDHyperionWI(Player player) {
        //✪
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dHyperion &6✪✪✪✪✪&c➎"));
        String lore = "&7Damage: &c+2037\n" +
            "\n" +
            "&6Ability: Wither Impact &e&lRIGHT CLICK\n" +
            "&7Teleports &a10 &7blocks ahead of you. Then\n" +
            "&7implode dealing &c10000 &7damage to enemies within\n" +
            "&a7 &7blocks. Also applies the &bwither shield &7ability\n" +
            "&7giving an absorption shield and reducing damage\n" +
            "&7by &a20%\n &7for &a5s&7, which is then converted\n" +
            "&7into healing.\n" +
            "\n" +
            "&d&l&kI&r&d&l MYTHIC &kI";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void givePerfectBoots(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Perfect boots"));
        String lore = "&7Health: &a+10\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void givePerfectLeggings(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Perfect leggings"));
        String lore = "&7Health: &a+10\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void givePerfectChestplate(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Perfect chestplate"));
        String lore = "&7Health: &a+10\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void givePerfectHelmet(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&6Perfect helmet"));
        String lore = "&7Health: &a+10\n" +
            "\n" +
            "&6&lLEGENDARY";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveHealingSword(Player player) {
        ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&9Healing sword"));
        String lore = "&7Damage: &c+5\n" +
            "\n" +
            "&6Ability: Instant Heal &e&lRIGHT CLICK\n" +
            "&7Instantly heal yourself for &a25% &7of\n" +
            "&7your max health, and heal players within\n" +
            "&a5 &7blocks of you for &a10% &7of their\n" +
            "&7max health.\n" +
            "&8Cooldown: &a2s\n" +
            "\n" +
            "&9&lRARE";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveTankOrb(Player player) {
        ItemStack item = new ItemStack(Material.SEA_LANTERN, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dTank Orb"));
        String lore = "&6Ability: Absorb\n" +
            "&7When players take damage within &a5 &7blocks\n" +
            "&7of you, you absorb &a80% &7of the damage\n" +
            "&7they take.\n" +
            "\n" +
            "&6Passive: Tanky\n" +
            "&7Reduces the damage you take by &a75%\n" +
            "&7while this item is in your inventory,\n" +
            "&7however, other players with this item\n" +
            "&7cannot absorb damage you take.\n" +
            "\n" +
            "&d&lMYTHIC";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

    public void giveDungeonPickaxe(Player player) {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&dDungeon Pickaxe"));
        String lore = "&7The best pickaxe for dungeons.\n" +
            "\n" +
            "&9Efficiency X\n" +
            "\n" +
            "&d&lMYTHIC";
        itemMeta.setLore(Arrays.stream(lore.split("\\n")).map(Utils::chat).collect(Collectors.toList()));
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 10, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        itemMeta.spigot().setUnbreakable(true);
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);
    }

}
