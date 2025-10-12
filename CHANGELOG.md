# Changelog

## [1.20.15] - 2025-10-12

UhcCore version `1.20.15` is now here! The update brings a few small fixes,
but most importantly, adds support for Minecraft 1.21.10.

### New features

- [#209][GL-209] - Support Minecraft 1.21.10
- [#205][GL-205] - Option to control the locator bar on Minecraft 1.21.6+

### Bug fixes

- [#206][GL-206] - Crash on world pregen for veins

[GL-209]: https://gitlab.com/uhccore/uhccore/-/issues/209
[GL-206]: https://gitlab.com/uhccore/uhccore/-/issues/206
[GL-205]: https://gitlab.com/uhccore/uhccore/-/issues/205

## [1.20.14] - 2025-06-22

UhcCore version `1.20.14` is now released! This update brings support for Minecraft 1.21.6,
but also adds integrations with the PlaceholderAPI and Chunky plugins, as well as various other features and bug fixes.

### New features

- [#204][GL-204] - Support Minecraft 1.21.6
- [#38][GL-38] - Use Chunky if installed as an alternative for pre-generation on supported Minecraft versions
- [#42][GL-42] - Provide placeholders from UhcCore via a PlaceholderAPI expansion
- [#43][GL-43] - Support using PlaceholderAPI placeholders in UhcCore
- [#60][GL-60] - Option to enable or disable specific kits and crafts
- [#203][GL-203] - Option to hide specific crafts from the crafts menu
- [#74][GL-74] - Always choose the smallest available number when allocating team numbers
- [#103][GL-103] - Player glow color
- [#130][GL-130] - Option to change chunk generator and biome provider for the UHC worlds
- [#202][GL-202] - Security system when loading the config for the first time
- [#178][GL-178] - The game should end immediately if only one living team remains online, even if there are living teams offline

### Bug fixes

- [#4][GL-4] - The generate-vein config option doesn't generate veins below y=0 on Minecraft 1.18+
- [#37][GL-37] - The generate-vein config option doesn't work in debug mode or with pre-generation disabled

[GL-4]: https://gitlab.com/uhccore/uhccore/-/issues/4
[GL-37]: https://gitlab.com/uhccore/uhccore/-/issues/37
[GL-38]: https://gitlab.com/uhccore/uhccore/-/issues/38
[GL-42]: https://gitlab.com/uhccore/uhccore/-/issues/42
[GL-43]: https://gitlab.com/uhccore/uhccore/-/issues/43
[GL-60]: https://gitlab.com/uhccore/uhccore/-/issues/60
[GL-74]: https://gitlab.com/uhccore/uhccore/-/issues/74
[GL-103]: https://gitlab.com/uhccore/uhccore/-/issues/103
[GL-130]: https://gitlab.com/uhccore/uhccore/-/issues/130
[GL-178]: https://gitlab.com/uhccore/uhccore/-/issues/178
[GL-202]: https://gitlab.com/uhccore/uhccore/-/issues/202
[GL-203]: https://gitlab.com/uhccore/uhccore/-/issues/203
[GL-204]: https://gitlab.com/uhccore/uhccore/-/issues/204

## [1.20.13] - 2025-04-13

UhcCore version `1.20.13` is now available! This update fixes some bugs, and adds support for Minecraft 1.21.5.

### New features

- [#201][GL-201] - Support Minecraft 1.21.5

### Bug fixes

- [#200][GL-200] - Exception when loading JSON item stack attribute modifiers without UUID
- [#199][GL-199] - NPE when breaking leaves without any item in hand
- [#198][GL-198] - Minimum and maximum options for JSON item stacks don't work on Paper servers for Minecraft 1.21+
- [#171][GL-171] - Some selected scenarios in the scenario menu are no longer displayed with a stack size of 2

[GL-201]: https://gitlab.com/uhccore/uhccore/-/issues/201
[GL-200]: https://gitlab.com/uhccore/uhccore/-/issues/200
[GL-199]: https://gitlab.com/uhccore/uhccore/-/issues/199
[GL-198]: https://gitlab.com/uhccore/uhccore/-/issues/198
[GL-171]: https://gitlab.com/uhccore/uhccore/-/issues/171

## [1.20.12] - 2025-02-05

UhcCore version `1.20.12` is now finally here! This update fixes lots of bugs,
and adds some nice features, most notably support for Minecraft 1.21.4.

### New features

- [#181][GL-181] - Support Minecraft 1.21.4
- [#190][GL-190] - Clarify apple-drops option behavior
- [#182][GL-182] - Option to change scoreboard render type for the hearts-on-tab option

### Bug fixes

- [#197][GL-197] - Not all flowers drop items in the Flower Power scenario
- [#196][GL-196] - Some items cannot be moved in the enchanting inventory in the CutClean scenario
- [#195][GL-195] - Not all entity drops are smelted in the CutClean scenario
- [#194][GL-194] - Revived players do not receive their items on Paper servers for Minecraft 1.21.1+
- [#191][GL-191] - Extra apples from the apple-drops option are only dropped when leaves decay and not when a player breaks leaves
- [#192][GL-192] - All types of decaying leaves drop oak saplings when the apple-drops.all-trees option is enabled
- [#193][GL-193] - The apple-drops.all-trees option can only be disabled on Minecraft 1.13+
- [#187][GL-187] - Only the first tool is enchanted when shift-clicking recipe result in the Hastey Boys scenario
- [#184][GL-184] - Various bugs and compatibility issues between scenarios with custom block drops
- [#183][GL-183] - Flowers break when you mine any block above them in the Flower Power scenario
- [#180][GL-180] - Player death message is not client-translatable on Paper servers for Minecraft 1.16.5+
- [#177][GL-177] - Iteminfo command fails when using it on a potion that has no base effect on Minecraft 1.20.5+

[GL-197]: https://gitlab.com/uhccore/uhccore/-/issues/197
[GL-196]: https://gitlab.com/uhccore/uhccore/-/issues/196
[GL-195]: https://gitlab.com/uhccore/uhccore/-/issues/195
[GL-194]: https://gitlab.com/uhccore/uhccore/-/issues/194
[GL-193]: https://gitlab.com/uhccore/uhccore/-/issues/193
[GL-192]: https://gitlab.com/uhccore/uhccore/-/issues/192
[GL-191]: https://gitlab.com/uhccore/uhccore/-/issues/191
[GL-190]: https://gitlab.com/uhccore/uhccore/-/issues/190
[GL-187]: https://gitlab.com/uhccore/uhccore/-/issues/187
[GL-184]: https://gitlab.com/uhccore/uhccore/-/issues/184
[GL-183]: https://gitlab.com/uhccore/uhccore/-/issues/183
[GL-182]: https://gitlab.com/uhccore/uhccore/-/issues/182
[GL-181]: https://gitlab.com/uhccore/uhccore/-/issues/181
[GL-180]: https://gitlab.com/uhccore/uhccore/-/issues/180
[GL-177]: https://gitlab.com/uhccore/uhccore/-/issues/177

## [1.20.11] - 2024-08-19

UhcCore version `1.20.11` is now here to fix a few bugs that affect the latest versions
of Minecraft, which also means that UhcCore is now fully compatible with Minecraft 1.21.1!

### Bug fixes

- [#174][GL-174] - Team rename and invite menu doesn't work on the latest server builds for Minecraft 1.21 and 1.21.1
- [#173][GL-173] - The pre-generate-world config option doesn't take the nether-scale option into account
- [#170][GL-170] - Enchantment and attribute modifier text no longer hidden in scenario view, edit and vote menus on Paper servers for Minecraft 1.20.5+
- [#169][GL-169] - NPE when activating the Randomized Crafts or Upside Down Crafting scenario on Paper servers for Minecraft 1.20.5+

[GL-174]: https://gitlab.com/uhccore/uhccore/-/issues/174
[GL-173]: https://gitlab.com/uhccore/uhccore/-/issues/173
[GL-170]: https://gitlab.com/uhccore/uhccore/-/issues/170
[GL-169]: https://gitlab.com/uhccore/uhccore/-/issues/169

## [1.20.10] - 2024-06-26

UhcCore version `1.20.10` is now here to add support for Minecraft 1.21,
and also includes an option to change the scale between the overworld and nether dimensions!

### New features

- [#164][GL-164] - Support Minecraft 1.21
- [#131][GL-131] - Option to change the overworld to nether scale factor

[GL-164]: https://gitlab.com/uhccore/uhccore/-/issues/164
[GL-131]: https://gitlab.com/uhccore/uhccore/-/issues/131

## [1.20.9] - 2024-06-03

UhcCore version `1.20.9` is now here to fix a few bugs,
add some more options, and most importantly, add support for Minecraft 1.20.6!

### New features

- [#157][GL-157] - Support Minecraft 1.20.6
- [#154][GL-154] - Option to change the time between teleporting each team
- [#135][GL-135] - Option to change the drop chance of golden apples in the Lucky Leaves scenario
- [#114][GL-114] - Add config option to decrease delay between teleport and deathmatch start

### Bug fixes

- [#153][GL-153] - ban-level-2-potions option can be bypassed
- [#152][GL-152] - Persistent leaves are decayed by the Fast Leaves Decay scenario
- [#150][GL-150] - Fast Leaves Decay scenario causes lag
- [#136][GL-136] - Player name is not revealed in elimination message in the Anonymous scenario

[GL-157]: https://gitlab.com/uhccore/uhccore/-/issues/157
[GL-154]: https://gitlab.com/uhccore/uhccore/-/issues/154
[GL-153]: https://gitlab.com/uhccore/uhccore/-/issues/153
[GL-152]: https://gitlab.com/uhccore/uhccore/-/issues/152
[GL-150]: https://gitlab.com/uhccore/uhccore/-/issues/150
[GL-136]: https://gitlab.com/uhccore/uhccore/-/issues/136
[GL-135]: https://gitlab.com/uhccore/uhccore/-/issues/135
[GL-114]: https://gitlab.com/uhccore/uhccore/-/issues/114

## [1.20.8] - 2023-12-18

UhcCore version `1.20.8` is now here to fix more bugs,
and brings support for Minecraft 1.20.4!

Note that the interpretation of the `deathmatch.center-deathmatch.end-size` setting in `config.yml`
has been changed to fix [#122][GL-122], so you may want to adjust the setting accordingly.
Previously, the setting was interpreted as the side length, but now it's correctly
interpreted as the apothem of the border (which is half of the side length).

### New features

- [#134][GL-134] - Support Minecraft 1.20.4
- [#133][GL-133] - Option to use the team name in the team prefix
    - Added the `%teamName%` placeholder to `display.team-prefix` in `lang.yml`
- [#121][GL-121] - Apply Health Boost status effect instead of changing max health base in the Superheroes scenario

### Bug fixes

- [#125][GL-125] - Custom craft is sometimes counted toward the limit even though the craft wasn't performed
- [#122][GL-122] - The deathmatch.center-deathmatch.end-size setting is not interpreted correctly
- [#120][GL-120] - Players sometimes lose their superpower in the Superheroes scenario when using the potion-effect-on-start setting
- [#119][GL-119] - Player max health is not reset to default when a player is spawned

[GL-134]: https://gitlab.com/uhccore/uhccore/-/issues/134
[GL-133]: https://gitlab.com/uhccore/uhccore/-/issues/133
[GL-125]: https://gitlab.com/uhccore/uhccore/-/issues/125
[GL-122]: https://gitlab.com/uhccore/uhccore/-/issues/122
[GL-121]: https://gitlab.com/uhccore/uhccore/-/issues/121
[GL-120]: https://gitlab.com/uhccore/uhccore/-/issues/120
[GL-119]: https://gitlab.com/uhccore/uhccore/-/issues/119

## [1.20.7] - 2023-09-25

UhcCore version `1.20.7` is now here to squash a few bugs,
and also brings support for Minecraft 1.20.2!

### New features

- [#111][GL-111] - Support Minecraft 1.20.2

### Bug fixes

- [#117][GL-117] - Players in spectator mode are unable to use inventory menus
- [#116][GL-116] - Spectator admins are able to join a team in the Love at First Sight scenario
- [#115][GL-115] - Spectators can still be invited to and join a team
    - Added `team.message.spectators-cannot-join` to `lang.yml`
- [#105][GL-105] - The prevent-player-from-leaving-team config option has no effect
- [#104][GL-104] - NullPointerException when using the pick-random-world-from-list setting
- [#102][GL-102] - Players are teleported to the wrong location when respawning as spectators after dying during the deathmatch
- [#101][GL-101] - No feedback from vanilla commands when executed in UHC worlds
- [#100][GL-100] - ArrayIndexOutOfBoundsException when using the cave-ores-only setting
- [#99][GL-99] - Items are sometimes missing from the chest in the Timebomb scenario

[GL-111]: https://gitlab.com/uhccore/uhccore/-/issues/111
[GL-117]: https://gitlab.com/uhccore/uhccore/-/issues/117
[GL-116]: https://gitlab.com/uhccore/uhccore/-/issues/116
[GL-115]: https://gitlab.com/uhccore/uhccore/-/issues/115
[GL-105]: https://gitlab.com/uhccore/uhccore/-/issues/105
[GL-104]: https://gitlab.com/uhccore/uhccore/-/issues/104
[GL-102]: https://gitlab.com/uhccore/uhccore/-/issues/102
[GL-101]: https://gitlab.com/uhccore/uhccore/-/issues/101
[GL-100]: https://gitlab.com/uhccore/uhccore/-/issues/100
[GL-99]: https://gitlab.com/uhccore/uhccore/-/issues/99

## [1.20.6] - 2023-08-05

UhcCore version `1.20.6` is now here and brings a whole bunch of bug fixes and new features!

### New features

- [#52][GL-52] - Option to prevent players from disenchanting tools in the Hastey Boys scenario
    - Added `scenarios.hastey_boys.grindstone-error` to `lang.yml`
    - Added the `allow-disenchanting` setting to `hastey_boys` in `scenarios.yml`
- [#97][GL-97] - Option to change generate-structures for UHC worlds
    - Added the `generate-structures` setting to `config.yml`
- [#96][GL-96] - Allow empty list in loot configurations
- [#94][GL-94] - Support loading/saving item attribute modifiers on Minecraft 1.8.8
- [#85][GL-85] - Inform players that PvP is not enabled yet if they try to damage a player before PvP has started
    - Added `players.pvp-off` to `lang.yml`
- [#82][GL-82] - Support loading/saving registry namespace for enchantments on items
- [#63][GL-63] - Support loading/saving custom model data on items

### Bug fixes

- [#98][GL-98] - Unable to find a safe starting location in the overworld on Minecraft 1.14 to 1.15.1
- [#95][GL-95] - UUID field is not saved/loaded for attribute modifiers on Minecraft 1.13+
- [#90][GL-90] - UhcCore often does not ignore canceled events
- [#89][GL-89] - Revive command does not return an error when trying to revive a living player
- [#88][GL-88] - Cannot revive offline players when server is in offline mode
- [#87][GL-87] - Attacks on the offline zombie of a player are never prevented for ranged attacks
- [#86][GL-86] - Players may respawn at the wrong location after being revived
- [#84][GL-84] - Cherry trees are not supported in the Timber and Fast Leaves Decay scenarios
- [#83][GL-83] - The explosion-power option in the Double Dates scenario does not support floating-point values
- [#79][GL-79] - Offline zombie is not always removed
- [#78][GL-78] - Player inventory items are spawned at the wrong location when using the revive command

[GL-52]: https://gitlab.com/uhccore/uhccore/-/issues/52
[GL-98]: https://gitlab.com/uhccore/uhccore/-/issues/98
[GL-97]: https://gitlab.com/uhccore/uhccore/-/issues/97
[GL-96]: https://gitlab.com/uhccore/uhccore/-/issues/96
[GL-95]: https://gitlab.com/uhccore/uhccore/-/issues/95
[GL-94]: https://gitlab.com/uhccore/uhccore/-/issues/94
[GL-90]: https://gitlab.com/uhccore/uhccore/-/issues/90
[GL-89]: https://gitlab.com/uhccore/uhccore/-/issues/89
[GL-88]: https://gitlab.com/uhccore/uhccore/-/issues/88
[GL-87]: https://gitlab.com/uhccore/uhccore/-/issues/87
[GL-86]: https://gitlab.com/uhccore/uhccore/-/issues/86
[GL-85]: https://gitlab.com/uhccore/uhccore/-/issues/85
[GL-84]: https://gitlab.com/uhccore/uhccore/-/issues/84
[GL-83]: https://gitlab.com/uhccore/uhccore/-/issues/83
[GL-82]: https://gitlab.com/uhccore/uhccore/-/issues/82
[GL-79]: https://gitlab.com/uhccore/uhccore/-/issues/79
[GL-78]: https://gitlab.com/uhccore/uhccore/-/issues/78
[GL-63]: https://gitlab.com/uhccore/uhccore/-/issues/63

## [1.20.5] - 2023-06-16

UhcCore version `1.20.5` is now here and brings some important bug fixes,
a new scenario option, and support for Minecraft 1.20.1!

### New features

- [#80][GL-80] - Support Minecraft 1.20.1
- [#76][GL-76] - Option to change explosion power in the Timebomb scenario
    - Added the `explosion-power` setting to `timebomb` in `scenarios.yml`

### Bug fixes

- [#66][GL-66] - Teleport spots for the deathmatch arena are not scanned correctly
- [#67][GL-67] - Players are set to survival mode and are able to break out of the lobby before being teleported
- [#68][GL-68] - Spectators will still join the game if the Double Dates scenario is enabled
- [#69][GL-69] - Offline zombies don't drop their armor on Minecraft < 1.9
- [#70][GL-70] - Some lobby schematics are not completely destroyed when game starts
- [#71][GL-71] - Offline zombies sometimes don't drop all player items
- [#72][GL-72] - Teleport spots for the deathmatch arena are not found when using FastAsyncWorldEdit on Minecraft < 1.13
- [#81][GL-81] - Players may sometimes fall through the ground when teleporting on Minecraft 1.8

[GL-66]: https://gitlab.com/uhccore/uhccore/-/issues/66
[GL-67]: https://gitlab.com/uhccore/uhccore/-/issues/67
[GL-68]: https://gitlab.com/uhccore/uhccore/-/issues/68
[GL-69]: https://gitlab.com/uhccore/uhccore/-/issues/69
[GL-70]: https://gitlab.com/uhccore/uhccore/-/issues/70
[GL-71]: https://gitlab.com/uhccore/uhccore/-/issues/71
[GL-72]: https://gitlab.com/uhccore/uhccore/-/issues/72
[GL-76]: https://gitlab.com/uhccore/uhccore/-/issues/76
[GL-80]: https://gitlab.com/uhccore/uhccore/-/issues/80
[GL-81]: https://gitlab.com/uhccore/uhccore/-/issues/81

## [1.20.4] - 2023-03-24

UhcCore version `1.20.4` is now here and brings some important bug fixes, lots
of little improvements, and support for Minecraft 1.19.4!

A notable improvement is that UhcCore should now be a lot better at spawning
players at safe locations. Previously, the plugin would sometimes spawn players
partially (or in some cases, completely) inside the ground, which should no
longer be the case. Please create an issue at the GitLab repository or reach
out on the Discord server if you experience any future issues with this.

### New features

- [#10][GL-10] - Option to disable axe requirement in the Timber scenario
    - Added the `require-axe` setting to `timber` in `scenarios.yml`
- [#14][GL-14] - Support more than 80 unique teams in the tab list
    - Removed `team.message.color-unavailable` from `lang.yml`
    - Added `display.team-prefix` to `lang.yml`
- [#18][GL-18] - Support loading/saving the Unbreakable NBT tag on items
- [#20][GL-20] - Support loading/saving banner patterns on items
- [#26][GL-26] - Log more information when a safe starting location can't be found
- [#30][GL-30] - Option to override max-players-per-team in the Love at First Sight scenario
    - Added the `max-players-per-team` setting to `love_at_first_sight` in `scenarios.yml`
- [#31][GL-31] - Support Minecraft 1.19.4
- [#36][GL-36] - Option to disable sneaking requirement in the Vein Miner scenario
    - Added the `require-sneaking` setting to `vein_miner` in `scenarios.yml`
- [#39][GL-39] - Clarify and remove some team name restrictions
    - Removed `team.message.name-change-error` from `lang.yml`
    - Added `team.message.name-empty` to `lang.yml`
    - Added `team.message.name-too-long` to `lang.yml`
    - Added `team.message.name-illegal-characters` to `lang.yml`
- [#47][GL-47] - Option to control the election threshold for scenario voting
    - Added the `customize-game-behavior.scenarios.voting.election-threshold` setting to `config.yml`
- [#58][GL-58] - Avoid resetting the player's aim when teleporting
- [#59][GL-59] - Option to disable team color menu
    - Added the `enable-team-color-menu` setting to `config.yml`
- Added `team.message.player-name-empty` to `lang.yml`
- Added `team.message.invite-success` to `lang.yml`

### Bug fixes

- [#21][GL-21] - Team invite menu does not validate user input properly
- [#23][GL-23] - Non-tool items break when mining in the Randomized Drops and Vein Miner scenarios
- [#24][GL-24] - Player starting location is sometimes inside of ground blocks
- [#25][GL-25] - Player starting location is often unsafe in the Nether Start scenario
- [#29][GL-29] - Teams are sometimes not correctly updated on the tab list
- [#45][GL-45] - Errors are spammed to the server console on Minecraft 1.19.3+ servers if ProtocolLib is installed
- [#55][GL-55] - Display names are not obfuscated in the Anonymous scenario if use-team-colors is set to false
- [#56][GL-56] - Players may sometimes fall into the ground (and suffocate) when teleported to their starting location

[GL-10]: https://gitlab.com/uhccore/uhccore/-/issues/10
[GL-14]: https://gitlab.com/uhccore/uhccore/-/issues/14
[GL-18]: https://gitlab.com/uhccore/uhccore/-/issues/18
[GL-20]: https://gitlab.com/uhccore/uhccore/-/issues/20
[GL-21]: https://gitlab.com/uhccore/uhccore/-/issues/21
[GL-23]: https://gitlab.com/uhccore/uhccore/-/issues/23
[GL-24]: https://gitlab.com/uhccore/uhccore/-/issues/24
[GL-25]: https://gitlab.com/uhccore/uhccore/-/issues/25
[GL-26]: https://gitlab.com/uhccore/uhccore/-/issues/26
[GL-29]: https://gitlab.com/uhccore/uhccore/-/issues/29
[GL-30]: https://gitlab.com/uhccore/uhccore/-/issues/30
[GL-31]: https://gitlab.com/uhccore/uhccore/-/issues/31
[GL-36]: https://gitlab.com/uhccore/uhccore/-/issues/36
[GL-39]: https://gitlab.com/uhccore/uhccore/-/issues/39
[GL-45]: https://gitlab.com/uhccore/uhccore/-/issues/45
[GL-47]: https://gitlab.com/uhccore/uhccore/-/issues/47
[GL-55]: https://gitlab.com/uhccore/uhccore/-/issues/55
[GL-56]: https://gitlab.com/uhccore/uhccore/-/issues/56
[GL-58]: https://gitlab.com/uhccore/uhccore/-/issues/58
[GL-59]: https://gitlab.com/uhccore/uhccore/-/issues/59

## [1.20.3] - 2022-12-17

### New features

- Updated the plugin to support Minecraft 1.19.3
- Added the `log-break-limit` option to `timber` in `scenarios.yml`
- An axe is now required in order to fell a tree in the Timber scenario

### Bug fixes

- Fixed a bug in the Timber and Vein Miner scenarios where tools would receive negative durability instead of breaking
- Fixed a bug in the Randomized Drops scenario where tools would be repaired instead of damaged when breaking blocks
- Fixed a bug where the `generate-sugar-cane` option would not work on Minecraft 1.12.2 and below
- Fixed a bug in the Timber scenario where some diagonal branches would not be chopped down
- Fixed a StackOverflowError that could happen when chopping very large trees in the Timber scenario
- Added missing log and leaf types to the Timber and Fast Leaves Decay scenarios
- Fixed a bug where a netherite axe would not work in the Timber scenario
- Fixed a bug in some ore-related scenarios where lapis ore would drop ink sacs on Minecraft 1.12.2 and below
- Fixed an IllegalArgumentException that would sometimes happen in the Hastey Boys scenario

## [1.20.2] - 2022-09-11

### New features

- Updated the plugin to support Minecraft 1.19.2
- Added the `eye-attempts` and `eye-probability` options to `dragon_rush` in `scenarios.yml`
- Added the `enable-victory` option to `config.yml`
- Added the `logging-level` option to `config.yml`
- Added some more translation strings to `lang.yml` under `team.inventory` and `team.colors`
- Added `display.team-chat` to `lang.yml`
- Added `scenarios.team_inventory.title` to `lang.yml`
- Added `players.death-message` to `lang.yml`
- UhcCore announcement messages can now be disabled by setting them to an empty string in `lang.yml`
- Worlds generated by UhcCore are now saved to the same location as other
worlds, if a custom `world-container` is configured in Spigot/Paper
- Added the `use-default-world-spawn-for-lobby` option to `config.yml`
- Added an announcement message for the deathmatch countdown
    - Can be changed using `game.starting-deathmatch-in` in `lang.yml`

### Bug fixes

- Fixed a bug in the Love at First Sight scenario where teams created in the lobby would carry through
- Fixed a bug in the Achievement Hunter scenario where not all advancements would be counted
- Fixed a bug in the Randomized Drops scenario where dropped items would sometimes glitch up through blocks
- Fixed a bug where players would be removed from the Best PvE list even if the damage was blocked
- Fixed a bug in the Horseless scenario where players could ride zombie/skeleton horses, donkeys and mules
- Fixed a bug in the Weakest Link scenario where players with the resistance effect could survive
- Fixed a bug in the Blood Diamonds and Sky High scenarios where health would "vanish" without playing the damage effect
- Fixed a bug where absorption and totems of undying would not protect against Blood Diamonds and Sky High damage
- Fixed a bug in the Randomized Drops scenario where some blocks would not drop anything at all
- Fixed a bug with scenario voting where players could lose a vote by clicking an item in their hotbar
- Fixed a crash that occurred when starting the game if voting was enabled but there were no scenarios to vote for
- Fixed a scenario error message where "Silent Night" was incorrectly named "Anonymous"
- Fixed a `NullPointerException` that would happen when trying to revive a nonexistent player
- Fixed an `IllegalPluginAccessException` which could sometimes happen when shutting down the server
- Fixed a bug with the JSON item deserializer where color codes in display names were not working
- Fixed incorrect scenario IDs in the default `scenarios.yml` template
- Fixed a bug where "Scoreboard line is too long" would be spammed while starting the game
- Removed some more log spam
- Clarified a few confusing log messages

## [1.20.1] - 2022-06-14

### New features

- Updated the plugin to support Minecraft 1.19
- Added the `player-death-sound` option (see `config.yml` for more details)
- Added the `/heal` command (see `/help heal` for more details)
    - Permission name: `uhc-core.commands.heal`

### Bug fixes

- Fixed a missing error message for the `/teaminventory` command

## [1.20.0] - 2022-06-08

For older releases and changelogs, see
<https://github.com/Mezy/UhcCore/releases>.

### New features

- Updated the plugin to support Minecraft 1.18

### Bug fixes

- Fixed a bug with the `spawn-offline-players` setting where killing
the zombie of an offline player would not kill them or drop their loot
- Fixed compatibility with the nether and deepslate ore types

### Other

- Optimized and updated the build system to support Java 17
- Removed the automatic plugin update functionality
- Removed the bStats metrics (for now)
