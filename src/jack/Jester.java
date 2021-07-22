package jack;

import io.github.bfox1.IMedusaAPI;
import io.github.bfox1.manager.IModuleCommandLoader;
import io.github.bfox1.module.IConfig;

import io.github.bfox1.module.Module;

public class Jester extends Module
{

    public Jester(int id, IMedusaAPI api)
    {
        super(id, api);
        setName("Jester");
        System.out.println(api);
    }

    @Override
    public void init()
    {

    }


    @Override
    public void loadCommands(IModuleCommandLoader iModuleCommandLoader)
    {
        iModuleCommandLoader.registerCommand(this, new DirtyJokes("dirtyjoke", "jester.user.dirty"));
    }

    @Override
    public IConfig getConfig()
    {
        return null;
    }
}
