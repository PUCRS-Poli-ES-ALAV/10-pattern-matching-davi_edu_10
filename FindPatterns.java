
public class FindPatterns{
    public static void main(String[]  args) {
        String s1 = "ABCDCBDCBERHFSDFHADFBADFHSDFHDGTSFHJSJFHBADF";
        String s2 = "BADF";
        
        FileHandler fl1 = new FileHandler("g1.txt");
        String fs1 = fl1.getLinesAsArray()[0];
        String fs2 = "PUv";
        
        FileHandler fl2 = new FileHandler("g2.txt");
        String f2s1 = "";
        for(String line: fl2.getLinesAsArray()){
            f2s1 = f2s1.concat(line);
        }
        String f2s2 = "EMX";
        
        FileHandler fl3 = new FileHandler("g3.txt");
        String f3s1 = "";
        for(String line: fl3.getLinesAsArray()){
            f3s1 = f3s1.concat(line);
        }
        String f3s2 = "WXhn";

        FileHandler fl4 = new FileHandler("g4.txt");
        String f4s1 = "";
        for(String line: fl4.getLinesAsArray()){
            f4s1 = f4s1.concat(line);
        }
        String f4s2 = "ryU";

        PerfomanceTester pt = PerfomanceTester.getInstance();

        pt.addTest("RabinKarpHash");
        pt.addTest("RabinKarpHash 500");
        pt.addTest("RabinKarpHash 50000");
        pt.addTest("RabinKarpHash 500000");
        pt.addTest("RabinKarpHash 5000000");
       
        pt.addTest("RabinKarpRollingHash");
        pt.addTest("RabinKarpRollingHash 500");
        pt.addTest("RabinKarpRollingHash 50000"); 
        pt.addTest("RabinKarpRollingHash 500000"); 
        pt.addTest("RabinKarpRollingHash 5000000"); 
        
        pt.addTest("KMP");
        pt.addTest("KMP 500");
        pt.addTest("KMP 50000");
        pt.addTest("KMP 500000");
        pt.addTest("KMP 5000000");

      
        // Rabin-Karp Hash Tests ------------------------------------------------------------------------------------------ //
        pt.start("RabinKarpHash");
        System.out.println(RabinKarpHash(s1, s2, pt));
        pt.end();
        
        pt.start("RabinKarpHash 500");
        System.out.println(RabinKarpHash(fs1, fs2, pt));
        pt.end();

        pt.start("RabinKarpHash 50000");
        System.out.println(RabinKarpHash(f2s1, f2s2, pt));
        pt.end();

        pt.start("RabinKarpHash 500000");
        System.out.println(RabinKarpHash(f3s1, f3s2, pt));
        pt.end();

        pt.start("RabinKarpHash 5000000");
        System.out.println(RabinKarpHash(f4s1, f4s2, pt));
        pt.end();

        System.out.println(pt.getReport("RabinKarpHash"));
        System.out.println(pt.getReport("RabinKarpHash 500"));
        System.out.println(pt.getReport("RabinKarpHash 50000"));
        System.out.println(pt.getReport("RabinKarpHash 500000"));
        System.out.println(pt.getReport("RabinKarpHash 5000000"));

        // Rabin-Karp Rolling Hash Tests ----------------------------------------------------------------------------------- //
        pt.start("RabinKarpRollingHash");
        System.out.println(RabinKarpRollingHash(s1,s2, pt));
        pt.end();

        pt.start("RabinKarpRollingHash 500");
        System.out.println(RabinKarpRollingHash(fs1,fs2, pt));
        pt.end();

        pt.start("RabinKarpRollingHash 50000");
        System.out.println(RabinKarpRollingHash(f2s1,f2s2, pt));
        pt.end();

        pt.start("RabinKarpRollingHash 500000");
        System.out.println(RabinKarpRollingHash(f3s1,f3s2, pt));
        pt.end();

        pt.start("RabinKarpRollingHash 5000000");
        System.out.println(RabinKarpRollingHash(f4s1,f4s2, pt));
        pt.end();

        System.out.println(pt.getReport("RabinKarpRollingHash"));
        System.out.println(pt.getReport("RabinKarpRollingHash 500"));
        System.out.println(pt.getReport("RabinKarpRollingHash 50000"));
        System.out.println(pt.getReport("RabinKarpRollingHash 500000"));
        System.out.println(pt.getReport("RabinKarpRollingHash 5000000"));

        // KMP Tests ------------------------------------------------------------------------------------------------------- //
        pt.start("KMP");
        KMPSearch(s2,s1,pt);
        pt.end();

        pt.start("KMP 500");
        KMPSearch(fs2,fs1,pt);
        pt.end();

        pt.start("KMP 50000");
        KMPSearch(f2s2,f2s1,pt);
        pt.end();

        pt.start("KMP 500000");
        KMPSearch(f3s2,f3s1,pt);
        pt.end();

        pt.start("KMP 5000000");
        KMPSearch(f4s2,f4s1,pt);
        pt.end();

        System.out.println(pt.getReport("KMP"));
        System.out.println(pt.getReport("KMP 500"));
        System.out.println(pt.getReport("KMP 50000"));
        System.out.println(pt.getReport("KMP 500000"));
        System.out.println(pt.getReport("KMP 5000000"));

    }

    private static int RabinKarpHash(String txt, String pat, PerfomanceTester pt) {
        int M = pat.length();
        int N = txt.length();
        long patHash = hash(pat, M, pt);
     
        for (int i = 0; i <= N - M; i++) {
           long txtHash = hash(txt.substring(i, i+M), M, pt);
           if (patHash == txtHash)
              return i; // ocorrência? colisão?
        }
        return N; // nenhuma ocorrência
    }

    private static long hash(String s, int M, PerfomanceTester pt) {
        long h = 0;
        for (int j = 0; j < M; j++){
            pt.countIter();
           h = (h * 26 + s.charAt(j)) %  2147483629;
        }
        return h;
    }

    
    static void KMPSearch(String pat, String txt, PerfomanceTester pt) 
    { 
        int M = pat.length(); 
        int N = txt.length(); 

        // cria lps[] que vai guardar o maior 
        // valor prefixo sufixo para o padrão 
        int lps[] = new int[M]; 
        int j = 0; // index for pat[] 

        // Calcula lps[] 
        computeLPSArray(pat, M, lps,pt); 

        int i = 0; // index for txt[] 
        while (i < N) { 
            pt.countIter();
            if (pat.charAt(j) == txt.charAt(i)) { 
                j++; 
                i++; 
            } 
            if (j == M) { 
                System.out.println("Found pattern "
                                + "at index " + (i - j)); 
                j = lps[j - 1]; 
                break;
            } 

            // mismatch após j matches 
            else if (i < N && pat.charAt(j) != txt.charAt(i)) { 
                // Não faz match dos caracteres lps[0..lps[j-1]], 
                // não é necesssário, eles combinarão 
                if (j != 0) 
                    j = lps[j - 1]; 
                else
                    i = i + 1; 
            } 
        } 
    } 

    static void computeLPSArray(String pat, int M, int lps[], PerfomanceTester pt) 
    { 
        // tamanho do maior prefixo sufixo anterior 
        int len = 0; 
        int i = 1; 
        lps[0] = 0; // lps[0] is always 0 

        // loop calcula lps[i] for i = 1 to M-1 
        while (i < M) { 
            pt.countIter();
            if (pat.charAt(i) == pat.charAt(len)) { 
                len++; 
                lps[i] = len; 
                i++; 
            } 
            else // (pat[i] != pat[len]) 
            { 
                if (len != 0) { 
                    len = lps[len - 1]; 
                } 
                else // if (len == 0) 
                { 
                    lps[i] = len; 
                    i++; 
                } 
            } 
        } 
    }

    static long powMod(long n, int e, int mod, PerfomanceTester pt){ 
        long result = 1;
        for(int i =0; i < e; i++){
            result = (result * n) % mod;
        }
        return result;
    }

    static long rollingHashFirst(String s, int tamanho, PerfomanceTester pt){ // O(n)
        long hash = 0;
    
        for(int i = 1; i <= tamanho; i++){
            pt.countIter();
            hash += (long)s.charAt(i-1) * powMod(26,tamanho-i,2147483629, pt); 
        } 
        return hash;
    }
    
    
    static long rollingHash(char add, char remove, int tamanho, long lastHash, PerfomanceTester pt){ // O(1)
        long hash = 0;
        long newChar = (long) add;
        long removeChar = (long) remove;

        long removeCharValue = (long) (removeChar * powMod(26,tamanho-1,2147483629,pt));
        hash = ((lastHash - removeCharValue)*26 % 2147483629)+ newChar % 2147483629;
        return hash;
    }

    static int RabinKarpRollingHash(String txt, String pat, PerfomanceTester pt){ // O(n + m)
        
        int M = txt.length();
        int N = pat.length();
        
        long txtHash = rollingHashFirst(txt.substring(0,N), N, pt);
        long patHash = rollingHashFirst(pat, N, pt);
        long lastHash = txtHash;

        if (patHash == txtHash)
            return 0; // ocorrência? colisão?
        
        for (int i = 1; i <= M -N; i++) {
            pt.countIter();
            txtHash = rollingHash(txt.charAt(N+i-1), txt.charAt(i-1), N, lastHash ,pt);
            lastHash = txtHash;
            if (patHash == txtHash)
               return i; // ocorrência? colisão?
        }

        return -1;

    }

}