public class Test0Sudoku {
    public static void main(String[] args) {
        String s[] = {
            "800 000 042",
            "007 059 063",
            "000 000 900",

            "000 900 400",
            "650 080 071",
            "004 003 000",

            "002 000 000",
            "910 620 800",
            "780 000 004" };
        Sudoku p;
        System.out.println("Author: " + Sudoku.myName());
        p = new Sudoku(s);
        System.out.println(p);
    }
}