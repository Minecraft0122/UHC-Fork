# Changelog

## [Unreleased]

### Bug fixes

- [#66][GL-66] - Teleport spots for the deathmatch arena are not scanned correctly
- [#67][GL-67] - Players are set to survival mode and are able to break out of the lobby before being teleported
- [#68][GL-68] - Spectators will still join the game if the Double Dates scenario is enabled
- [#69][GL-69] - Offline zombies don't drop their armor on Minecraft < 1.9
- [#70][GL-70] - Some lobby schematics are not completely destroyed when game starts
- [#71][GL-71] - Offline zombies sometimes don't drop all player items
- [#72][GL-72] - Teleport spots for the deathmatch arena are not found when using FastAsyncWorldEdit on Minecraft < 1.13

[GL-66]: https://gitlab.com/uhccore/uhccore/-/issues/66
[GL-67]: https://gitlab.com/uhccore/uhccore/-/issues/67
[GL-68]: https://gitlab.com/uhccore/uhccore/-/issues/68
[GL-69]: https://gitlab.com/uhccore/uhccore/-/issues/69
[GL-70]: https://gitlab.com/uhccore/uhccore/-/issues/70
[GL-71]: https://gitlab.com/uhccore/uhccore/-/issues/71
[GL-72]: https://gitlab.com/uhccore/uhccore/-/issues/72

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
