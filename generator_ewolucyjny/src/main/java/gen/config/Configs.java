package gen.config;

public class Configs {
    public static Config[] configs = {
            new Config("Default", 50,50,false,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    false,40,false),
            new Config("Hell Portal",50,50,true,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    false,40,false),
            new Config("Acid Corpses", 50,50,false,30,
                    10,20,true,30,50,
                    30,20,0,10,
                    false,40,false),
            new Config("Small Mutation Variant", 50,50,false,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    true,40,false),
            new Config("Chaos animal Behaviour", 50,50,false,30,
                    10,20,false,30,50,
                    30,20,0,10,
                    false,40,true)
    };
    public static Config[] getConfigs(){
        return configs;
    }
}
