
public class FindPatterns{
    public static void main(String[]  args) {
        String s1 = "ABCDCBDCBERHFSDFHADFHSDFHDGTSFHJSJFHBADF";

        String s2 = "BADF";

        FileHandler fl1 = new FileHandler("g1.txt");
        FileHandler fl2 = new FileHandler("g2.txt");

        String fs1 = fl1.getLinesAsArray()[0];
        String fs2 = "PUv";

        String f2s1 = "";
        for(String line: fl2.getLinesAsArray()){
            f2s1 = f2s1.concat(line);
        }
        String f2s2 = "EMX";

        PerfomanceTester pt = PerfomanceTester.getInstance();
        pt.addTest("String test");
        pt.addTest("String test 500");
        pt.addTest("String test 50000");
        pt.addTest("String Hashtest");
        pt.addTest("String Hashtest 500");
        pt.addTest("String Hashtest 50000");
        pt.addTest("String KMP");
        pt.addTest("String KMP 500");
        pt.addTest("String KMP 50000");

        pt.start("String test");
        System.out.println(verifica(s1, s2, pt));
        pt.end();
        
        pt.start("String test 500");
        System.out.println(verifica(fs1, fs2, pt));
        pt.end();

        pt.start("String test 50000");
        System.out.println(verifica(f2s1, f2s2, pt));
        pt.end();

        System.out.println(pt.getReport("String test"));
        System.out.println(pt.getReport("String test 500"));
        System.out.println(pt.getReport("String test 50000"));

        pt.start("String Hashtest");
        System.out.println(search(s1, s2, pt));
        pt.end();
        
        pt.start("String Hashtest 500");
        System.out.println(search(fs1, fs2, pt));
        pt.end();

        pt.start("String Hashtest 50000");
        System.out.println(search(f2s1, f2s2, pt));
        pt.end();

        System.out.println(pt.getReport("String Hashtest"));
        System.out.println(pt.getReport("String Hashtest 500"));
        System.out.println(pt.getReport("String Hashtest 50000"));

        pt.start("String KMP");
        KMPSearch(s2,s1,pt);
        pt.end();

        pt.start("String KMP 500");
        KMPSearch(fs2,fs1,pt);
        pt.end();

        pt.start("String KMP 50000");
        KMPSearch(f2s2,f2s1,pt);
        pt.end();

        System.out.println(pt.getReport("String KMP"));
        System.out.println(pt.getReport("String KMP 500"));
        System.out.println(pt.getReport("String KMP 50000"));

    }

    public static int verifica(String s1, String s2, PerfomanceTester pt) {
        for(int i = 0; i<=s1.length() - s2.length();i++) {
            int count = 1;
            char ch = s1.charAt(i);
        
            
            for(int j = 0; j<s2.length(); j++) {
                pt.countIter();
                char ch2 = s2.charAt(j);
                if(ch == ch2){
                    if(j == s2.length()-1){
                        return i;
                    }
                    ch = s1.charAt(i+count++);
                }
                else break;
            }
        }
        return -1;
    }

    private static int search(String txt, String pat, PerfomanceTester pt) {
        int M = pat.length();
        int N = txt.length();
        long patHash = hash(pat, M, pt);
     
        for (int i = 0; i <= N - M; i++) {
           pt.countIter();
           long txtHash = hash(txt.substring(i, i+M), M, pt);
           if (patHash == txtHash)
              return i; // ocorrência? colisão?
        }
        return N; // nenhuma ocorrência
    }

    private static long hash(String s, int M, PerfomanceTester pt) {
        long h = 0;
        for (int j = 0; j < M; j++){
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

}