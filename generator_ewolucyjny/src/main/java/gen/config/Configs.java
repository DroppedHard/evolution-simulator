package gen.config;

public class Configs {
    public static Config[] configs = {
            new Config("Basic", 10,10,false,15,
                    10,5,false,10,20,
                    15,10,0,10,
                    false,40,false),
            new Config("Hell Portal",25,25,true,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    false,40,false),
            new Config("Acid Corpses", 25,25,false,30,
                    10,20,true,30,50,
                    30,20,0,10,
                    false,40,false),
            new Config("Small Mutation Variant", 25,25,false,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    true,40,false),
            new Config("Chaos animal Behaviour", 25,25,false,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    false,40,true)
    };
    public static Config[] getConfigs(){
        return configs;
    }
}
