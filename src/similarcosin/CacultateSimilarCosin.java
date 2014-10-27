/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package similarcosin;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phucdk
 */
public class CacultateSimilarCosin {

    public static float termFrequency(String t, String d) throws Exception {
        try {
            List<String> listTokens = splitInToken(d);
            return (float) numberOfOccurence(t, listTokens) / listTokens.size();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static List<String> splitInToken(String d) {
        List<String> listTokens = null;
        if (d != null) {
            listTokens = new ArrayList<>();
            String[] arrTokens = d.split(" ");
            for (String token : arrTokens) {
                listTokens.add(token.toLowerCase().trim());
            }
        }
        return listTokens;
    }

    public static int numberOfOccurence(String t, List<String> listTokens) {
        return Collections.frequency(listTokens, t.toLowerCase());
    }

    public static float inverseDocumentFrequence(String t, List<String> listDocuments) {
        int totalDocument = listDocuments.size();
        int numberOfApperence = countNumberApprerence(t, listDocuments);
        if (numberOfApperence == 0) {
            return Integer.MAX_VALUE;
        } else {
            return 1 + (float) Math.log((float) totalDocument / numberOfApperence);
        }
    }

    private static int countNumberApprerence(String t, List<String> listDocuments) {
        int count = 0;
        for (String document : listDocuments) {
            if (splitInToken(document).contains(t.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public static float tf_idf(String t, String document, List<String> listDocuments) throws Exception {
        return termFrequency(t, document) * inverseDocumentFrequence(t, listDocuments);
    }

    //similar cosine in case: search keyword contains only 2 tokens
    public static float cosin(Vector v1, Vector v2) {
        return (float) (v1.getX() * v2.getX() + v1.getY() * v2.getY()) / (float) (Math.sqrt(v1.getX() * v1.getX() + v1.getY() * v1.getY()) * Math.sqrt(v2.getX() * v2.getX() + v2.getY() * v2.getY()));
    }

    public static Map<String, Float> similarCosin(String document, List<String> listDocumets) throws Exception {
        List<String> listToken = splitInToken(document);
        String t1 = listToken.get(0);
        String t2 = listToken.get(1);

        Vector v0 = new Vector();
        v0.setX(tf_idf(t1, document, listDocumets));
        v0.setY(tf_idf(t2, document, listDocumets));

        List<Vector> listVectors = new ArrayList<>();
        for (String secondDocument : listDocumets) {
            Vector aVector = new Vector();
            aVector.setX(tf_idf(t1, secondDocument, listDocumets));
            aVector.setY(tf_idf(t2, secondDocument, listDocumets));
            listVectors.add(aVector);
        }
        Map<String, Float> mapSiminlarcosin = new HashMap<>();
        for (int i = 0; i < listVectors.size(); i++) {
            mapSiminlarcosin.put(listDocumets.get(i), cosin(v0, listVectors.get(i)));
        }
        return mapSiminlarcosin;
    }

    //similar cosine in general case: search keyword contains more than 2 tokens
    public static float generalCosin(GeneralVector v1, GeneralVector v2) {
        int dimention = v1.getDimention();
        float dotProduct = 0;
        float eucledianDist = 0;
        float tmp;
        for (int i = 0; i < dimention; i++) {
            tmp = v1.getListValue().get(i) * v2.getListValue().get(i);
            dotProduct += tmp;
        }

        float eucledianDistV1 = 0;
        float eucledianDistV2 = 0;
        tmp = 0;
        for (int i = 0; i < dimention; i++) {
            tmp += v1.getListValue().get(i) * v1.getListValue().get(i);
        }
        eucledianDistV1 = (float) Math.sqrt(tmp);

        tmp = 0;
        for (int i = 0; i < dimention; i++) {
            tmp += v2.getListValue().get(i) * v2.getListValue().get(i);
        }
        eucledianDistV2 = (float) Math.sqrt(tmp);
        eucledianDist = eucledianDistV1 * eucledianDistV2;
        if (eucledianDist == 0) {
            return 0;
        } else {
            return (float) dotProduct / eucledianDist;
        }

    }

    public static List<ResultObject> gereralSimilarCosin(String document, List<String> listDocumets) throws Exception {
        List<ResultObject> listResult = new ArrayList<>();
        List<String> listToken = splitInToken(document);
        int dimention = listToken.size();

        GeneralVector v0 = new GeneralVector(dimention);
        for (String token : listToken) {
            v0.getListValue().add(tf_idf(token, document, listDocumets));
        }

        List<GeneralVector> listVectors = new ArrayList<>();
        for (String nextDocument : listDocumets) {
            GeneralVector aGeneralVector = new GeneralVector(dimention);
            for (String token : listToken) {
                aGeneralVector.getListValue().add(tf_idf(token, nextDocument, listDocumets));
            }
            listVectors.add(aGeneralVector);
        }

        for (int i = 0; i < listVectors.size(); i++) {
            ResultObject aResultObject = new ResultObject();
            aResultObject.setDocument(listDocumets.get(i));
            aResultObject.setSimilarCosin(generalCosin(v0, listVectors.get(i)));
            listResult.add(aResultObject);
        }
        Collections.sort(listResult);
        return listResult;
    }
}
