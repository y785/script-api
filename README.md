# ❤ Moe Script API
This is a java-based  script api for writing performant, low overhead, MapleStory scripts.
We do this by constructing proxy objects that communicate between the api and the script files.
The API itself is heavily modelled after nexon's own api, with some minor additions here and there, like text command scripts.
[Credits to bboki/feras for the below image](https://github.com/Fukerfu)
<p align="center">
    <img src="https://raw.githubusercontent.com/y785/script-api/master/example.png" tag="v83 example provided by bboki" width="200" height="200">
</p>

### Features
- Non-blocking, Fast
- 1 dependency (Logging)

### Examples
All scripts extend their base script type, so a script for an Npc would extend the NpcScript, etc.
Additionally, all logic is conducted in the ``Script::work()`` method. You shouldn't have to extend any underlying logic methods to have functioning scripts, for a more detailed explanation or outlook on scripts, see [my main script repo](https://github.com/y785/moe-scripts).
For a more in depth explanation, see [**the wiki**](https://github.com/y785/script-api/wiki/Basic-Script-Examples)
