import java.io.*;
public class Sudoku {
    public static String inputFilename;
    public static String outputFilename;
    public static int m;
    public static int n;
    public static boolean check(int[][] a, int i, int j, int k, int l) {
        int x,y;
        for(int n = 0;n < l;n++) {        //列中有K返false
            if(a[i][n] == k) {
                return false;
            }
        }
        for (int m = 0; m < l; m++) {     //行中有K返false
            if (a[m][j] == k) {
                return false;
            }
        }
        switch (l){                               //4、6、8、9阶判断宫
            case 4:
                x = i / 2 * 2;                //x、y为宫坐标
                y = j / 2 * 2;
                for (int m = x; m < x + 2; m++){
                    for (int n = y; n < y + 2; n++){
                        if (a[m][n] == k) {
                            return false;
                        }
                    }
                }
            case 6:
                x = i / 2 * 2;
                y = j / 3 * 3;
                for (int m = x; m < x + 2; m++){
                    for (int n = y; n < y + 3; n++){
                        if (a[m][n] == k) {
                            return false;
                        }
                    }
                }
            case 8:
                x = i / 4 * 4;
                y = j / 2 * 2;
                for (int m = x; m < x + 4; m++){
                    for (int n = y; n < y + 2; n++){
                        if (a[m][n] == k) {
                            return false;
                        }
                    }
                }
            case 9:
                x = i / 3 * 3;
                y = j / 3 * 3;
                for (int m = x; m < x + 3; m++){
                    for (int n = y; n < y + 3; n++){
                        if (a[m][n] == k) {
                            return false;
                        }
                    }
                }
        }
        return true;
    }
    public static void sudoku(int[][] a, int n, int l) throws IOException {
        int [][] temp =new int[l][l];//l为阶数，几阶就是l*l
        for (int i = 0; i < temp.length; i++) {
            System.arraycopy(a[i], 0, temp[i], 0, temp.length);//复制数组
        }
        int i = n / l;             //行列
        int j = n % l;
        int z = l*l-1; //除去起点剩多少格子
        if (a[i][j] != 0) {
            print(n, l, temp, z);
        }
        else {
            for (int k = 1; k <= l; k++){
                boolean flag = check(temp, i, j, k,l);
                if (flag){
                    temp[i][j] = k;
                    print(n, l, temp, z);
                    temp[i][j] = 0;
                }
            }
        }
    }
    public static void print(int n, int l, int[][] temp, int z) throws IOException {
        if (n == z) {//最后一个行列
            FileWriter out = new FileWriter(outputFilename);
            for (int[] ints : temp) {
                for (int j1 = 0; j1 < temp.length; j1++) {
                    String s = Integer.toString(ints[j1]);
                    out.write(s);
                    out.write(" ");
                }
                out.write("\n");
            }
            out.write("\n");
            out.close();
        }
        else {
            sudoku(temp, n + 1,l);
        }
    }
    public static void load(String[] args){
        if(args.length>0){
            for(int i=0;i<args.length;i++){
                switch (args[i]) {
                    case "-i":
                        inputFilename = args[++i];
                        break;
                    case "-o":
                        outputFilename = args[++i];
                        break;
                    case "-m":
                        m=Integer.parseInt(args[++i]);
                        if(m < 3 || m > 9){
                            System.out.println("m不是3-9的解决不了，退了再开");
                            System.exit(0);
                        }
                        break;
                    case "-n":
                        n=Integer.parseInt(args[++i]);
                        if(n < 1){
                            System.out.println("n小于0的解决不了，退了再开");
                            System.exit(0);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static void main(String[] args)  {
        load(args);
        int[][] ShuDu =new int[100][100];
        File myFile = new File(inputFilename);
        if(!myFile.exists()){
            System.out.println("没有输入文件呢，准备好了再来吧");
            System.exit(0);
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(myFile));
        int tempchar;
        int i=0;
        int j=0;
        while ((tempchar = reader.read()) != -1) {
            if ( (((char) tempchar) != '\n') && (((char) tempchar) != ' ')) {
                if(i<m){
                    if(j<m){
                        if(tempchar!=13){
                            ShuDu[i][j]=((char) tempchar)-48;
                            j++;
                        }
                    }else{
                        i++;
                        j=0;
                        ShuDu[i][j]=((char) tempchar)-48;
                    }
                }
                if(i==m){
                    if(0<n){
                        sudoku(ShuDu, 0,m);
                        i=0;
                        j=0;
                        n--;
                    }
                }
            }
        }
        reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
