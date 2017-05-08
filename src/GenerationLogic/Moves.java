package GenerationLogic;

public enum Moves {
    L(), R(), U(), D(), LU(), LD(), RU(), RD(), NA();

    public static Moves fromInteger(int x){
        switch (x){
            case 0: return L;
            case 1: return R;
            case 2: return U;
            case 3: return D;
            case 4: return LU;
            case 5: return LD;
            case 6: return RU;
            case 7: return RD;
            case 8: return NA;
            default: throw new IllegalArgumentException();
        }
    }


    public static Moves fromString(String x){
        switch (x){
            case "L": return L;
            case "R": return R;
            case "U": return U;
            case "D": return D;
            case "LU": return LU;
            case "LD": return LD;
            case "RU": return RU;
            case "RD": return RD;
            case "NA": return NA;
            default: throw new IllegalArgumentException();
        }
    }
}
