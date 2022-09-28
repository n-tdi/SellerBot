package world.ntdi.sellerbot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import world.ntdi.sellerbot.events.JoinEvent;

import javax.security.auth.login.LoginException;

public class SellerBot {
    public static JDA getJda;
    public static void main(String[] args) throws LoginException, InterruptedException {
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.forceGuildOnly(1024742260232945815L);
//        builder.setServerInvite("https://discord.gg/Ahqeb8ahHn");
        builder.setOwnerId("811580599068262421");
        builder.setCoOwnerIds(348587937144897537L);
        builder.setPrefix("/");
        builder.useHelpBuilder(false);

        builder.setActivity(Activity.watching("Eggs eM eL"));
        CommandClient commandClient = builder.build();

        String token = getToken();

        JDA jda = JDABuilder.createDefault(token)
                .disableCache(CacheFlag.VOICE_STATE)
                .setBulkDeleteSplittingEnabled(false)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(
                        new JoinEvent(),
                        commandClient)
                .build();

        getJda = jda;

        jda.awaitReady();
        CommandListUpdateAction commandListUpdateAction = jda.updateCommands();
        commandListUpdateAction.queue();
    }

    public static String getToken() {
        Dotenv dotenv;

        try {
            dotenv = Dotenv.load();
            return dotenv.get("BOT_TOKEN");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Do you have a .env file with the BOT_TOKEN key?");
            return "";
        }
    }
}
