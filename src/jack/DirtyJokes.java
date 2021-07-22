package jack;


import io.github.bfox1.IMedusaAPI;
import io.github.bfox1.command.Command;
import io.github.bfox1.event.IEvent;
import io.github.bfox1.utils.ActionResult;
import io.github.bfox1.utils.IActionResult;
import io.github.bfox1.module.Module;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DirtyJokes extends Command
{

    private static List<String> jokeList;

    private int location;
    public DirtyJokes(String name, String permNode)
    {
        super(name, permNode);

        if(jokeList == null)
        {
            try {
                jokeList = loadJokeList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(jokeList.size() >0)
        {
            int random = ThreadLocalRandom.current().nextInt(0, jokeList.size());

            location = random;
        }
        else {
            location = 0;
        }
    }

    @Override
    public IActionResult<String> executeCommand(IEvent iEvent, LinkedList<String> linkedList)
    {

        IMedusaAPI api = Module.getApiInstance();



        if(jokeList != null)
        {
            if(jokeList.size() != 0)
            {
                //int random = ThreadLocalRandom.current().nextInt(0, jokeList.size());

                String joke = jokeList.get(location);
                location++;
                if(location == jokeList.size())
                {
                    location = 0;
                }
                return new ActionResult<String>(true, joke);
            }
            else
            {
                return new ActionResult<String>(false ,"Joke list is not populated.");
            }
        }
        else
        {
            return new ActionResult<String>(false, "Jok List is Null. ");
        }

    }

    @Override
    public boolean isTwitchChatEnabled() {
        return true;
    }

    @Override
    public boolean isTwitchDMEnabled() {
        return false;
    }

    @Override
    public boolean isDiscordChatEnabled() {
        return true;
    }

    @Override
    public boolean isDiscordPMEnabled() {
        return false;
    }

    @Override
    public boolean isOtherEnabled() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public String getDescription() {
        return "Tells Dirty jokes (NSFW)";
    }

    @Override
    public String helpSyntax() {
        return "m/dirtyjoke";
    }

    private final List<String> loadJokeList() throws IOException {
        InputStream in = getClass().getResourceAsStream("resources/DirtyJokes.yaml");

        byte[] buffer = new byte[in.available()];

        in.read(buffer);

        IMedusaAPI api = Module.getApiInstance();

        File path = new File("data" + File.separator + "jester" + File.separator);
        File f = new File("data" + File.separator + "jester" + File.separator + "DirtyJokes.yaml");

        if(f.exists())
        {
            f.delete();
        }
        path.mkdirs();
        OutputStream os = new FileOutputStream(f);
        os.write(buffer);
        in.close();
        os.close();
        //BufferedReader reader = new BufferedReader((new InputStreamReader(in)));

        return Module.getApiInstance().createYamlLoader("tes").loadYamlFile("jester", "DirtyJokes.yaml", List.class);
    }
}
