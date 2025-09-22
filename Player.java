package game.othello;
public class Player {
    private String name;

    Player(String name){
        setName(name);
    }

    private void setName(String name){
        if(!name.isEmpty()){
            this.name=name;
        }
    }

    public String getName(){
        return this.name;
    }
}
