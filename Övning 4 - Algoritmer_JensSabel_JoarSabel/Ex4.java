import java.lang.System;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class Ex4 {
    private static final int MAXSIZE = 1000000;  // Max nr of words
    private static final int CUTOFF = 20;       // Cut-off for recursive quicksort

    /**
     * @param a Takes the array of words as an comparable
     * @param low the first index
     * @param high the last index
     *             Functional insertionSort algorithm
     */
    private static void insertionSort( Comparable [ ] a, int low, int high ) {
        for( int p = low + 1; p <= high; p++ ) {
            Comparable tmp = a[ p ];
            int j;

            for( j = p; j > low && tmp.compareTo( a[ j - 1 ] ) < 0; j-- )
                a[ j ] = a[ j - 1 ];
            a[ j ] = tmp;
        }
    }

    /**
     * @param w takes the array as an input
     *          Fairly simple mergeSort algorithm
     */
    public static void mergeSort(String[] w) {
        if(w.length < 2){ return; }
        int mid = w.length/2;
        String[] left = new String[mid];
        String[] right = new String[w.length-mid];
        int k = 0;

        for (int i=0; i< w.length; i++){
            if(i<mid){
                left[i] = w[i];
            }
            else{
                right[k] = w[i];
                k += 1;
            }
        }

        mergeSort(left);
        mergeSort(right);
        merge(left, right, w);
    }

    public static void merge(String[] left, String[] right, String[] w){
        int i=0,l=0,r=0;
        while(l < left.length && r < right.length){
            if(left[l].compareTo(right[r]) < 0){
                w[i++] = left[l++];
            }
            else{
                w[i++] = right[r++];
            }
        }
        while(l<left.length){
            w[i++] = left[l++];
        }
        while(r<right.length){
            w[i++] = right[r++];
        }
    }

    /**
     * @param a takes the array as an input
     *          calls on "quicksort" function to execute the sorting algorithm
     */
    public static void quickSort( Comparable [ ] a ) {
        quicksort( a, 0, a.length - 1 );
    }

    /**
     * @param a The array of words as an comparable array
     * @param low the first index of the words
     * @param high the last index of the words
     */
    private static void quicksort( Comparable [ ] a, int low, int high ) {
        if( low + CUTOFF > high )
            insertionSort( a, low, high );
        else {
            int middle = ( low + high ) / 2;
            if( a[ middle ].compareTo( a[ low ] ) < 0 )
                swapReferences( a, low, middle );
            if( a[ high ].compareTo( a[ low ] ) < 0 )
                swapReferences( a, low, high );
            if( a[ high ].compareTo( a[ middle ] ) < 0 )
                swapReferences( a, middle, high );

            swapReferences( a, middle, high - 1 );
            Comparable pivot = a[ high - 1 ];

            int i, j;
            for( i = low, j = high - 1; ; ) {
                while( a[ ++i ].compareTo( pivot ) < 0 )
                    ;
                while( pivot.compareTo( a[ --j ] ) < 0 )
                    ;
                if( i >= j )
                    break;
                swapReferences( a, i, j );
            }

            swapReferences( a, i, high - 1 );

            quicksort( a, low, i - 1 ); // Sort small elements
            quicksort( a, i + 1, high ); // Sort large elements
        }
    }

    public static final void swapReferences( Object [ ] a, int index1, int index2 ) {
        Object temp = a[ index1 ];
        a[ index1 ] = a[ index2 ];
        a[ index2 ] = temp;
    }

    public static String[] readWords(String fileName) {
        String[] words = new String[MAXSIZE];
        int rowCount = 0;
        int wordCount = 0;
        try {
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            String inputLine, thisLine;

            // Read lines until end of file
            while ((inputLine = myReader.readLine()) != null) {
                // Remove punctuation characters and convert to lower case
                //// Note: compound words will have the - removed !!!
                thisLine = inputLine.replaceAll("\\p{Punct}", "").toLowerCase();
                if (thisLine.length() !=0) {         // Skip empty lines
                    // Split the line into separate words on one or more whitespace
                    String[] w = thisLine.split("\\p{IsWhite_Space}+");
                    // Put the words in an array
                    for(String s:w){
                        if (!s.isEmpty()) words[wordCount++] = s;  // Skip empty words, count nr of words
                    }
                    rowCount++;    // Count number of rows
                }
            }
            System.out.println();
            System.out.println("Read " + rowCount + " rows and " + wordCount + " words");
            // Return the words in an array of of length wordCound
            return(java.util.Arrays.copyOfRange(words, 0, wordCount));
        }

        catch (IOException e) { // No file
            System.out.println("Error: " + e.getMessage());
            return (null);
        }
    }


    public static void writeWords(String [] words, String fileName) {
        BufferedWriter bw = null;
        try {
            File outputFile = new File(fileName);
            outputFile.createNewFile();        // Create the output file
            FileWriter fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
            for (String s:words) {       // Write the words to the file
                bw.write(s + " ");     //
            }
            System.out.println("Wrote file " + fileName);

        } catch (IOException e) {
            System.out.println ("No file " + fileName);
            System.exit(0);
        }
        finally {
            try {
                if (bw != null) bw.close();
            }
            catch (Exception ex) {
                System.out.println("Error in closing file " + fileName);
            }
        }
    }

    public static void main(String[] args) {
        // Check that a file name is given as an argument
        if (args.length != 1 ) {
            System.out.println("Please give the file name");
            System.exit(0);
        }
        String fileName = args[0];

        // Read the words from the input file
        String[] words = readWords(fileName);
        if (words == null) System.exit(0);     // Quit if file is not found

        System.out.println();
        System.out.println("Sorting with Insertion sort ");
        // Test the insertion sort method and measure how long it takes
        long startTime = System.nanoTime();
        insertionSort(words, 0 ,words.length-1);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for InsertionSort: " + estimatedTime/1000000000.0 + " seconds");
        // Write the result of insertion sort to a new file
        writeWords(words, fileName + ".InsertionSort" );

        System.out.println();
        System.out.println("Sorting with MergeSort ");
        // Test the MergeSort method
        startTime = System.nanoTime();
        mergeSort(words);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for MergeSort: " + estimatedTime/1000000000.0 + " seconds");
        writeWords(words, fileName + ".MergeSort" );


        // Test the quicksort method and measure how long it takes
        System.out.println();
        System.out.println("Sorting with Quicksort ");
        startTime = System.nanoTime();
        quickSort(words);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for QuickSort: " + estimatedTime/1000000000.0 + " seconds");
        // Write the result of quicksort to a new file
        writeWords(words, fileName + ".QuickSort" );
        System.out.println();
        System.out.println();

    }
}
