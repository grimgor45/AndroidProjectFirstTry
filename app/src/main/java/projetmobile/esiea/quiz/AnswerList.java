package projetmobile.esiea.quiz;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//TODO make it into a singleton
public class AnswerList {

    private static AnswerList instance = null;

    private static JSONObject A1;
    private static JSONObject A2;
    private static JSONObject A3;
    private static JSONObject A4;

    public static  JSONObject[] objList;

    public static  int correct;

    private static Random rand = new Random();

    private AnswerList(JSONArray list)
    {


        try {
            int[] ans = createRandomDiffIntList(4,list.length());
             A1 = list.getJSONObject(ans[0]);
             A2 = list.getJSONObject(ans[1]);
             A3 = list.getJSONObject(ans[2]);
             A4 = list.getJSONObject(ans[3]);
            objList = new JSONObject[]{A1,A2,A3,A4};

            correct = rand.nextInt(4)+1;

        }
        catch(JSONException e)
        { }
    }

    public final static AnswerList getInstance(JSONArray list){
        if (AnswerList.instance== null){
            AnswerList.instance = new AnswerList(list);
        }
        else{
            try {
                int[] ans = createRandomDiffIntList(4, list.length());
                A1 = list.getJSONObject(ans[0]);
                A2 = list.getJSONObject(ans[1]);
                A3 = list.getJSONObject(ans[2]);
                A4 = list.getJSONObject(ans[3]);
                objList = new JSONObject[]{A1,A2,A3,A4};
            correct = rand.nextInt(4)+1;
            }
            catch(JSONException e)
            { }
        }
        return instance;
    }

    private static int[] createRandomDiffIntList(int size, int max){
        Random random = new Random();
        int[] list = new int[size];
        for (int i = 0; i < size; i++)
        {
            list[i]=random.nextInt(max);
            while (true){
                for (int j = 0; j < i; j++)
                {
                    if (list[i]==list[j])
                    {
                        list[i]=random.nextInt();
                    }
                }
                break;
            }
        }
        return list;

    }
}
