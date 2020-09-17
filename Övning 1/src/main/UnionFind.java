package main;


//Created a new class so as to not clutter up the Ex1 class.
public class UnionFind {

    int [] dataSet;     //The array that will be used as for the union-find operations.


    //Constructor - int n represents the size of the array/the number of cells
    public UnionFind(int n){

        if(n > 0){
            this.dataSet = new int[n];
            for (int i = 0; i < n; i++){
                this.dataSet[i] = -1;
            }
        }
        else{
            throw new IllegalArgumentException("The number which was used as input is invalid");
        }
    }


    //Find operation, int i represents the number which we wish to find.
    public int find(int i){
        if (this.dataSet[i] < 0){
            return i;
        }
        this.dataSet[i] = this.find(this.dataSet[i]);
        return this.dataSet[i];
    }


    /**Union operation - "int a" is the first set that will be merged
     * and int b is the second set that will be merged.
     */

    public void union(int a, int b){

        int rootA = this.find(a);
        int rootB = this.find(b);
        int sizeOfA = this.dataSet[rootA];
        int sizeOfB = this.dataSet[rootB];

        if (sizeOfA < sizeOfB){
            this.dataSet[rootA] = rootB;
            this.dataSet[rootB] += sizeOfA;
        }
        else{
            this.dataSet[rootB] = rootA;
            this.dataSet[rootA] += sizeOfB;
        }
    }

    public int getSubsets (){
        int num = 0;

        for (int i = 0; i < this.dataSet.length; i++) {
            if (this.dataSet[i] < 0){
                num++;
            }
        }
        return num;
    }


}
