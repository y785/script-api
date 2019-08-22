package moe.maple.api.script.model.object;

/**
 * Created on 8/22/2019.
 */
public interface GuildObject<Guild> extends ScriptObject<Guild> {
    /** BMS Notes:
     * system function(integer)		isGuildMember;
     * 	system function(integer)		isGuildMaster;
     * 	system function(integer)		isGuildSubMaster;
     * 	system function(integer)		removeGuild( integer );
     * 	system function(integer)		getGuildCountMax;
     * 	system function(integer)		incGuildCountMax( integer, integer );
     * 	system function(integer)		isCreateGuildPossible( integer );
     * 	system function(integer)		createNewGuild( integer );
     * 	system function(integer)		setGuildMark( integer );
     * 	system function(integer)		isGuildMarkExist;
     * 	system function(integer)		removeGuildMark( integer );
     * 	system function(integer)		isGuildQuestRegistered;
     * 	system function(integer)		canEnterGuildQuest;
     * 	system function				clearGuildQuest;
     * 	system function				incGuildPoint( integer );
     */
}
