// Java program to find out
// all combinations of positive
// numbers that add upto given
// number
import java.io.*;
import java.util.ArrayList;

class GFG
{
    /* arr - array to store the
    combination
    index - next location in array
    num - given number
    reducedNum - reduced number */
    static StringBuilder findCombinationsUtil(int arr[], int index,
                                     int num, int reducedNum,StringBuilder str)


    {

        // Base condition
        if (reducedNum < 0)
            return str;

        // If combination is
        // found, print it
        if (reducedNum == 0)
        {
            for (int i = 0; i < index; i++) {
                //System.out.print(arr[i] + " ");
                str.append(arr[i] + " ");
            }
          //  System.out.println();
            str.append("\n");
            return str;
        }

        // Find the previous number
        // stored in arr[]. It helps
        // in maintaining increasing
        // order
        int prev = (index == 0) ?
                1 : arr[index - 1];

        // note loop starts from
        // previous number i.e. at
        // array location index - 1
        for (int k = prev; k <= num ; k++)
        {
            // next element of
            // array is k
            arr[index] = k;

            // call recursively with
            // reduced number
            findCombinationsUtil(arr, index + 1, num,
                    reducedNum - k,str);
        }
        return str;
    }

    /* Function to find out all
    combinations of positive
    numbers that add upto given
    number. It uses findCombinationsUtil() */
    static StringBuilder findCombinations(int n)
    {

        // array to store the combinations
        // It can contain max n elements
        int arr[] = new int [n];
        StringBuilder str = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        // find all combinations
        str2=findCombinationsUtil(arr, 0, n, n,str);

        return str2;
    }

    public static ArrayList<Integer[]> findCombinationsWithLength(int n,int length){


        String kombinacje = findCombinations(n).toString();
        System.out.println(kombinacje+"\n================\n");
        String arr[] = kombinacje.split("\n");
        String[] wybraneKombinacje;
        ArrayList<Integer[]> kombinacjeInt= new ArrayList<>();
        for(String s : arr)

            if(s.split(" ").length==length) {
                System.out.println(s);
                kombinacjeInt.add(new Integer[length]);
                int i=0;
                for (String s2 : s.split(" ")) {
                    kombinacjeInt.get(kombinacjeInt.size() - 1)[i] = Integer.parseInt(s2);
                    i++;
                }
            }
        System.out.println("\n================\n");
        for(Integer[] i: kombinacjeInt){
            for(Integer k : i)
                System.out.print(k+ " ");
            System.out.println();

        }
    return kombinacjeInt;
    }
    // Driver code

}


