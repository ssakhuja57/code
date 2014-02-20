public enum Fruits {
    apple("red", "tasty", "hard"), blueberry("blue", "okay", "soft"), strawberry("red", "yummy", "soft");

    private final String color;
    private final String tastiness;
    private final String hardness;
    
    Fruits(String color, String tastiness, String hardness){
        this.color = color;
        this.tastiness = tastiness;
        this.hardness = hardness;
    }

    public String getColor(){
        return this.color;
    }

    public String getTastiness(){
        return this.tastiness;
    }

    public String getHardness(){
        return this.hardness;
    }

}