package uk.ac.gla.dcs.dsms;

import org.terrier.structures.postings.Posting;
import org.terrier.matching.dsms.DependenceScoreModifier;
import java.lang.*;
import java.util.Arrays;
import org.terrier.structures.postings.BlockPosting;
import org.terrier.structures.postings.IterablePosting;

/**
 * You should use this sample class to implement a proximity feature in Exercise 2.
 * TODO: Describe the function that your class implements
 * <p>
 * You can add your feature into a learned model by appending DSM:uk.ac.gla.IRcourse.SampleProxFeatureDSM to the features.list file.
 * @author TODO
 */
public class ProxFeatureDSM_2 extends DependenceScoreModifier {


    /** This class is passed the postings of the current document,
     * and should return a score to represent that document.
     */
    @Override
    protected double calculateDependence(
            Posting[] ips, //postings for this document (these are actually IterablePosting[])
            boolean[] okToUse,  //is each posting on the correct document?
            double[] phraseTermWeights, boolean SD //not needed
    )
    {

        final int numberOfQueryTerms = okToUse.length;

        //***
        //TODO: in this part, write your code that inspects
        //the positions of query terms, to make a proximity function
        //NB: you can cast each Posting to org.terrier.structures.postings.BlockPosting
        //and use the getPositions() method to obtain the positions.
        //***
        double diff_avg_pos = 0.0;
        int count = 0;
        for(int index1 = 0; index1 < numberOfQueryTerms; index1++){
            if(okToUse[index1] == true){

                int[] vector1 = ((BlockPosting)ips[index1]).getPositions();

                if(index1+1 < numberOfQueryTerms){

                    for(int index2 = index1+1 ; index2 < numberOfQueryTerms; index2++)
                        if(okToUse[index2] == true){

                            int[] vector2 = ((BlockPosting)ips[index2]).getPositions();

                            int sum1 = 0;
                            int sum2 = 0;
                            for(int i = 0; i < vector1.length; i++){
                                sum1 += vector1[i];
                            }
                            double avg1 = sum1/vector1.length;
                            for(int i = 0; i < vector2.length; i++){
                                sum2 += vector2[i];
                            }
                            double avg2 = sum2/vector2.length;
                            diff_avg_pos += Math.abs(avg1 - avg2);
                            count++;
                        }
                }

            }
        }
        diff_avg_pos = diff_avg_pos/count;
        return diff_avg_pos;
    }

    /** You do NOT need to implement this method */
    @Override
    protected double scoreFDSD(int matchingNGrams, int docLength) {
        throw new UnsupportedOperationException();
    }


    @Override
    public String getName() {
        return "ProxFeatureDSM_MYFUNCTION";
    }

}
